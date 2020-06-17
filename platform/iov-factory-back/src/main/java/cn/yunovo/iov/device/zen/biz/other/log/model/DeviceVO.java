package cn.yunovo.iov.device.zen.biz.other.log.model;


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
public class DeviceVO {
	
	
	/**
	 * id
	 */
	@ApiModelProperty(value = "id", name="id", hidden = true)
	private Integer id;
	
	/**
	 * iccid
	 */
	@ApiModelProperty(value = "iccid", name="iccid")
	@NotBlank(message="[iccid]为必填项")
	@Size(min = 1, max = 32, message = "[iccid]长度在[1,32]之间")
	private String iccid;
	
	/**
	 * 0:修改SN,1:更新组织机构,2:修改ICCID
	 */
	@ApiModelProperty(value = "0:修改SN,1:更新组织机构,2:修改ICCID", name="optType")
	@Digits(fraction = 0, integer = Integer.MAX_VALUE)
	private Integer optType;
	
	/**
	 * 描述
	 */
	@ApiModelProperty(value = "描述", name="optDesc")
	@Size(min = 1, max = 128, message = "[optDesc]长度在[1,128]之间")
	private String optDesc;
	
	/**
	 * 上报者
	 */
	@ApiModelProperty(value = "上报者", name="reporter")
	@Size(min = 1, max = 255, message = "[reporter]长度在[1,255]之间")
	private String reporter;
	
	/**
	 * 创建时间
	 */
	@ApiModelProperty(value = "创建时间", name="addDatetime")
	private String addDatetime;
	
}
