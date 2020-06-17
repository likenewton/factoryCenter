package cn.yunovo.iov.device.zen.biz.report.report.model;

import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;
import org.apache.ibatis.type.JdbcType;

import lombok.Data;
import lombok.ToString;
import tk.mybatis.mapper.annotation.ColumnType;

/**
 * 设备生产阶段，上报设备信息到云端 实体
 */
@Data
@ToString
@Table(name = "t_device_report")
public class DeviceReportProtoType{
	
	
	/**
	 * 主键
	 */
	@Id
	private Integer id;
	
	/**
	 * 设备SN
	 */
	@ColumnType(jdbcType=JdbcType.VARCHAR)
	private String sn;
	
	/**
	 * 设备IMEI
	 */
	@ColumnType(jdbcType=JdbcType.VARCHAR)
	private String imei;
	
	/**
	 * 卡ICCID
	 */
	@ColumnType(jdbcType=JdbcType.VARCHAR)
	private String iccid;
	
	/**
	 * 0:云智SN,1:非云智SN
	 */
	@ColumnType(jdbcType=JdbcType.TINYINT)
	private Integer customSn;
	
	/**
	 * 额外信息
	 */
	@ColumnType(jdbcType=JdbcType.VARCHAR)
	private String attached;
	
	/**
	 * BT
	 */
	@ColumnType(jdbcType=JdbcType.VARCHAR)
	private String btAddr;
	
	/**
	 * WIFI地址
	 */
	@ColumnType(jdbcType=JdbcType.VARCHAR)
	private String wifiAddr;
	
	/**
	 * 上报者
	 */
	@ColumnType(jdbcType=JdbcType.VARCHAR)
	private String reporter;
	
	/**
	 * 新增时间
	 */
	@ColumnType(jdbcType=JdbcType.TIMESTAMP)
	private Date createDatetime;
}



