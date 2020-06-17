package cn.yunovo.iov.device.zen.biz.other.log.model;

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
public class DeviceQuery {
	
	
	/**
	 * iccid
	 */
	@ApiModelProperty(value = "iccid", name="iccid")
	private String iccid;
	
	/**
	 * 0:修改SN,1:更新组织机构,2:修改ICCID
	 */
	@ApiModelProperty(value = "0:修改SN,1:更新组织机构,2:修改ICCID", name="optType")
	private Integer optType;
	
	/**
	 * 描述
	 */
	@ApiModelProperty(value = "描述", name="optDesc")
	private String optDesc;
	
	/**
	 * 上报者
	 */
	@ApiModelProperty(value = "上报者", name="reporter")
	private String reporter;
	
	/**
	 * 创建时间
	 */
	@ApiModelProperty(value = "创建时间", name="addDatetime")
	private String addDatetime;
	
	/**
	 * 查询时间
	 */
	@ApiModelProperty(value = "查询时间", name="dateTime")
	private String dateTime;
}
