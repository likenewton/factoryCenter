package cn.yunovo.iov.zen.biz.test.model;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.Digits;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * 展示对象：SampleVO，Sample一般为网页名称<br/>
 * 
 * @author huangzz
 *
 */
@Data
@ToString
@ApiModel(value="设备测试项", description="设备测试项查询对象")
public class DeviceTestItemVO {
	
	
	/**
	 * 主键
	 */
	@ApiModelProperty(value = "主键", name="id", hidden = true)
	@JsonIgnore
	private Integer id;
	
	/**
	 * 测试主键ID
	 */
	@ApiModelProperty(value = "测试主键ID", name="testId")
	@Digits(fraction = 0, integer = Integer.MAX_VALUE)
	private Integer testId;
	
	/**
	 * 测试项
	 */
	@ApiModelProperty(value = "测试项", name="testItem")
	@NotBlank(message="[testItem]为必填项")
	@Size(min = 0, max = 128, message = "[testItem]长度在[0,128]之间")
	private String testItem;
	
	/**
	 * 测试值
	 */
	@ApiModelProperty(value = "测试值", name="testValue")
	@Size(min = 0, max = 255, message = "[testValue]长度在[0,255]之间")
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
	@Digits(fraction = 0, integer = Integer.MAX_VALUE)
	private Integer testMethod;
	
}
