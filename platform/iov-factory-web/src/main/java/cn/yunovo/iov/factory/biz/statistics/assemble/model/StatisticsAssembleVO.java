package cn.yunovo.iov.factory.biz.statistics.assemble.model;


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
@ToString
@ApiModel(value="统计组织上报", description="统计组织上报查询对象")
public class StatisticsAssembleVO {
	
	
	/**
	 * 主键ID
	 */
	@ApiModelProperty(value = "主键ID", name="id", hidden = true)
	private Integer id;
	
	/**
	 * 品牌名称(机构代码orgCode)
	 */
	@ApiModelProperty(value = "品牌名称(机构代码orgCode)", name="brandName")
	@NotBlank(message="[brandName]为必填项")
	@Size(min = 0, max = 64, message = "[brandName]长度在[0,64]之间")
	private String brandName;
	
	/**
	 * 工厂名称
	 */
	@ApiModelProperty(value = "工厂名称", name="factoryName")
	@NotBlank(message="[factoryName]为必填项")
	@Size(min = 0, max = 128, message = "[factoryName]长度在[0,128]之间")
	private String factoryName;
	
	/**
	 * 设备数量
	 */
	@ApiModelProperty(value = "设备数量", name="deviceNumber")
	@Digits(fraction = 0, integer = Integer.MAX_VALUE)
	private Integer deviceNumber;
	
	/**
	 * 错误数量
	 */
	@ApiModelProperty(value = "错误数量", name="errorNumber")
	private Integer errorNumber;
	
	/**
	 * 上报时间
	 */
	@ApiModelProperty(value = "上报时间", name="reportTime")
	private String reportTime;
	
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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getFactoryName() {
		return factoryName;
	}

	public void setFactoryName(String factoryName) {
		this.factoryName = factoryName;
	}

	public Integer getDeviceNumber() {
		return deviceNumber;
	}

	public void setDeviceNumber(Integer deviceNumber) {
		this.deviceNumber = deviceNumber;
	}

	public Integer getErrorNumber() {
		return errorNumber;
	}

	public void setErrorNumber(Integer errorNumber) {
		this.errorNumber = errorNumber;
	}

	public String getReportTime() {
		if(null != reportTime) {
			return reportTime.substring(0, 10);
		}
		return reportTime;
	}

	public void setReportTime(String reportTime) {
		this.reportTime = reportTime;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	
	
	
}
