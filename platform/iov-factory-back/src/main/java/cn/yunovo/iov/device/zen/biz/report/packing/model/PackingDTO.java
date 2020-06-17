package cn.yunovo.iov.device.zen.biz.report.packing.model;

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
public class PackingDTO {
	
	/**
	 * 主键
	 */
	private Integer id;
	
	/**
	 * 设备IMEI
	 */
	private String imei;
	
	/**
	 * 软件代码
	 */
	private String swCode;
	
	/**
	 * 16位字符，标识硬件信息，用于生产追踪
	 */
	private String yunovoCode;
	
	/**
	 * 上报者
	 */
	private String reporter;
	
	/**
	 * 创建时间
	 */
	private Date createDatetime;
}
