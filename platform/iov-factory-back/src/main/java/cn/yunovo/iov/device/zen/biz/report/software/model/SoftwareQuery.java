package cn.yunovo.iov.device.zen.biz.report.software.model;

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
@ApiModel(value="zen 平台，上报设备信息到云端", description="zen 平台，上报设备信息到云端查询对象")
public class SoftwareQuery {
	
	
	/**
	 * 软件代码
	 */
	@ApiModelProperty(value = "软件代码", name="swCode")
	private String swCode;
	
	/**
	 * ROM 版本号
	 */
	@ApiModelProperty(value = "ROM 版本号", name="romVersion")
	private String romVersion;
	
	/**
	 * 设备项目型号：D10
	 */
	@ApiModelProperty(value = "设备项目型号：D10", name="projectModel")
	private String projectModel;
	
	/**
	 * 设备ROM包机构编码：OG-00171
	 */
	@ApiModelProperty(value = "设备ROM包机构编码：OG-00171", name="orgCode")
	private String orgCode;
	
	/**
	 * 上报者
	 */
	@ApiModelProperty(value = "上报者", name="reporter")
	private String reporter;
	
	/**
	 * 新增时间
	 */
	@ApiModelProperty(value = "新增时间", name="addDatetime")
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
