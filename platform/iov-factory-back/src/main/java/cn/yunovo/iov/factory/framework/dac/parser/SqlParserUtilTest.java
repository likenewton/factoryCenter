package cn.yunovo.iov.factory.framework.dac.parser;

import java.util.List;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.Join;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectBody;
import net.sf.jsqlparser.util.SelectUtils;

public class SqlParserUtilTest {

	public static void addJoin() {
		String sql = "select id,channel_id,brand_name,area,factory_name,device_number from t_statistics_shipping";
		try {
			Select select = (Select) CCJSqlParserUtil.parse(sql);
			SelectBody selectBody = select.getSelectBody();
			PlainSelect plainSelect = (PlainSelect) selectBody;
			SelectObject selectObject = new SelectObject();
		
			
			System.out.println("---------------------");
		} catch (JSQLParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void testParseJoin() {
		String sql = "select *from A as a left join B on a.bid = B.id left join C on A.cid = C.id left join D on B.did = D.id";
		try {
			Select select = (Select) CCJSqlParserUtil.parse(sql);
			SelectBody selectBody = select.getSelectBody();
			PlainSelect plainSelect = (PlainSelect) selectBody;
			List<Join> joins = plainSelect.getJoins();
			for (Join join : joins) {
				EqualsTo equalsTo = (EqualsTo) join.getOnExpression();
				Column leftExpression = (Column) equalsTo.getLeftExpression();
				Column rightExpression = (Column) equalsTo.getRightExpression();

				System.out.println("left table name:" + leftExpression.getTable());
				System.out.println("left table field:" + leftExpression.getColumnName());
				System.out.println("right table name:" + rightExpression.getTable());
				System.out.println("right table field:" + rightExpression.getColumnName());
				System.out.println("---------------------");
			}

		} catch (JSQLParserException e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		addJoin();

	}

}
