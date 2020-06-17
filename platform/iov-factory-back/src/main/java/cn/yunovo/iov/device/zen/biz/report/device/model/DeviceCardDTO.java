package cn.yunovo.iov.device.zen.biz.report.device.model;

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
public class DeviceCardDTO {
	
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
