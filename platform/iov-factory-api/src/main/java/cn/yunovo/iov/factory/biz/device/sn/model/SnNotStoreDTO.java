package cn.yunovo.iov.factory.biz.device.sn.model;

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
public class SnNotStoreDTO {
	
	/**
	 * 主键ID
	 */
	private Integer id;
	
	/**
	 * 设备SN
	 */
	private String sn;
	
	/**
	 * 创建时间
	 */
	private Date createTime;
}
