package cn.yunovo.iov.factory.biz.statistics.model;

import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;
import org.apache.ibatis.type.JdbcType;

import lombok.Data;
import lombok.ToString;
import tk.mybatis.mapper.annotation.ColumnType;

/**
 *  实体
 */
@Data
@ToString
@Table(name = "t_statistics_type")
public class StatisticsTypeProtoType{
	
	
	/**
	 * 主键ID
	 */
	@Id
	private Integer id;
	
	/**
	 * 统计类型,1贴片统计，2组装统计
	 */
	@ColumnType(jdbcType=JdbcType.TINYINT)
	private Integer statisticsType;
	
	/**
	 * 工厂代码
	 */
	@ColumnType(jdbcType=JdbcType.VARCHAR)
	private String factoryName;
	
	/**
	 * 机构代码
	 */
	@ColumnType(jdbcType=JdbcType.VARCHAR)
	private String orgCode;
	
	/**
	 * 创建时间
	 */
	@ColumnType(jdbcType=JdbcType.TIMESTAMP)
	private Date createTime;
}



