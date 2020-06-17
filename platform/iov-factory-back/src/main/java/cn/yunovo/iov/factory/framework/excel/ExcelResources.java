package cn.yunovo.iov.factory.framework.excel;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelResources {
	/**
	 * 属性的标题名称
	 * 
	 * @return
	 */
	String title();

	/**
	 * 在excel的顺序
	 * 
	 * @return
	 */
	int order() default 9999;

	/**
	 * 列的宽度
	 */
	int width() default 3000;
}