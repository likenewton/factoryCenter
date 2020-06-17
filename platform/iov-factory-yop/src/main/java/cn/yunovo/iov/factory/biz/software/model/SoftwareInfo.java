package cn.yunovo.iov.factory.biz.software.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "软件信息对象", description = "软件信息对象")
public class SoftwareInfo {

	@ApiModelProperty(value = "软件代码：AAAA")
	private String swCode;
	
	@ApiModelProperty(value = "ROM版本号：CK02-DX-D10")
	private String romVersion;
	
	@ApiModelProperty(value = "设备项目型号：D10")
	private String projectModel;
	
	@ApiModelProperty(value = "设备ROM包机构编码：OG-00171")
	private String orgCode;
	
	@ApiModelProperty(value = "设备信息报告者，用于追踪")
	private String reporter;
	
	@ApiModelProperty(value = "设备信息上报时间")
	private String reportTime;
}
