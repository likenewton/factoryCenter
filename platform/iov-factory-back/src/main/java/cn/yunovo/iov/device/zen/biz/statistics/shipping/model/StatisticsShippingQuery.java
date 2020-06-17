package cn.yunovo.iov.device.zen.biz.statistics.shipping.model;

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
@ApiModel(value="统计发货管理", description="统计发货管理查询对象")
public class StatisticsShippingQuery {
	
	
	/**
	 * 渠道ID
	 */
	@ApiModelProperty(value = "渠道ID", name="channelId")
	private Integer channelId;
	
	/**
	 * 品牌名称(机构代码orgCode)
	 */
	@ApiModelProperty(value = "品牌名称(机构代码orgCode)", name="brandName")
	private String brandName;
	
	/**
	 * 渠道区域
	 */
	@ApiModelProperty(value = "渠道区域", name="area")
	private String area;
	
	/**
	 * 工厂名称
	 */
	@ApiModelProperty(value = "工厂名称", name="factoryName")
	private String factoryName;
	
	/**
	 * 设备数据
	 */
	@ApiModelProperty(value = "设备数据", name="deviceNumber")
	private Integer deviceNumber;
	
	/**
	 * 最后导入时间
	 */
	@ApiModelProperty(value = "最后导入时间", name="lastImporttime")
	private String lastImporttime;
	
	/**
	 * 创建时间
	 */
	@ApiModelProperty(value = "创建时间", name="createTime")
	private String createTime;
	
	/**
	 * 修改时间
	 */
	@ApiModelProperty(value = "修改时间", name="updateTime")
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
