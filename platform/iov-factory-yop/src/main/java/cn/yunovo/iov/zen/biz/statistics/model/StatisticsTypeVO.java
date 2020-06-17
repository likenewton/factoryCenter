package cn.yunovo.iov.zen.biz.statistics.model;


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
	 * 统计类型,1贴片统计，2组装统计
	 */
	@ApiModelProperty(value = "统计类型,1贴片统计，2组装统计", name="statisticsType")
	private Integer statisticsType;
	
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
	 * 创建时间
	 */
	@ApiModelProperty(value = "创建时间", name="createTime", hidden = true)
	private String createTime;
	
}
