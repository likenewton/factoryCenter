package cn.yunovo.iov.device.zen.biz.statistics.shipping.model;

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
@Table(name = "t_statistics_shippinglist")
public class StatisticsShippingListProtoType{
	
	
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
	 * 设备数据
	 */
	@ColumnType(jdbcType=JdbcType.INTEGER)
	private Integer deviceNumber;
	
	/**
	 * 导入时间
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



