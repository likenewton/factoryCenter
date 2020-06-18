package cn.yunovo.iov.factory.biz.statistics.type.model;

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
public class StatisticsTypeQuery {
	
	
	/**
	 * 渠道ID
	 */
	@ApiModelProperty(value = "渠道ID", name="channelId")
	private Integer channelId;
	
	/**
	 * 工厂代码
	 */
	@ApiModelProperty(value = "工厂代码", name="factoryName")
	private String factoryName;
	
	/**
	 * 机构代码
	 */
	@ApiModelProperty(value = "机构代码", name="orgCode")
	private String orgCode;
	
	/**
	 * 统计类型,1贴片统计，2组装统计，3发货统计
	 */
	@ApiModelProperty(value = "统计类型,1贴片统计，2组装统计，3发货统计", name="statisticsType")
	private Integer statisticsType;
	
	/**
	 * 创建者ID
	 */
	@ApiModelProperty(value = "创建者ID", name="createId")
	private String createId;
	
	/**
	 * 创建时间
	 */
	@ApiModelProperty(value = "创建时间", name="createTime")
	private String createTime;
	
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
