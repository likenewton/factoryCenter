package cn.yunovo.iov.factory.biz.shipping.channel.model;

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
@ApiModel(value="渠道管理", description="渠道管理查询对象")
public class ChannelQuery {
	
	
	/**
	 * 父ID
	 */
	@ApiModelProperty(value = "父ID", name="parentId")
	private Integer parentId;
	
	/**
	 * 渠道名称
	 */
	@ApiModelProperty(value = "渠道名称", name="channelName")
	private String channelName;
	
	/**
	 * 品牌名称(机构代码orgCode)
	 */
	@ApiModelProperty(value = "品牌名称(机构代码orgCode)", name="brandName")
	private String brandName;
	
	/**
	 * 区域
	 */
	@ApiModelProperty(value = "区域", name="area")
	private String area;
	
	/**
	 * 区域ID串，逗号隔开
	 */
	@ApiModelProperty(value = "区域ID串，逗号隔开", name="areaIds")
	private String areaIds;
	
	/**
	 * 父ID串，逗号隔开
	 */
	@ApiModelProperty(value = "父ID串，逗号隔开", name="paths")
	private String paths;
	
	/**
	 * 级别
	 */
	@ApiModelProperty(value = "级别", name="level")
	private Integer level;
	
	/**
	 * 联系人
	 */
	@ApiModelProperty(value = "联系人", name="contacts")
	private String contacts;
	
	/**
	 * 手机号
	 */
	@ApiModelProperty(value = "手机号", name="phone")
	private String phone;
	
	/**
	 * 渠道详细地址
	 */
	@ApiModelProperty(value = "渠道详细地址", name="address")
	private String address;
	
	/**
	 * 创建时间
	 */
	@ApiModelProperty(value = "创建时间", name="createTime")
	private String createTime;
	
	/**
	 * 更新时间
	 */
	@ApiModelProperty(value = "更新时间", name="updateTime")
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
