package cn.yunovo.iov.factory.biz.shipping.shipping.model;

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
public class ShippingDeviceDTO {
	
	/**
	 * 主键ID
	 */
	private Integer id;
	
	/**
	 * 设备IMEI
	 */
	private String imei;
	
	/**
	 * 发货ID
	 */
	private Integer shippingId;
	
	/**
	 * 创建时间
	 */
	private Date createTime;
}
