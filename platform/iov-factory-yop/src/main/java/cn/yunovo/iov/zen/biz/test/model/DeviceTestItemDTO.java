package cn.yunovo.iov.zen.biz.test.model;


import lombok.Data;
import lombok.ToString;

/**
 * 数据传输对象：SampleDTO，Sample为业务领域相关的名称<br/>
 * 
 * @author huangzz
 *
 */
@Data
@ToString
public class DeviceTestItemDTO {
	
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
