package cn.yunovo.iov.zen.biz.packing.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "PackingInfo 对象", description = "设备生产阶段，外包装信息上报到云端")
public class PackingReport {

	@ApiModelProperty(value = "设备IMEI")
	private String imei;

	@ApiModelProperty(value = "软件代码")
	private String swCode;

	@ApiModelProperty(value = "16位字符，标识硬件信息，用于生产追踪")
	private String yunovoCode;

	@ApiModelProperty(value = "设备信息报告者，用于追踪")
	private String reporter;

}
