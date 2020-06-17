package cn.yunovo.iov.device.zen.biz.statistics.repetition.model;

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
@Table(name = "t_statistics_repetitionreport")
public class RepetitionReportProtoType{
	
	
	/**
	 * 主键ID
	 */
	@Id
	private Integer id;
	
	/**
	 * 字段名称
	 */
	@ColumnType(jdbcType=JdbcType.VARCHAR)
	private String fieldName;
	
	/**
	 * 字段值
	 */
	@ColumnType(jdbcType=JdbcType.VARCHAR)
	private String fieldValue;
	
	/**
	 * 上报次数
	 */
	@ColumnType(jdbcType=JdbcType.INTEGER)
	private Integer reportTimes;
	
	/**
	 * 最后上报时间
	 */
	@ColumnType(jdbcType=JdbcType.TIMESTAMP)
	private Date lastDatetime;
	
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



