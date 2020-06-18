package cn.yunovo.iov.factory.biz.device.model;

import java.util.Date;

import lombok.Data;
import lombok.ToString;

/**
 * 与数据库表结构一一对应，通过DAO层向上传输数据源对象。<br/>
 * 
 * @author huangzz
 *
 */
@Data
@ToString
public class DeviceReportDO {
	private String sn;
	private String imei;
	private String iccid;
	private String customSN;
	private String attached;
	private String reporter;
	private Date createDatetime;
	private String btAddr;
	private String wifiAddr;
}
