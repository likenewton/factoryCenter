package cn.yunovo.iov.device.zen.biz.statistics.area.model;


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
@ApiModel(value="统计发货管理", description="统计发货管理查询对象")
public class StatisticsAreaVO {
	
	
	/**
	 * 主键ID
	 */
	@ApiModelProperty(value = "主键ID", name="id", hidden = true)
	private Integer id;
	
	/**
	 * 设备发货数量
	 */
	@ApiModelProperty(value = "设备发货数量", name="deviceNumber")
	@Digits(fraction = 0, integer = Integer.MAX_VALUE)
	private Integer deviceNumber;
	
	/**
	 * 渠道ID
	 */
	@ApiModelProperty(value = "渠道ID", name="channelId")
	private Integer channelId;
	
	/**
	 * 工厂名称
	 */
	@ApiModelProperty(value = "工厂名称", name="factoryName")
	@Size(min = 0, max = 128, message = "[factoryName]长度在[0,128]之间")
	private String factoryName;
	
	/**
	 * 品牌名称(机构代码orgCode)
	 */
	@ApiModelProperty(value = "品牌名称(机构代码orgCode)", name="brandName")
	@Size(min = 0, max = 64, message = "[brandName]长度在[0,64]之间")
	private String brandName;
	
	/**
	 * 区域
	 */
	@ApiModelProperty(value = "区域", name="area")
	@NotBlank(message="[area]为必填项")
	@Size(min = 0, max = 255, message = "[area]长度在[0,255]之间")
	private String area;
	
	/**
	 * 创建时间
	 */
	@ApiModelProperty(value = "创建时间", name="createTime", hidden = true)
	private String createTime;
	
	/**
	 * 修改时间
	 */
	@ApiModelProperty(value = "修改时间", name="updateTime", hidden = true)
	private String updateTime;
	
}
