package cn.yunovo.iov.device.zen.biz.report.packing.model;


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
@ApiModel(value="设备生产阶段，上报组装信息到云端", description="设备生产阶段，上报组装信息到云端查询对象")
public class PackingVO {
	
	
	/**
	 * 主键
	 */
	@ApiModelProperty(value = "主键", name="id", hidden = true)
	private Integer id;
	
	/**
	 * 设备IMEI
	 */
	@ApiModelProperty(value = "设备IMEI", name="imei")
	@Size(min = 1, max = 32, message = "[imei]长度在[1,32]之间")
	private String imei;
	
	/**
	 * 软件代码
	 */
	@ApiModelProperty(value = "软件代码", name="swCode")
	@Size(min = 1, max = 32, message = "[swCode]长度在[1,32]之间")
	private String swCode;
	
	/**
	 * 16位字符，标识硬件信息，用于生产追踪
	 */
	@ApiModelProperty(value = "16位字符，标识硬件信息，用于生产追踪", name="yunovoCode")
	@Size(min = 1, max = 16, message = "[yunovoCode]长度在[1,16]之间")
	private String yunovoCode;
	
	/**
	 * 上报者
	 */
	@ApiModelProperty(value = "上报者", name="reporter")
	@Size(min = 1, max = 64, message = "[reporter]长度在[1,64]之间")
	private String reporter;
	
	/**
	 * 创建时间
	 */
	@ApiModelProperty(value = "创建时间", name="createDatetime")
	private String createDatetime;
	
}
