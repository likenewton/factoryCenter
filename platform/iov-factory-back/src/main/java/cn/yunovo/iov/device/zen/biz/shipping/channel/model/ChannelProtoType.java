package cn.yunovo.iov.device.zen.biz.shipping.channel.model;

import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;
import org.apache.ibatis.type.JdbcType;

import lombok.Data;
import lombok.ToString;
import tk.mybatis.mapper.annotation.ColumnType;

/**
 * 渠道管理 实体
 */
@Data
@ToString
@Table(name = "t_channel_info")
public class ChannelProtoType{
	
	
	/**
	 * 主键ID
	 */
	@Id
	private Integer id;
	
	/**
	 * 父ID
	 */
	@ColumnType(jdbcType=JdbcType.INTEGER)
	private Integer parentId;
	
	/**
	 * 渠道名称
	 */
	@ColumnType(jdbcType=JdbcType.VARCHAR)
	private String channelName;
	
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
	 * 区域ID串，逗号隔开
	 */
	@ColumnType(jdbcType=JdbcType.VARCHAR)
	private String areaIds;
	
	/**
	 * 父ID串，逗号隔开
	 */
	@ColumnType(jdbcType=JdbcType.VARCHAR)
	private String paths;
	
	/**
	 * 级别
	 */
	@ColumnType(jdbcType=JdbcType.TINYINT)
	private Integer level;
	
	/**
	 * 联系人
	 */
	@ColumnType(jdbcType=JdbcType.VARCHAR)
	private String contacts;
	
	/**
	 * 手机号
	 */
	@ColumnType(jdbcType=JdbcType.VARCHAR)
	private String phone;
	
	/**
	 * 渠道详细地址
	 */
	@ColumnType(jdbcType=JdbcType.VARCHAR)
	private String address;
	
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



