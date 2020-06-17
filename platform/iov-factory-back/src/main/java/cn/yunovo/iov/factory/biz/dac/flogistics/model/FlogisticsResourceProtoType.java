package cn.yunovo.iov.factory.biz.dac.flogistics.model;

import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;
import org.apache.ibatis.type.JdbcType;

import lombok.Data;
import lombok.ToString;
import tk.mybatis.mapper.annotation.ColumnType;

/**
 * 物流用户数据权限：资源，主体，规则关系表 实体
 */
@Data
@ToString
@Table(name = "t_dac_flogistics")
public class FlogisticsResourceProtoType{
	
	
	/**
	 * 主键ID
	 */
	@Id
	private Integer id;
	
	/**
	 * 数据表中的主键
	 */
	@ColumnType(jdbcType=JdbcType.INTEGER)
	private Integer dataId;
	
	/**
	 * 数据提供的表,值为主表
	 */
	@ColumnType(jdbcType=JdbcType.VARCHAR)
	private String dataProvider;
	
	/**
	 * 数据创建者ID
	 */
	@ColumnType(jdbcType=JdbcType.VARCHAR)
	private String creatorId;
	
	/**
	 * 来源创建者ID
	 */
	@ColumnType(jdbcType=JdbcType.VARCHAR)
	private String sourceCreatorId;
	
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



