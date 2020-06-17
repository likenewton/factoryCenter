package cn.yunovo.iov.factory.biz.shipping.shipping.model;

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
public class ShippingDeviceDO {
	
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
