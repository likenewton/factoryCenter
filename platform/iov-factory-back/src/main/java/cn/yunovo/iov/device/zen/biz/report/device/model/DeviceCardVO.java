package cn.yunovo.iov.device.zen.biz.report.device.model;


import javax.validation.constraints.Size;

import cn.yunovo.iov.device.zen.framework.excel.ExcelResources;
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
@ApiModel(value="三码关系表", description="三码关系表查询对象")
public class DeviceCardVO {
	
	
	/**
	 * 设备与卡的关系
	 */
	@ApiModelProperty(value = "设备与卡的关系", name="id", hidden = true)
	private Integer id;
	
	/**
	 * ICCID
	 */
	@ApiModelProperty(value = "ICCID", name="iccid")
	@Size(min = 1, max = 32, message = "[iccid]长度在[1,32]之间")
	@ExcelResources(title="ICCID",order=1,width=8000)
	private String iccid;
	
	/**
	 * IMEI
	 */
	@ApiModelProperty(value = "IMEI", name="imei")
	@Size(min = 1, max = 255, message = "[imei]长度在[1,255]之间")
	@ExcelResources(title="IMEI",order=2,width=8000)
	private String imei;
	
	/**
	 * 设备SN
	 */
	@ApiModelProperty(value = "设备SN", name="sn")
	@Size(min = 1, max = 64, message = "[sn]长度在[1,64]之间")
	@ExcelResources(title="设备SN",order=3,width=8000)
	private String sn;
	
	/**
	 * 创建时间
	 */
	@ApiModelProperty(value = "创建时间", name="addDatetime")
	@ExcelResources(title="创建时间",order=4,width=8000)
	private String addDatetime;
	
	/**
	 * 更新时间
	 */
	@ApiModelProperty(value = "更新时间", name="updateDatetime")
	private String updateDatetime;
	
}
