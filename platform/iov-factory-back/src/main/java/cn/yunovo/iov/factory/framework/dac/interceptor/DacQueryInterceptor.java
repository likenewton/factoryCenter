package cn.yunovo.iov.factory.framework.dac.interceptor;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import com.github.pagehelper.PageHelper;

import cn.yunovo.iov.factory.framework.dac.DacHelper;
import cn.yunovo.iov.factory.framework.dac.bean.DataResource;
import cn.yunovo.iov.factory.framework.dac.metadata.DacProperties;
import cn.yunovo.iov.factory.framework.dac.parser.DacByParser;

@Intercepts({ @Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class }),
		@Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class }), })
public class DacQueryInterceptor implements Interceptor {

	private volatile DacHelper dacHelper;

	private static Field additionalParametersField;

	private DacProperties dacProperties;

	private Map<String, String> dataProviderMap = new HashMap<String, String>();

	static {
		try {
			additionalParametersField = BoundSql.class.getDeclaredField("additionalParameters");
			additionalParametersField.setAccessible(true);
		} catch (NoSuchFieldException e) {
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Object intercept(Invocation invocation) throws Throwable {

		// 和 PageHelper分页有冲突
		if (null == PageHelper.getLocalPage()) {
			dacHelper.clearProvider();
		}

		// 是否需要做数据权限
		if (dacHelper.skip(dacProperties.getMaster(), dacProperties.getUserType())) {
			return invocation.proceed();
		}

		try {
			Object[] args = invocation.getArgs();
			MappedStatement ms = (MappedStatement) args[0];
			Object parameter = args[1];
			RowBounds rowBounds = (RowBounds) args[2];
			ResultHandler resultHandler = (ResultHandler) args[3];
			Executor executor = (Executor) invocation.getTarget();
			CacheKey cacheKey;
			BoundSql boundSql;
			// 由于逻辑关系，只会进入一次
			if (args.length == 4) {
				// 4 个参数时
				boundSql = ms.getBoundSql(parameter);
				cacheKey = executor.createCacheKey(ms, parameter, rowBounds, boundSql);
			} else {
				// 6 个参数时
				cacheKey = (CacheKey) args[4];
				boundSql = (BoundSql) args[5];
			}
			String sql = boundSql.getSql();

			if (dacHelper.skipQuery(sql, dataProviderMap, dacProperties.getMaster(), dacProperties.getUserType())) {
				return invocation.proceed();
			} else {
				DataResource dataResource = dacHelper.getDataAuthorityControl();
				String providerBy = dataResource.getProviderBy();
				String providerBySql = DacByParser.converToProviderBySql(sql, dataResource);
				// 更新cacheKey，防止缓存错误
				cacheKey.update(providerBy);
				BoundSql providerByBoundSql = new BoundSql(ms.getConfiguration(), providerBySql, boundSql.getParameterMappings(), parameter);
				Map<String, Object> additionalParameters = (Map<String, Object>) additionalParametersField.get(boundSql);
				for (String key : additionalParameters.keySet()) {
					providerByBoundSql.setAdditionalParameter(key, additionalParameters.get(key));
				}
				return executor.query(ms, parameter, rowBounds, resultHandler, cacheKey, providerByBoundSql);
			}
		} finally {
			if (null == PageHelper.getLocalPage()) {
				dacHelper.clearUser();
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
