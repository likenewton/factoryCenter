package cn.yunovo.iov.factory.biz.statistics.report.model;

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
@ApiModel(value="", description="查询对象")
public class ScanReportQuery {
	
	
	/**
	 * 统计时间
	 */
	@ApiModelProperty(value = "统计时间", name="stateDatetime")
	private String stateDatetime;
	
	/**
	 * 扫描上报次数
	 */
	@ApiModelProperty(value = "扫描上报次数", name="reportTimes")
	private Integer reportTimes;
	
	/**
	 * 创建时间
	 */
	@ApiModelProperty(value = "创建时间", name="createTime")
	private String createTime;
	
	/**
	 * 更新时间
	 */
	@ApiModelProperty(value = "更新时间", name="updateTime")
	private String updateTime;
	
	/**
	 * 查询时间
	 */
	@ApiModelProperty(value = "查询时间", name="dateTime")
	private String dateTime;
}
