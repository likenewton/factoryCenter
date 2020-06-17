package cn.yunovo.iov.zen.biz.packing.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "PackingInfo 对象", description = "设备生产阶段，外包装信息上报到云端")
public class PackingReportQuery {

	@ApiModelProperty(value = "设备IMEI")
	private String imei;

	@ApiModelProperty(value = "软件代码")
	private String swCode;

}
