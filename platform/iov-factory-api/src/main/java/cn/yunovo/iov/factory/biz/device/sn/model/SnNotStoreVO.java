package cn.yunovo.iov.factory.biz.device.sn.model;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

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
@ApiModel(value="测试上报数据时，SN未入库记录", description="测试上报数据时，SN未入库记录查询对象")
public class SnNotStoreVO {
	
	
	/**
	 * 主键ID
	 */
	@ApiModelProperty(value = "主键ID", name="id", hidden = true)
	private Integer id;
	
	/**
	 * 设备SN
	 */
	@ApiModelProperty(value = "设备SN", name="sn")
	@NotBlank(message="[sn]为必填项")
	@Size(min = 0, max = 63, message = "[sn]长度在[0,63]之间")
	private String sn;
	
	/**
	 * 创建时间
	 */
	@ApiModelProperty(value = "创建时间", name="createTime", hidden = true)
	private String createTime;
	
}
