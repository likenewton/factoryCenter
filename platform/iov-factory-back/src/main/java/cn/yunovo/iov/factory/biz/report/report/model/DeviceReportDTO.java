package cn.yunovo.iov.factory.biz.report.report.model;

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
public class DeviceReportDTO {
	
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
