package cn.yunovo.iov.factory.biz.report.software.model;

import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;
import org.apache.ibatis.type.JdbcType;

import lombok.Data;
import lombok.ToString;
import tk.mybatis.mapper.annotation.ColumnType;

/**
 * zen 平台，上报设备信息到云端 实体
 */
@Data
@ToString
@Table(name = "t_software_report")
public class SoftwareProtoType{
	
	
	/**
	 * 主键
	 */
	@Id
	private Integer id;
	
	/**
	 * 软件代码
	 */
	@ColumnType(jdbcType=JdbcType.VARCHAR)
	private String swCode;
	
	/**
	 * ROM 版本号
	 */
	@ColumnType(jdbcType=JdbcType.VARCHAR)
	private String romVersion;
	
	/**
	 * 设备项目型号：D10
	 */
	@ColumnType(jdbcType=JdbcType.VARCHAR)
	private String projectModel;
	
	/**
	 * 设备ROM包机构编码：OG-00171
	 */
	@ColumnType(jdbcType=JdbcType.VARCHAR)
	private String orgCode;
	
	/**
	 * 上报者
	 */
	@ColumnType(jdbcType=JdbcType.VARCHAR)
	private String reporter;
	
	/**
	 * 新增时间
	 */
	@ColumnType(jdbcType=JdbcType.TIMESTAMP)
	private Date addDatetime;
	
	/**
	 * 更新时间
	 */
	@ColumnType(jdbcType=JdbcType.TIMESTAMP)
	private Date updateDatetime;
}



