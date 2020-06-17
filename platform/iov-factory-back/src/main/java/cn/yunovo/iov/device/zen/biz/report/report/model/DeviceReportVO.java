package cn.yunovo.iov.device.zen.biz.report.report.model;


import javax.validation.constraints.Size;

import cn.yunovo.iov.device.zen.framework.excel.ExcelResources;

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
@ApiModel(value="设备生产阶段，上报设备信息到云端", description="设备生产阶段，上报设备信息到云端查询对象")
public class DeviceReportVO {
	
	
	/**
	 * 主键
	 */
	@ApiModelProperty(value = "主键", name="id", hidden = true)
	private Integer id;
	
	/**
	 * 设备SN
	 */
	@ApiModelProperty(value = "设备SN", name="sn")
	@Size(min = 1, max = 32, message = "[sn]长度在[1,32]之间")
	@ExcelResources(title="设备SN",order=1,width=8000)
	private String sn;
	
	/**
	 * 设备IMEI
	 */
	@ApiModelProperty(value = "设备IMEI", name="imei")
	@Size(min = 1, max = 32, message = "[imei]长度在[1,32]之间")
	@ExcelResources(title="设备IMEI",order=2,width=8000)
	private String imei;
	
	/**
	 * 卡ICCID
	 */
	@ApiModelProperty(value = "卡ICCID", name="iccid")
	@Size(min = 1, max = 32, message = "[iccid]长度在[1,32]之间")
	@ExcelResources(title="卡ICCID",order=3,width=8000)
	private String iccid;
	
	/**
	 * 0:云智SN,1:非云智SN
	 */
	@ApiModelProperty(value = "0:云智SN,1:非云智SN", name="customSn")
	@Digits(fraction = 0, integer = Integer.MAX_VALUE)
	@ExcelResources(title="0:云智SN,1:非云智",order=4,width=8000)
	private Integer customSn;
	
	/**
	 * 额外信息
	 */
	@ApiModelProperty(value = "额外信息", name="attached")
	@Size(min = 1, max = 128, message = "[attached]长度在[1,128]之间")
	@ExcelResources(title="额外信息",order=5,width=8000)
	private String attached;
	
	/**
	 * BT
	 */
	@ApiModelProperty(value = "BT", name="btAddr")
	@Size(min = 1, max = 64, message = "[btAddr]长度在[1,64]之间")
	@ExcelResources(title="BT",order=6,width=8000)
	private String btAddr;
	
	/**
	 * WIFI地址
	 */
	@ApiModelProperty(value = "WIFI地址", name="wifiAddr")
	@Size(min = 1, max = 64, message = "[wifiAddr]长度在[1,64]之间")
	@ExcelResources(title="WIFI地址",order=7,width=8000)
	private String wifiAddr;
	
	/**
	 * 上报者
	 */
	@ApiModelProperty(value = "上报者", name="reporter")
	@Size(min = 1, max = 64, message = "[reporter]长度在[1,64]之间")
	@ExcelResources(title="上报者",order=8,width=8000)
	private String reporter;
	
	/**
	 * 新增时间
	 */
	@ApiModelProperty(value = "新增时间", name="createDatetime")
	@ExcelResources(title="新增时间",order=9,width=8000)
	private String createDatetime;
	
}
