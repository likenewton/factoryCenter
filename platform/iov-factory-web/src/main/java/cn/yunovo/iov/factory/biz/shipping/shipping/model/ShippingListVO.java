package cn.yunovo.iov.factory.biz.shipping.shipping.model;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.validation.constraints.Digits;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.ToString;

/**
 * 展示对象：SampleVO，Sample一般为网页名称<br/>
 * 
 * @author huangzz
 *
 */
@ToString
@ApiModel(value="发货导入清单", description="发货导入清单查询对象")
public class ShippingListVO {
	
	
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
	 * 发货分组ID
	 */
	@ApiModelProperty(value = "发货分组ID", name="groupId")
	private Integer groupId;
	
	/**
	 * 渠道区域
	 */
	@ApiModelProperty(value = "渠道区域", name="area")
	private String area;
	
	/**
	 * 品牌名称
	 */
	@ApiModelProperty(value = "品牌名称", name="brandName")
	@NotBlank(message="[brandName]为必填项")
	@Size(min = 0, max = 255, message = "[brandName]长度在[0,255]之间")
	private String brandName;
	
	/**
	 * 工厂名称
	 */
	@ApiModelProperty(value = "工厂名称", name="factoryName")
	@Size(min = 0, max = 255, message = "[factoryName]长度在[0,255]之间")
	private String factoryName;
	
	/**
	 * 云智码
	 */
	@ApiModelProperty(value = "云智码", name="yunovoCode")
	@NotBlank(message="[yunovoCode]为必填项")
	@Size(min = 0, max = 64, message = "[yunovoCode]长度在[0,64]之间")
	private String yunovoCode;
	
	/**
	 * 设备数据
	 */
	@ApiModelProperty(value = "设备数据", name="deviceNumber")
	@Digits(fraction = 0, integer = Integer.MAX_VALUE)
	private Integer deviceNumber;
	
	/**
	 * 型号
	 */
	@ApiModelProperty(value = "型号", name="modelNumber")
	@Size(min = 0, max = 64, message = "[modelNumber]长度在[0,64]之间")
	private String modelNumber;
	
	/**
	 * 工单号
	 */
	@ApiModelProperty(value = "工单号", name="workOrderno")
	@Size(min = 0, max = 128, message = "[workOrderno]长度在[0,128]之间")
	private String workOrderno;
	
	/**
	 * 导入备注
	 */
	@ApiModelProperty(value = "导入备注", name="remark")
	@Size(min = 0, max = 255, message = "[remark]长度在[0,255]之间")
	private String remark;
	
	/**
	 * 操作人
	 */
	@ApiModelProperty(value = "操作人", name="operator")
	@Size(min = 0, max = 64, message = "[operator]长度在[0,64]之间")
	private String operator;
	
	/**
	 * 上报时间
	 */
	@ApiModelProperty(value = "上报时间", name="importTime")
	private String importTime;
	
	/**
	 * 生产日期
	 */
	@ApiModelProperty(value = "生产日期", name="productDate")
	private String productDate;
	
	/**
	 * 屏尺寸
	 */
	@ApiModelProperty(value = "屏尺寸", name="screenSize")
	@Size(min = 0, max = 64, message = "[screenSize]长度在[0,64]之间")
	private String screenSize;
	
	/**
	 * 创建时间
	 */
	@ApiModelProperty(value = "创建时间", name="createTime", hidden = true)
	private String createTime;
	
	@ApiModelProperty(value = "设备IMEI", name="imeis")
	private Object imeis;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getChannelId() {
		return channelId;
	}

	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
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

	public String getYunovoCode() {
		return yunovoCode;
	}

	public void setYunovoCode(String yunovoCode) {
		this.yunovoCode = yunovoCode;
	}

	public Integer getDeviceNumber() {
		return deviceNumber;
	}

	public void setDeviceNumber(Integer deviceNumber) {
		this.deviceNumber = deviceNumber;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getImportTime() {
		return importTime;
	}

	public void setImportTime(String importTime) {
		this.importTime = importTime;
	}

	public String getProductDate() {
		return productDate;
	}

	public void setProductDate(String productDate) {
		this.productDate = productDate;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public Object getImeis() {
		return imeis;
	}

	public void setImeis(Object imeis) {
		this.imeis = imeis;
	}

	public String getModelNumber() {
		return modelNumber;
	}

	public void setModelNumber(String modelNumber) {
		this.modelNumber = modelNumber;
	}

	public String getWorkOrderno() {
		return workOrderno;
	}

	public void setWorkOrderno(String workOrderno) {
		this.workOrderno = workOrderno;
	}

	public String getScreenSize() {
		return screenSize;
	}

	public void setScreenSize(String screenSize) {
		this.screenSize = screenSize;
	}

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}
	
	
	
}
