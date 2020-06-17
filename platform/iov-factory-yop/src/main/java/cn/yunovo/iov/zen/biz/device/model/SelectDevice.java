package cn.yunovo.iov.zen.biz.device.model;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "DeviceInfo 对象", description = "设备生产阶段，上报设备信息到云端")
public class SelectDevice {

	@ApiModelProperty(value = "设备SN")
	private String sn;

	@ApiModelProperty(value = "设备IMEI")
	private String imei;

	@ApiModelProperty(value = "卡ICCID")
	private String iccid;

}
