package cn.yunovo.iov.device.zen.biz.shipping.channel.model;


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
@ApiModel(value="渠道管理", description="渠道管理查询对象")
public class ChannelVO {
	
	
	/**
	 * 主键ID
	 */
	@ApiModelProperty(value = "主键ID", name="id", hidden = true)
	private Integer id;
	
	/**
	 * 父ID
	 */
	@ApiModelProperty(value = "父ID", name="parentId")
	@Digits(fraction = 0, integer = Integer.MAX_VALUE)
	private Integer parentId;
	
	/**
	 * 渠道名称
	 */
	@ApiModelProperty(value = "渠道名称", name="channelName")
	@NotBlank(message="[channelName]为必填项")
	@Size(min = 0, max = 64, message = "[channelName]长度在[0,64]之间")
	private String channelName;
	
	/**
	 * 品牌名称(机构代码orgCode)
	 */
	@ApiModelProperty(value = "品牌名称(机构代码orgCode)", name="brandName")
	@Size(min = 0, max = 64, message = "[brandName]长度在[0,64]之间")
	private String brandName;
	
	/**
	 * 区域
	 */
	@ApiModelProperty(value = "区域", name="area")
	@NotBlank(message="[area]为必填项")
	@Size(min = 0, max = 64, message = "[area]长度在[0,64]之间")
	private String area;
	
	/**
	 * 区域ID串，逗号隔开
	 */
	@ApiModelProperty(value = "区域ID串，逗号隔开", name="areaIds")
	@NotBlank(message="[areaIds]为必填项")
	@Size(min = 0, max = 64, message = "[areaIds]长度在[0,64]之间")
	private String areaIds;
	
	/**
	 * 父ID串，逗号隔开
	 */
	@ApiModelProperty(value = "父ID串，逗号隔开", name="paths")
	@Size(min = 0, max = 255, message = "[paths]长度在[0,255]之间")
	private String paths;
	
	/**
	 * 级别
	 */
	@ApiModelProperty(value = "级别", name="level")
	@Digits(fraction = 0, integer = Integer.MAX_VALUE)
	private Integer level;
	
	/**
	 * 联系人
	 */
	@ApiModelProperty(value = "联系人", name="contacts")
	@NotBlank(message="[contacts]为必填项")
	@Size(min = 0, max = 64, message = "[contacts]长度在[0,64]之间")
	private String contacts;
	
	/**
	 * 手机号
	 */
	@ApiModelProperty(value = "手机号", name="phone")
	@NotBlank(message="[phone]为必填项")
	@Size(min = 0, max = 13, message = "[phone]长度在[0,13]之间")
	private String phone;
	
	/**
	 * 渠道详细地址
	 */
	@ApiModelProperty(value = "渠道详细地址", name="address")
	@Size(min = 0, max = 128, message = "[address]长度在[0,128]之间")
	private String address;
	
	/**
	 * 创建时间
	 */
	@ApiModelProperty(value = "创建时间", name="createTime", hidden = true)
	private String createTime;
	
	/**
	 * 更新时间
	 */
	@ApiModelProperty(value = "更新时间", name="updateTime", hidden = true)
	private String updateTime;
	
}
