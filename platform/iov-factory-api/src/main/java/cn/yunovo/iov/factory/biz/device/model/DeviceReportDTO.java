package cn.yunovo.iov.factory.biz.device.model;

import java.util.Date;

import lombok.Data;
import lombok.ToString;

/**
 * 数据传输对象：DeviceReportDTO，DeviceReport为业务领域相关的名称<br/>
 * 
 * @author huangzz
 *
 */
@Data
@ToString
public class DeviceReportDTO {
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
