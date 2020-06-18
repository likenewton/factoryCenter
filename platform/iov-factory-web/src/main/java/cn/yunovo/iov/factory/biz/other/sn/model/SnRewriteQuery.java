package cn.yunovo.iov.factory.biz.other.sn.model;

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
public class SnRewriteQuery {
	
	
	/**
	 * 之前SN
	 */
	@ApiModelProperty(value = "之前SN", name="sn")
	private String sn;
	
	/**
	 * 最新的sn
	 */
	@ApiModelProperty(value = "最新的sn", name="latestSn")
	private String latestSn;
	
	/**
	 * ICCID
	 */
	@ApiModelProperty(value = "ICCID", name="iccid")
	private String iccid;
	
	/**
	 * createTime
	 */
	@ApiModelProperty(value = "createTime", name="createTime")
	private String createTime;
	
	/**
	 * 查询时间
	 */
	@ApiModelProperty(value = "查询时间", name="dateTime")
	private String dateTime;
}
