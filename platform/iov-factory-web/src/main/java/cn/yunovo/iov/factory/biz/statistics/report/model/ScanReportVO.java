package cn.yunovo.iov.factory.biz.statistics.report.model;


import javax.validation.constraints.Digits;

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
@ApiModel(value="", description="查询对象")
public class ScanReportVO {
	
	
	/**
	 * 主键ID
	 */
	@ApiModelProperty(value = "主键ID", name="id", hidden = true)
	private Integer id;
	
	/**
	 * 统计时间
	 */
	@ApiModelProperty(value = "统计时间", name="stateDatetime")
	private String stateDatetime;
	
	/**
	 * 扫描上报次数
	 */
	@ApiModelProperty(value = "扫描上报次数", name="reportTimes")
	@Digits(fraction = 0, integer = Integer.MAX_VALUE)
	private Integer reportTimes;
	
	/**
	 * 创建时间
	 */
	@ApiModelProperty(value = "创建时间", name="createTime", hidden = true)
	private String createTime;
	
	/**
	 * 更新时间
	 */
	@ApiModelProperty(value = "更新时间", name="updateTime", hidden = true)
	private String updateTime;
	
}
