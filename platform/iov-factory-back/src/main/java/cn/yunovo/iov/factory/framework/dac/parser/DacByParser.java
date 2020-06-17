package cn.yunovo.iov.factory.framework.dac.parser;

import java.util.List;

import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;

import cn.yunovo.iov.factory.framework.dac.bean.DataResource;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.Limit;
import net.sf.jsqlparser.statement.select.OrderByElement;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectBody;
import net.sf.jsqlparser.statement.select.SelectItem;
import net.sf.jsqlparser.util.TablesNamesFinder;

public class DacByParser {

	private static final Log log = LogFactory.getLog(DacByParser.class);

	public static String converToProviderBySql(String sql, DataResource dataResource) {
		// 解析SQL
		try {
			if (0 < sql.indexOf(dataResource.getMapperBy())) {
				return sql;
			}
			Statement statement = getStatement(sql);
			Select select = (Select) statement;
			SelectBody selectBody = select.getSelectBody();
			PlainSelect plainSelect = (PlainSelect) selectBody;

			// 获取查询字段
			String clomus = "";
			List<SelectItem> selectItemlist = plainSelect.getSelectItems();
			if (null != selectItemlist) {
				for (SelectItem itme : selectItemlist) {
					clomus = clomus + "," + itme.toString();
				}
				clomus = clomus.substring(1, clomus.length());
			}

			// 获取where查询条件
			String where = "";
			Expression whereExpression = plainSelect.getWhere();
			if (null != whereExpression) {
				where = whereExpression.toString();
			}

			// 获取排序字段
			String orderBy = "";
			List<OrderByElement> orderByList = plainSelect.getOrderByElements();
			if (null != orderByList) {
				for (OrderByElement itme : orderByList) {
					orderBy = orderBy + "," + itme.toString();
				}
				orderBy = orderBy.substring(1, orderBy.length());
			}

			// 获取分页
			Limit limit = plainSelect.getLimit();

			// 拼接新SQL
			String tableName = getTablesNames(sql);
			String providerBy = dataResource.getProviderBy();
			String adcSql = "select resource.* from " + dataResource.getMapperBy() + " dacr left join " + tableName + " resource on resource.id=dacr.data_id where dacr.data_provider='" + providerBy + "' and dacr.creator_id='" + dataResource.getUserId()
					+ "'";
			sql = "SELECT " + clomus + " FROM  (" + adcSql + ") filterAfter";

			if (null != whereExpression) {
				sql = sql + " where " + where + " ";
			}

			if (null != orderByList) {
				sql = sql + " order by " + orderBy.toString();
			}

			if (null != limit) {
				sql = sql + limit.toString();
			}
		} catch (Throwable e) {
			log.error("数据权限解析SQL异常", e);
		}
		return sql;
	}

	public static String getTablesNames(String sql) {
		Statement statement = getStatement(sql);
		TablesNamesFinder tablesNamesFinder = new TablesNamesFinder();
		List<String> tableList = tablesNamesFinder.getTableList(statement);
		String tableName = tableList.get(0);
		return tableName;
	}

	private static Statement getStatement(String sql) {
		Statement statement = null;
		try {
			statement = CCJSqlParserUtil.parse(sql);
		} catch (JSQLParserException e) {
			log.error("数据权限解析SQL异常", e);
		}
		return statement;
	}
}
