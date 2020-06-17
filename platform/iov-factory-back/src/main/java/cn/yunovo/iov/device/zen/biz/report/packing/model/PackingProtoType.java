package cn.yunovo.iov.device.zen.biz.report.packing.model;

import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;
import org.apache.ibatis.type.JdbcType;

import lombok.Data;
import lombok.ToString;
import tk.mybatis.mapper.annotation.ColumnType;

/**
 * 设备生产阶段，上报组装信息到云端 实体
 */
@Data
@ToString
@Table(name = "t_packing_report")
public class PackingProtoType{
	
	
	/**
	 * 主键
	 */
	@Id
	private Integer id;
	
	/**
	 * 设备IMEI
	 */
	@ColumnType(jdbcType=JdbcType.VARCHAR)
	private String imei;
	
	/**
	 * 软件代码
	 */
	@ColumnType(jdbcType=JdbcType.VARCHAR)
	private String swCode;
	
	/**
	 * 16位字符，标识硬件信息，用于生产追踪
	 */
	@ColumnType(jdbcType=JdbcType.VARCHAR)
	private String yunovoCode;
	
	/**
	 * 上报者
	 */
	@ColumnType(jdbcType=JdbcType.VARCHAR)
	private String reporter;
	
	/**
	 * 创建时间
	 */
	@ColumnType(jdbcType=JdbcType.TIMESTAMP)
	private Date createDatetime;
}



