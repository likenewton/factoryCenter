package cn.yunovo.iov.device.zen.biz.other.sn.model;

import java.util.Date;
import lombok.Data;
import lombok.ToString;

/**
 * 
 * 与数据库表结构一一对应，通过DAO层向上传输数据源对象。<br/>
 * 
 * @author huangzz
 *
 */
@Data
@ToString
public class SnRewriteDO {
	
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
