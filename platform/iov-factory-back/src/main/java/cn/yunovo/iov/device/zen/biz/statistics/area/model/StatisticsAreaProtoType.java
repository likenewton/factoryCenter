package cn.yunovo.iov.device.zen.biz.statistics.area.model;

import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;
import org.apache.ibatis.type.JdbcType;

import lombok.Data;
import lombok.ToString;
import tk.mybatis.mapper.annotation.ColumnType;

/**
 * 统计发货管理 实体
 */
@Data
@ToString
@Table(name = "t_statistics_area")
public class StatisticsAreaProtoType{
	
	
	/**
	 * 主键ID
	 */
	@Id
	private Integer id;
	
	/**
	 * 设备发货数量
	 */
	@ColumnType(jdbcType=JdbcType.INTEGER)
	private Integer deviceNumber;
	
	/**
	 * 渠道ID
	 */
	@ColumnType(jdbcType=JdbcType.INTEGER)
	private Integer channelId;
	
	/**
	 * 工厂名称
	 */
	@ColumnType(jdbcType=JdbcType.VARCHAR)
	private String factoryName;
	
	/**
	 * 品牌名称(机构代码orgCode)
	 */
	@ColumnType(jdbcType=JdbcType.VARCHAR)
	private String brandName;
	
	/**
	 * 区域
	 */
	@ColumnType(jdbcType=JdbcType.VARCHAR)
	private String area;
	
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



