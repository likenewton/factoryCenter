package cn.yunovo.iov.factory.biz.report.packing.model;

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
public class PackingDO {
	
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
