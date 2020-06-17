package cn.yunovo.iov.factory.biz.statistics.assemble.model;

import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;
import org.apache.ibatis.type.JdbcType;

import lombok.Data;
import lombok.ToString;
import tk.mybatis.mapper.annotation.ColumnType;

/**
 * 统计组织上报 实体
 */
@Data
@ToString
@Table(name = "t_statistics_assemble")
public class StatisticsAssembleProtoType{
	
	
	/**
	 * 主键ID
	 */
	@Id
	private Integer id;
	
	/**
	 * 品牌名称(机构代码orgCode)
	 */
	@ColumnType(jdbcType=JdbcType.VARCHAR)
	private String brandName;
	
	/**
	 * 工厂名称
	 */
	@ColumnType(jdbcType=JdbcType.VARCHAR)
	private String factoryName;
	
	/**
	 * 设备数量
	 */
	@ColumnType(jdbcType=JdbcType.INTEGER)
	private Integer deviceNumber;
	
	/**
	 * 错误数量
	 */
	@ColumnType(jdbcType=JdbcType.INTEGER)
	private Integer errorNumber;
	
	/**
	 * 上报时间
	 */
	@ColumnType(jdbcType=JdbcType.DATE)
	private Date reportTime;
	
	/**
	 * 创建时间
	 */
	@ColumnType(jdbcType=JdbcType.TIMESTAMP)
	private Date createTime;
	
	/**
	 * 更新时间
	 */
	@ColumnType(jdbcType=JdbcType.TIMESTAMP)
	private Date updateTime;
}



