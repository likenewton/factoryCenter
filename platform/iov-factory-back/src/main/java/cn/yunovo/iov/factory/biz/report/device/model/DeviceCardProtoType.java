package cn.yunovo.iov.factory.biz.report.device.model;

import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;
import org.apache.ibatis.type.JdbcType;

import lombok.Data;
import lombok.ToString;
import tk.mybatis.mapper.annotation.ColumnType;

/**
 * 三码关系表 实体
 */
@Data
@ToString
@Table(name = "t_device_card")
public class DeviceCardProtoType{
	
	
	/**
	 * 设备与卡的关系
	 */
	@Id
	private Integer id;
	
	/**
	 * ICCID
	 */
	@ColumnType(jdbcType=JdbcType.VARCHAR)
	private String iccid;
	
	/**
	 * IMEI
	 */
	@ColumnType(jdbcType=JdbcType.VARCHAR)
	private String imei;
	
	/**
	 * 设备SN
	 */
	@ColumnType(jdbcType=JdbcType.VARCHAR)
	private String sn;
	
	/**
	 * 创建时间
	 */
	@ColumnType(jdbcType=JdbcType.TIMESTAMP)
	private Date addDatetime;
	
	/**
	 * 更新时间
	 */
	@ColumnType(jdbcType=JdbcType.TIMESTAMP)
	private Date updateDatetime;
}



