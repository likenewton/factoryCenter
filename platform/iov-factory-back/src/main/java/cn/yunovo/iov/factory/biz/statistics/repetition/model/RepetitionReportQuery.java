package cn.yunovo.iov.factory.biz.statistics.repetition.model;

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
public class RepetitionReportQuery {
	
	
	/**
	 * 字段名称
	 */
	@ApiModelProperty(value = "字段名称", name="fieldName")
	private String fieldName;
	
	/**
	 * 字段值
	 */
	@ApiModelProperty(value = "字段值", name="fieldValue")
	private String fieldValue;
	
	/**
	 * 上报次数
	 */
	@ApiModelProperty(value = "上报次数", name="reportTimes")
	private Integer reportTimes;
	
	/**
	 * 最后上报时间
	 */
	@ApiModelProperty(value = "最后上报时间", name="lastDatetime")
	private String lastDatetime;
	
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
