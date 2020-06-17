package cn.yunovo.iov.device.zen.biz.other.data.model;

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
public class DictionaryQuery {
	
	
	/**
	 * 词KEY
	 */
	@ApiModelProperty(value = "词KEY", name="wordKey")
	private String wordKey;
	
	/**
	 * 词值
	 */
	@ApiModelProperty(value = "词值", name="wordValue")
	private String wordValue;
	
	/**
	 * 词类型
	 */
	@ApiModelProperty(value = "词类型", name="wordType")
	private Integer wordType;
	
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
