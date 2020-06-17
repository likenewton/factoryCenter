package cn.yunovo.iov.device.zen.biz.report.report.model;

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
@ApiModel(value="设备生产阶段，上报设备信息到云端", description="设备生产阶段，上报设备信息到云端查询对象")
public class DeviceReportQuery {
	
	
	/**
	 * 设备SN
	 */
	@ApiModelProperty(value = "设备SN", name="sn")
	private String sn;
	
	/**
	 * 设备IMEI
	 */
	@ApiModelProperty(value = "设备IMEI", name="imei")
	private String imei;
	
	/**
	 * 卡ICCID
	 */
	@ApiModelProperty(value = "卡ICCID", name="iccid")
	private String iccid;
	
	/**
	 * 0:云智SN,1:非云智SN
	 */
	@ApiModelProperty(value = "0:云智SN,1:非云智SN", name="customSn")
	private Integer customSn;
	
	/**
	 * 额外信息
	 */
	@ApiModelProperty(value = "额外信息", name="attached")
	private String attached;
	
	/**
	 * BT
	 */
	@ApiModelProperty(value = "BT", name="btAddr")
	private String btAddr;
	
	/**
	 * WIFI地址
	 */
	@ApiModelProperty(value = "WIFI地址", name="wifiAddr")
	private String wifiAddr;
	
	/**
	 * 上报者
	 */
	@ApiModelProperty(value = "上报者", name="reporter")
	private String reporter;
	
	/**
	 * 新增时间
	 */
	@ApiModelProperty(value = "新增时间", name="createDatetime")
	private String createDatetime;
	
	/**
	 * 查询时间
	 */
	@ApiModelProperty(value = "查询时间", name="dateTime")
	private String dateTime;
}
