package cn.yunovo.iov.factory.biz.report.device.model;

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
@ApiModel(value="三码关系表", description="三码关系表查询对象")
public class DeviceCardQuery {
	
	
	/**
	 * ICCID
	 */
	@ApiModelProperty(value = "ICCID", name="iccid")
	private String iccid;
	
	/**
	 * IMEI
	 */
	@ApiModelProperty(value = "IMEI", name="imei")
	private String imei;
	
	/**
	 * 设备SN
	 */
	@ApiModelProperty(value = "设备SN", name="sn")
	private String sn;
	
	/**
	 * 创建时间
	 */
	@ApiModelProperty(value = "创建时间", name="addDatetime")
	private String addDatetime;
	
	/**
	 * 更新时间
	 */
	@ApiModelProperty(value = "更新时间", name="updateDatetime")
	private String updateDatetime;
	
	/**
	 * 查询时间
	 */
	@ApiModelProperty(value = "查询时间", name="dateTime")
	private String dateTime;
}
