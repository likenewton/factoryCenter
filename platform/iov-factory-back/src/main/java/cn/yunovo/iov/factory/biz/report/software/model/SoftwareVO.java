package cn.yunovo.iov.factory.biz.report.software.model;


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
@ApiModel(value="zen 平台，上报设备信息到云端", description="zen 平台，上报设备信息到云端查询对象")
public class SoftwareVO {
	
	
	/**
	 * 主键
	 */
	@ApiModelProperty(value = "主键", name="id", hidden = true)
	private Integer id;
	
	/**
	 * 软件代码
	 */
	@ApiModelProperty(value = "软件代码", name="swCode")
	@Size(min = 1, max = 32, message = "[swCode]长度在[1,32]之间")
	private String swCode;
	
	/**
	 * ROM 版本号
	 */
	@ApiModelProperty(value = "ROM 版本号", name="romVersion")
	@Size(min = 1, max = 32, message = "[romVersion]长度在[1,32]之间")
	private String romVersion;
	
	/**
	 * 设备项目型号：D10
	 */
	@ApiModelProperty(value = "设备项目型号：D10", name="projectModel")
	@Size(min = 1, max = 64, message = "[projectModel]长度在[1,64]之间")
	private String projectModel;
	
	/**
	 * 设备ROM包机构编码：OG-00171
	 */
	@ApiModelProperty(value = "设备ROM包机构编码：OG-00171", name="orgCode")
	@Size(min = 1, max = 255, message = "[orgCode]长度在[1,255]之间")
	private String orgCode;
	
	/**
	 * 上报者
	 */
	@ApiModelProperty(value = "上报者", name="reporter")
	@Size(min = 1, max = 64, message = "[reporter]长度在[1,64]之间")
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
	
}
