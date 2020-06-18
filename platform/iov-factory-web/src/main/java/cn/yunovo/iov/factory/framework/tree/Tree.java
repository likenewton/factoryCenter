package cn.yunovo.iov.factory.framework.tree;

import cn.yunovo.iov.boot.autoconfigure.request.select.Condition;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Tree implements Condition {
	public final static String expression = "tree=";
	private String tree;
}
