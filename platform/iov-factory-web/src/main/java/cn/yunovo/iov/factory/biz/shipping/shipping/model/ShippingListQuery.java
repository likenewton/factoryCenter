package cn.yunovo.iov.factory.biz.shipping.shipping.model;

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
@ApiModel(value="发货导入清单", description="发货导入清单查询对象")
public class ShippingListQuery {
	
	
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
	 * 品牌名称(机构代码orgCode)
	 */
	@ApiModelProperty(value = "品牌名称(机构代码orgCode)", name="brandName")
	private String brandName;
	
	/**
	 * 工厂名称
	 */
	@ApiModelProperty(value = "工厂名称", name="factoryName")
	private String factoryName;
	
	/**
	 * 云智码
	 */
	@ApiModelProperty(value = "云智码", name="yunovoCode")
	private String yunovoCode;
	
	/**
	 * 设备数据
	 */
	@ApiModelProperty(value = "设备数据", name="deviceNumber")
	private Integer deviceNumber;
	
	/**
	 * 型号
	 */
	@ApiModelProperty(value = "型号", name="modelNumber")
	private String modelNumber;
	
	/**
	 * 工单号
	 */
	@ApiModelProperty(value = "工单号", name="workOrderno")
	private String workOrderno;
	
	/**
	 * 导入备注
	 */
	@ApiModelProperty(value = "导入备注", name="remark")
	private String remark;
	
	/**
	 * 操作人
	 */
	@ApiModelProperty(value = "操作人", name="operator")
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
	private String screenSize;
	
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
