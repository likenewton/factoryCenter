package cn.yunovo.iov.zen.biz.test.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * Query：数据查询对象，各层接收上层的查询请求。注意超过2个参数的查询封装，禁止使用Map类来传输。<br/>
 * 
 * 附加说明：可以继承DTO
 * 
 * @author huangzz
 *
 */
@Data
@ToString
@ApiModel(value="设备测试项", description="设备测试项查询对象")
public class DeviceTestItemQuery {
	
	
	/**
	 * 测试主键ID
	 */
	@ApiModelProperty(value = "测试主键ID", name="testId")
	private Integer testId;
	
	/**
	 * 测试项
	 */
	@ApiModelProperty(value = "测试项", name="testItem")
	private String testItem;
	
	/**
	 * 测试值
	 */
	@ApiModelProperty(value = "测试值", name="testValue")
	private String testValue;
	
	/**
	 * 测试结果
	 */
	@ApiModelProperty(value = "测试结果", name="testResult")
	private Integer testResult;
	
	/**
	 * 1自动测试项,2手动测试项
	 */
	@ApiModelProperty(value = "1自动测试项,2手动测试项", name="testMethod")
	private Integer testMethod;
	
	/**
	 * 数据按照创建时间查询-开始时间
	 */
	@ApiModelProperty(value = "数据按照创建时间查询-开始时间", name="selStartTime")
	private String selStartTime;
	
	/**
	 * 数据按照创建时间查询-结束时间
	 */
	@ApiModelProperty(value = "数据按照创建时间查询-结束时间", name="selEndTime")
	private String selEndTime;
}
