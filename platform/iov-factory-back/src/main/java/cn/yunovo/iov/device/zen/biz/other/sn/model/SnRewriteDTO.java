package cn.yunovo.iov.device.zen.biz.other.sn.model;

import java.util.Date;

import lombok.Data;
import lombok.ToString;

/**
 * 数据传输对象：SampleDTO，Sample为业务领域相关的名称<br/>
 * 
 * @author huangzz
 *
 */
@Data
@ToString
public class SnRewriteDTO {
	
	/**
	 * ID
	 */
	private Integer id;
	
	/**
	 * 之前SN
	 */
	private String sn;
	
	/**
	 * 最新的sn
	 */
	private String latestSn;
	
	/**
	 * ICCID
	 */
	private String iccid;
	
	/**
	 * createTime
	 */
	private Date createTime;
}
