package cn.yunovo.iov.device.zen.biz.report.device.model;

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
public class DeviceCardDO {
	
	/**
	 * 设备与卡的关系
	 */
	private Integer id;
	
	/**
	 * ICCID
	 */
	private String iccid;
	
	/**
	 * IMEI
	 */
	private String imei;
	
	/**
	 * 设备SN
	 */
	private String sn;
	
	/**
	 * 创建时间
	 */
	private Date addDatetime;
	
	/**
	 * 更新时间
	 */
	private Date updateDatetime;
}
