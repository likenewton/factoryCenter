package cn.yunovo.iov.device.zen.biz.statistics.type.model;

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
	 * 渠道ID
	 */
	@ColumnType(jdbcType=JdbcType.INTEGER)
	private Integer channelId;
	
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
	 * 统计类型,1贴片统计，2组装统计，3发货统计
	 */
	@ColumnType(jdbcType=JdbcType.TINYINT)
	private Integer statisticsType;
	
	/**
	 * 创建者ID
	 */
	@ColumnType(jdbcType=JdbcType.VARCHAR)
	private String createId;
	
	/**
	 * 创建时间
	 */
	@ColumnType(jdbcType=JdbcType.TIMESTAMP)
	private Date createTime;
}



