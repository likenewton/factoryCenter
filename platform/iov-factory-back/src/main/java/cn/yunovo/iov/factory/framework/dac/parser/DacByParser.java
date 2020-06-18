package cn.yunovo.iov.factory.framework.dac.parser;

import java.util.List;

import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;

import cn.yunovo.iov.factory.framework.dac.bean.DataResource;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Alias;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.Function;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.FromItem;
import net.sf.jsqlparser.statement.select.Join;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectBody;
import net.sf.jsqlparser.statement.select.SelectExpressionItem;
import net.sf.jsqlparser.statement.select.SelectItem;
import net.sf.jsqlparser.util.SelectUtils;
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

			FromItem fromItem = plainSelect.getFromItem();
			Table fromTable = (Table) fromItem;

			final EqualsTo equalsTo = new EqualsTo();
			if (null != fromTable.getAlias()) {
				equalsTo.setLeftExpression(new Column(fromTable.getAlias().getName() + ".id"));
			} else {
				fromTable.setAlias(new Alias("targer"));
				String alias = fromTable.getAlias().getName();
				equalsTo.setLeftExpression(new Column(alias + ".id"));
				List<SelectItem> selectItemlist = plainSelect.getSelectItems();
				if (null != selectItemlist) {
					for (SelectItem item : selectItemlist) {
						SelectExpressionItem sei = (SelectExpressionItem) item;
						Expression expressionColumn = sei.getExpression();

						if (expressionColumn instanceof Column) {
							Column c = (Column) expressionColumn;
							c.setColumnName(alias + "." + c.getColumnName());
						}

						if (expressionColumn instanceof Function) {
							// Column c = (Column) expressionColumn;
							// c.setColumnName(alias + "." + c.getColumnName());
						}
					}
				}
			}

			equalsTo.setRightExpression(new Column("dacr.data_id"));
			Table table = new Table(dataResource.getMapperBy());
			Alias alias = new Alias("dacr");
			table.setAlias(alias);
			String providerBy = dataResource.getProviderBy();
			String whereString = "dacr.data_provider='" + providerBy + "' and dacr.creator_id='" + dataResource.getUserId() + "'";
			Expression whereExpression = CCJSqlParserUtil.parseCondExpression(whereString);
			if (plainSelect.getWhere() == null) {
				plainSelect.setWhere(whereExpression);
			} else {
				plainSelect.setWhere(new AndExpression(plainSelect.getWhere(), whereExpression));
			}

			Join addJoin = SelectUtils.addJoin(select, table, equalsTo);
			addJoin.setRight(true);
			System.out.println(select.toString());
			sql = select.toString();
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
