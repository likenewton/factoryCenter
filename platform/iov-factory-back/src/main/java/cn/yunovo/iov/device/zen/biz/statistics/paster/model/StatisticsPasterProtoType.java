package cn.yunovo.iov.device.zen.biz.statistics.paster.model;

import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;
import org.apache.ibatis.type.JdbcType;

import lombok.Data;
import lombok.ToString;
import tk.mybatis.mapper.annotation.ColumnType;

/**
 * 统计贴片上报 实体
 */
@Data
@ToString
@Table(name = "t_statistics_paster")
public class StatisticsPasterProtoType{
	
	
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
	 * 错误数量
	 */
	@ColumnType(jdbcType=JdbcType.INTEGER)
	private Integer errorNumber;
	
	/**
	 * 贴片数量
	 */
	@ColumnType(jdbcType=JdbcType.INTEGER)
	private Integer pasterNumber;
	
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
	 * 修改时间
	 */
	@ColumnType(jdbcType=JdbcType.TIMESTAMP)
	private Date updateTime;
}



