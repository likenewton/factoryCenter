package cn.yunovo.iov.device.zen.biz.statistics.sumtotal.model;

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
@Table(name = "t_statistics_sumtotal")
public class StatisticsSumTotalProtoType{
	
	
	/**
	 * 主键ID
	 */
	@Id
	private Integer id;
	
	/**
	 * 统计类型，1贴片统计，2组装统计，3发货统计
	 */
	@ColumnType(jdbcType=JdbcType.TINYINT)
	private Integer statisticsType;
	
	/**
	 * 设备数量
	 */
	@ColumnType(jdbcType=JdbcType.INTEGER)
	private Integer todayNumber;
	
	/**
	 * 累计总数量
	 */
	@ColumnType(jdbcType=JdbcType.INTEGER)
	private Integer sumTotal;
	
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



