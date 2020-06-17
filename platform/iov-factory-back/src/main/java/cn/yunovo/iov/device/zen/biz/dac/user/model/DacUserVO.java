package cn.yunovo.iov.device.zen.biz.dac.user.model;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
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
@ApiModel(value="", description="查询对象")
public class DacUserVO {
	
	
	/**
	 * 主键ID
	 */
	@ApiModelProperty(value = "主键ID", name="id", hidden = true)
	private Integer id;
	
	/**
	 * 用户ID
	 */
	@ApiModelProperty(value = "用户ID", name="userId")
	@NotBlank(message="[userId]为必填项")
	@Size(min = 0, max = 64, message = "[userId]长度在[0,64]之间")
	private String userId;
	
	/**
	 * 用户名称
	 */
	@ApiModelProperty(value = "用户名称", name="userMapper")
	@NotBlank(message="[userMapper]为必填项")
	@Size(min = 0, max = 128, message = "[userMapper]长度在[0,128]之间")
	private String userMapper;
	
	/**
	 * 用户类型:1机构(品牌)，2工厂
	 */
	@ApiModelProperty(value = "用户类型:1机构(品牌)，2工厂", name="userType")
	@Digits(fraction = 0, integer = Integer.MAX_VALUE)
	private Integer userType;
	
}
