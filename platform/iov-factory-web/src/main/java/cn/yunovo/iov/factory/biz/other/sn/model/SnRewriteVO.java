package cn.yunovo.iov.factory.biz.other.sn.model;


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
@Data
@ToString
@ApiModel(value="", description="查询对象")
public class SnRewriteVO {
	
	
	/**
	 * ID
	 */
	@ApiModelProperty(value = "ID", name="id", hidden = true)
	private Integer id;
	
	/**
	 * 之前SN
	 */
	@ApiModelProperty(value = "之前SN", name="sn")
	@Size(min = 1, max = 64, message = "[sn]长度在[1,64]之间")
	private String sn;
	
	/**
	 * 最新的sn
	 */
	@ApiModelProperty(value = "最新的sn", name="latestSn")
	@Size(min = 1, max = 64, message = "[latestSn]长度在[1,64]之间")
	private String latestSn;
	
	/**
	 * ICCID
	 */
	@ApiModelProperty(value = "ICCID", name="iccid")
	@Size(min = 1, max = 32, message = "[iccid]长度在[1,32]之间")
	private String iccid;
	
	/**
	 * createTime
	 */
	@ApiModelProperty(value = "createTime", name="createTime", hidden = true)
	private String createTime;
	
}
