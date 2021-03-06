package cn.yunovo.iov.factory.biz.report.report.model;

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
public class DeviceReportDO {
	
	/**
	 * 主键
	 */
	private Integer id;
	
	/**
	 * 设备SN
	 */
	private String sn;
	
	/**
	 * 设备IMEI
	 */
	private String imei;
	
	/**
	 * 卡ICCID
	 */
	private String iccid;
	
	/**
	 * 0:云智SN,1:非云智SN
	 */
	private Integer customSn;
	
	/**
	 * 额外信息
	 */
	private String attached;
	
	/**
	 * BT
	 */
	private String btAddr;
	
	/**
	 * WIFI地址
	 */
	private String wifiAddr;
	
	/**
	 * 上报者
	 */
	private String reporter;
	
	/**
	 * 新增时间
	 */
	private Date createDatetime;
}
