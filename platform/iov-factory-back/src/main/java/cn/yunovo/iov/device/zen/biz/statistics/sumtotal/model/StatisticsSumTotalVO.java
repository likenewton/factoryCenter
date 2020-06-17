package cn.yunovo.iov.device.zen.biz.statistics.sumtotal.model;


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
@ApiModel(value="统计组织上报", description="统计组织上报查询对象")
public class StatisticsSumTotalVO {
	
	
	/**
	 * 主键ID
	 */
	@ApiModelProperty(value = "主键ID", name="id", hidden = true)
	private Integer id;
	
	/**
	 * 统计类型，1贴片统计，2组装统计，3发货统计
	 */
	@ApiModelProperty(value = "统计类型，1贴片统计，2组装统计，3发货统计", name="statisticsType")
	@Digits(fraction = 0, integer = Integer.MAX_VALUE)
	private Integer statisticsType;
	
	/**
	 * 设备数量
	 */
	@ApiModelProperty(value = "设备数量", name="todayNumber")
	@Digits(fraction = 0, integer = Integer.MAX_VALUE)
	private Integer todayNumber;
	
	/**
	 * 累计总数量
	 */
	@ApiModelProperty(value = "累计总数量", name="sumTotal")
	private Integer sumTotal;
	
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
