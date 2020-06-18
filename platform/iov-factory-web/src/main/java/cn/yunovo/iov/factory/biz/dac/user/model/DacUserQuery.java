package cn.yunovo.iov.factory.biz.dac.user.model;

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
public class DacUserQuery {
	
	
	/**
	 * 用户ID
	 */
	@ApiModelProperty(value = "用户ID", name="userId")
	private String userId;
	
	/**
	 * 用户名称
	 */
	@ApiModelProperty(value = "用户名称", name="userMapper")
	private String userMapper;
	
	/**
	 * 用户类型:1机构(品牌)，2工厂
	 */
	@ApiModelProperty(value = "用户类型:1机构(品牌)，2工厂", name="userType")
	private Integer userType;
	
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
