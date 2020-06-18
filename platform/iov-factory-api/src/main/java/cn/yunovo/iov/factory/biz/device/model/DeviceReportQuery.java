package cn.yunovo.iov.factory.biz.device.model;

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
public class DeviceReportQuery {
	
	@ApiModelProperty(value = "设备SN")
	private String sn;

	@ApiModelProperty(value = "设备IMEI")
	private String imei;

	@ApiModelProperty(value = "卡ICCID")
	private String iccid;
	
	@ApiModelProperty(value = "BT")
	private String btAddr;
	
	@ApiModelProperty(value = "WIFI地址")
	private String wifiAddr;
}
