package cn.yunovo.iov.factory.biz.test.model;

import lombok.Data;
import lombok.ToString;

/**
 * 
 * 与数据库表结构一一对应，通过DAO层向上传输数据源对象。<br/>
 * 
 * @author huangzz
 *
 */
@Data
@ToString
public class DeviceTestItemDO {
	
	/**
	 * 主键
	 */
	private Integer id;
	
	/**
	 * 测试主键ID
	 */
	private Integer testId;
	
	/**
	 * 测试项
	 */
	private String testItem;
	
	/**
	 * 测试值
	 */
	private String testValue;
	
	/**
	 * 测试结果
	 */
	private Integer testResult;
	
	/**
	 * 1自动测试项,2手动测试项
	 */
	private Integer testMethod;
}
