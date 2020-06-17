package cn.yunovo.iov.factory.biz.statistics.report.model;

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
@Table(name = "t_statistics_scanreport")
public class ScanReportProtoType{
	
	
	/**
	 * 主键ID
	 */
	@Id
	private Integer id;
	
	/**
	 * 统计时间
	 */
	@ColumnType(jdbcType=JdbcType.DATE)
	private Date stateDatetime;
	
	/**
	 * 扫描上报次数
	 */
	@ColumnType(jdbcType=JdbcType.INTEGER)
	private Integer reportTimes;
	
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



