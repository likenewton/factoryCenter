package cn.yunovo.iov.factory.framework.dac.interceptor;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Pattern;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.springframework.util.ReflectionUtils;

import cn.yunovo.iov.factory.framework.dac.DacHelper;
import cn.yunovo.iov.factory.framework.dac.metadata.DacProperties;
import cn.yunovo.iov.factory.framework.dac.parser.DacByParser;

@Intercepts({ @Signature(type = Executor.class, method = "update", args = { MappedStatement.class, Object.class }) })
public class DacUpdateInterceptor implements Interceptor {

	private volatile DacHelper dacHelper;

	private static Field additionalParametersField;

	private DacProperties dacProperties;

	private Map<String, String> dataProviderMap = new HashMap<String, String>();

	private static final Log log = LogFactory.getLog(DacUpdateInterceptor.class);

	static {
		try {
			additionalParametersField = BoundSql.class.getDeclaredField("additionalParameters");
			additionalParametersField.setAccessible(true);
		} catch (NoSuchFieldException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Object intercept(Invocation invocation) throws Throwable {

		try {
			return invocation.proceed();
		} finally {

			// 新增之后，获取新增ID
			Object[] args = invocation.getArgs();
			MappedStatement ms = (MappedStatement) args[0];

			Object parameter = args[1];
			SqlCommandType commandType = ms.getSqlCommandType();
			Object dataId = null;

			// 新增数据权限
			if ("INSERT".equals(commandType.toString())) {
				String methodName = "getId";
				Method method = ReflectionUtils.findMethod(parameter.getClass(), methodName);
				if(null != method) {
					dataId = method.invoke(parameter);

					if (null != dataId) {
						BoundSql boundSql = ms.getBoundSql(parameter);
						String sql = boundSql.getSql();

						// 是否需要做数据权限
						List<String> tables = DacByParser.getTablesNames(sql);
						for (String table : tables) {
							if (!dacHelper.containsProvider(dacProperties, dataProviderMap, tables)) {
								String insertTable = table;
								dacHelper.insert(insertTable, Integer.valueOf(dataId.toString()), dacProperties.getUserType());
							}
						}
					}
				}

			} else if ("DELETE".equals(commandType.toString())) {

				// 删除数据权限
				if (null != parameter) {
					try {
						String deleteId = ms.getId();
						String regex = ".+\\.delete.+ById";
						boolean isMatch = Pattern.matches(regex, deleteId);
						if (isMatch) {
							dataId = Integer.valueOf(parameter.toString());
							BoundSql boundSql = ms.getBoundSql(parameter);
							String sql = boundSql.getSql();

							// 是否需要做数据权限
							List<String> tables = DacByParser.getTablesNames(sql);
							for (String table : tables) {
								if (!dacHelper.containsProvider(dacProperties, dataProviderMap, tables)) {
									String deleteTable = table;
									dacHelper.delete(deleteTable, Integer.valueOf(dataId.toString()));
								}
							}
							
						}
					} catch (NumberFormatException e) {
						log.warn(e.toString());
					}
				}
			}
		}
	}

	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties properties) {
		dacHelper = new DacHelper();
	}

	public void setDacProperties(DacProperties dacProperties) {
		this.dacProperties = dacProperties;
		List<String> dataProvider = dacProperties.getDataProvider();
		for (String table : dataProvider) {
			dataProviderMap.put(table, table);
		}
	}

}
