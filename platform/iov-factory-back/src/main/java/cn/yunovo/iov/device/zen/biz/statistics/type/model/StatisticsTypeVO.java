package cn.yunovo.iov.device.zen.biz.statistics.type.model;


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
@ApiModel(value="", description="查询对象")
public class StatisticsTypeVO {
	
	
	/**
	 * 主键ID
	 */
	@ApiModelProperty(value = "主键ID", name="id", hidden = true)
	private Integer id;
	
	/**
	 * 渠道ID
	 */
	@ApiModelProperty(value = "渠道ID", name="channelId")
	private Integer channelId;
	
	/**
	 * 工厂代码
	 */
	@ApiModelProperty(value = "工厂代码", name="factoryName")
	@Size(min = 0, max = 64, message = "[factoryName]长度在[0,64]之间")
	private String factoryName;
	
	/**
	 * 机构代码
	 */
	@ApiModelProperty(value = "机构代码", name="orgCode")
	@Size(min = 0, max = 64, message = "[orgCode]长度在[0,64]之间")
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
	@Size(min = 0, max = 64, message = "[createId]长度在[0,64]之间")
	private String createId;
	
	/**
	 * 创建时间
	 */
	@ApiModelProperty(value = "创建时间", name="createTime", hidden = true)
	private String createTime;
	
}
