package cn.yunovo.iov.device.zen.biz.statistics.repetition.model;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
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
public class RepetitionReportVO {
	
	
	/**
	 * 主键ID
	 */
	@ApiModelProperty(value = "主键ID", name="id", hidden = true)
	private Integer id;
	
	/**
	 * 字段名称
	 */
	@ApiModelProperty(value = "字段名称", name="fieldName")
	@NotBlank(message="[fieldName]为必填项")
	@Size(min = 1, max = 32, message = "[fieldName]长度在[1,32]之间")
	private String fieldName;
	
	/**
	 * 字段值
	 */
	@ApiModelProperty(value = "字段值", name="fieldValue")
	@Size(min = 1, max = 64, message = "[fieldValue]长度在[1,64]之间")
	private String fieldValue;
	
	/**
	 * 上报次数
	 */
	@ApiModelProperty(value = "上报次数", name="reportTimes")
	@Digits(fraction = 0, integer = Integer.MAX_VALUE)
	private Integer reportTimes;
	
	/**
	 * 最后上报时间
	 */
	@ApiModelProperty(value = "最后上报时间", name="lastDatetime")
	private String lastDatetime;
	
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
