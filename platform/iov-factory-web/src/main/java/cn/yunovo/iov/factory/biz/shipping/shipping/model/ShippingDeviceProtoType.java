package cn.yunovo.iov.factory.biz.shipping.shipping.model;

import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;
import org.apache.ibatis.type.JdbcType;

import lombok.Data;
import lombok.ToString;
import tk.mybatis.mapper.annotation.ColumnType;

/**
 * 发货设备IMEI清单 实体
 */
@Data
@ToString
@Table(name = "t_shipping_device")
public class ShippingDeviceProtoType{
	
	
	/**
	 * 主键ID
	 */
	@Id
	private Integer id;
	
	/**
	 * 设备IMEI
	 */
	@ColumnType(jdbcType=JdbcType.VARCHAR)
	private String imei;
	
	/**
	 * 发货ID
	 */
	@ColumnType(jdbcType=JdbcType.INTEGER)
	private Integer shippingId;
	
	/**
	 * 创建时间
	 */
	@ColumnType(jdbcType=JdbcType.TIMESTAMP)
	private Date createTime;
}



