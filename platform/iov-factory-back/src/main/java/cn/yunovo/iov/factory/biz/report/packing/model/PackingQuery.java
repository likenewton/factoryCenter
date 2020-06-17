package cn.yunovo.iov.factory.biz.report.packing.model;

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
@ApiModel(value="设备生产阶段，上报组装信息到云端", description="设备生产阶段，上报组装信息到云端查询对象")
public class PackingQuery {
	
	
	/**
	 * 设备IMEI
	 */
	@ApiModelProperty(value = "设备IMEI", name="imei")
	private String imei;
	
	/**
	 * 软件代码
	 */
	@ApiModelProperty(value = "软件代码", name="swCode")
	private String swCode;
	
	/**
	 * 16位字符，标识硬件信息，用于生产追踪
	 */
	@ApiModelProperty(value = "16位字符，标识硬件信息，用于生产追踪", name="yunovoCode")
	private String yunovoCode;
	
	/**
	 * 上报者
	 */
	@ApiModelProperty(value = "上报者", name="reporter")
	private String reporter;
	
	/**
	 * 创建时间
	 */
	@ApiModelProperty(value = "创建时间", name="createDatetime")
	private String createDatetime;
	
	/**
	 * 查询时间
	 */
	@ApiModelProperty(value = "查询时间", name="dateTime")
	private String dateTime;
}
