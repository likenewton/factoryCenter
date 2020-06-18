package cn.yunovo.iov.factory.biz.software.model;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@ApiModel(value = "软件信息查询对象", description = "软件信息查询对象")
public class SoftwareInfoQuery {

	private String swCode;
	
	private String romVersion;
}
