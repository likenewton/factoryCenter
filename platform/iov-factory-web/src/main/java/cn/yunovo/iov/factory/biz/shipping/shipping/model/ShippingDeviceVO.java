package cn.yunovo.iov.factory.biz.shipping.shipping.model;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import cn.yunovo.iov.factory.framework.excel.ExcelResources;
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
@ApiModel(value="发货设备IMEI清单", description="发货设备IMEI清单查询对象")
public class ShippingDeviceVO {
	
	
	/**
	 * 主键ID
	 */
	@ApiModelProperty(value = "主键ID", name="id", hidden = true)
	private Integer id;
	
	/**
	 * 设备IMEI
	 */
	@ApiModelProperty(value = "设备IMEI", name="imei")
	@NotBlank(message="[imei]为必填项")
	@Size(min = 0, max = 64, message = "[imei]长度在[0,64]之间")
	@ExcelResources(title="imei",order=1,width=8000)
	private String imei;
	
	/**
	 * 发货ID
	 */
	@ApiModelProperty(value = "发货ID", name="shippingId")
	private Integer shippingId;
	
	/**
	 * 创建时间
	 */
	@ApiModelProperty(value = "创建时间", name="createTime", hidden = true)
	private String createTime;
	
}
