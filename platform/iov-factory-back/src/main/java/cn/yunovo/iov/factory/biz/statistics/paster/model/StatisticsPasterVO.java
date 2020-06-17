package cn.yunovo.iov.factory.biz.statistics.paster.model;


import javax.validation.constraints.NotBlank;
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
@ToString
@ApiModel(value="统计贴片上报", description="统计贴片上报查询对象")
public class StatisticsPasterVO {
	
	
	/**
	 * 主键ID
	 */
	@ApiModelProperty(value = "主键ID", name="id", hidden = true)
	private Integer id;
	
	@ApiModelProperty(value = "品牌名称(机构代码orgCode)", name="brandName")
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
	 * 错误数量
	 */
	@ApiModelProperty(value = "错误数量", name="errorNumber")
	private Integer errorNumber;
	
	/**
	 * 贴片数量
	 */
	@ApiModelProperty(value = "贴片数量", name="pasterNumber")
	private Integer pasterNumber;
	
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
	 * 修改时间
	 */
	@ApiModelProperty(value = "修改时间", name="updateTime", hidden = true)
	private String updateTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFactoryName() {
		return factoryName;
	}

	public void setFactoryName(String factoryName) {
		this.factoryName = factoryName;
	}

	public Integer getErrorNumber() {
		return errorNumber;
	}

	public void setErrorNumber(Integer errorNumber) {
		this.errorNumber = errorNumber;
	}

	public Integer getPasterNumber() {
		return pasterNumber;
	}

	public void setPasterNumber(Integer pasterNumber) {
		this.pasterNumber = pasterNumber;
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
