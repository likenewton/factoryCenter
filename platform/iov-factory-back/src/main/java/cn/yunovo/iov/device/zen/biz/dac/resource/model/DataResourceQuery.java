package cn.yunovo.iov.device.zen.biz.dac.resource.model;

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
@ApiModel(value="数据权限：资源，主体，规则关系表", description="数据权限：资源，主体，规则关系表查询对象")
public class DataResourceQuery {
	
	
	/**
	 * 数据表中的主键
	 */
	@ApiModelProperty(value = "数据表中的主键", name="dataId")
	private Integer dataId;
	
	/**
	 * 数据提供的表,值为主表
	 */
	@ApiModelProperty(value = "数据提供的表,值为主表", name="dataProvider")
	private String dataProvider;
	
	/**
	 * 数据创建者ID
	 */
	@ApiModelProperty(value = "数据创建者ID", name="creatorId")
	private String creatorId;
	
	/**
	 * 来源创建者ID
	 */
	@ApiModelProperty(value = "来源创建者ID", name="sourceCreatorId")
	private String sourceCreatorId;
	
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
