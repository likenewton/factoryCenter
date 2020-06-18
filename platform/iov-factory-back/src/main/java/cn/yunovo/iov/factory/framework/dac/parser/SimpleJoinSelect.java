package cn.yunovo.iov.factory.framework.dac.parser;

import java.util.ArrayList;

import java.util.List;

import net.sf.jsqlparser.expression.Alias;

import net.sf.jsqlparser.schema.Table;

import net.sf.jsqlparser.statement.select.Join;

import net.sf.jsqlparser.statement.select.PlainSelect;

public class SimpleJoinSelect {

	public static void main(String[] args) {
		SimpleJoinSelect demo = new SimpleJoinSelect();
		String table = "KB_ZX_CUST_PROD_SUM";
		String[][] condition = { { "MONTH", "12" }, { "VIP_TYPE", "高值商客" }, { "ZX_CUST_CODE", "12" } };
		String[][] outColumn = { { "MONTH" }, { "SUM_TOTAL" } };
		String limit = "122";
		demo.selectObjectDemo(table, null, condition, outColumn, null, limit);
		demo.jsqlDemo(table, null, condition, outColumn, null, limit);
	}

	// SelectObject使用
	SelectObject selectObjectDemo(String table, String aliasName, String[][] condition, String[][] outColumn, String[][] groubyColumn, String limit) {
		SimpleSelectDemo demo = new SimpleSelectDemo();
		SelectObject selectObject = demo.selectObjectDemo(table, aliasName, condition, outColumn, groubyColumn, limit);
		selectObject.addJoin(table, "T_B", false).setSimple(true);
		System.out.println("simple Join :" + selectObject);
		return selectObject;
	}

	//JSqlParser直接调用
	PlainSelect jsqlDemo(String ta, String aliasName, String[][] condition, String[][] outColumn, String[][] groubyColumn, String rowCount) {

		SimpleSelectDemo demo = new SimpleSelectDemo();

		PlainSelect plainSelect = demo.jsqlDemo(ta, aliasName, condition, outColumn, groubyColumn, rowCount);

		List<Join> joins = new ArrayList<Join>();

		Join join = new Join();

		Table table = new Table(ta);

		table.setAlias(new Alias("T_B", false));

		join.setRightItem(table);

		join.setSimple(true);

		joins.add(join);

		plainSelect.setJoins(joins);

		System.out.println("simple Join :" + plainSelect);

		return plainSelect;

	}

}
