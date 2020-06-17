package cn.yunovo.iov.zen.biz.device.model;

import java.util.Map;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "DeviceInfo 对象", description = "设备生产阶段，上报设备信息到云端")
public class DeviceReport {

	@ApiModelProperty(value = "设备SN")
	private String sn;

	@ApiModelProperty(value = "设备IMEI")
	private String imei;

	@ApiModelProperty(value = "卡ICCID")
	private String iccid;

	@ApiModelProperty(value = "设备信息报告者，用于追踪")
	private String reporter;

	@ApiModelProperty(value = "设备信息上报时间")
	private String reportTime;
	
	@ApiModelProperty(value = "false 云智SN，true 非云智SN")
	private String customSN;

	@ApiModelProperty(value = "额外信息")
	private Map<String, String> attached;
	
	@ApiModelProperty(value = "BT")
	private String btAddr;
	
	@ApiModelProperty(value = "WIFI地址")
	private String wifiAddr;

}
