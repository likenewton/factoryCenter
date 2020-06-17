package cn.yunovo.iov.device.zen.biz.statistics.sumtotal.model;

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
@ApiModel(value="统计组织上报", description="统计组织上报查询对象")
public class StatisticsSumTotalQuery {
	
	
	/**
	 * 统计类型，1贴片统计，2组装统计，3发货统计
	 */
	@ApiModelProperty(value = "统计类型，1贴片统计，2组装统计，3发货统计", name="statisticsType")
	private Integer statisticsType;
	
	/**
	 * 设备数量
	 */
	@ApiModelProperty(value = "设备数量", name="todayNumber")
	private Integer todayNumber;
	
	/**
	 * 累计总数量
	 */
	@ApiModelProperty(value = "累计总数量", name="sumTotal")
	private Integer sumTotal;
	
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
	 * 数据按照创建时间查询-开始时间
	 */
	@ApiModelProperty(value = "数据按照创建时间查询-开始时间", name="selStartTime")
	private String selStartTime;
	
	/**
	 * 数据按照创建时间查询-结束时间
	 */
	@ApiModelProperty(value = "数据按照创建时间查询-结束时间", name="selEndTime")
	private String selEndTime;
}
