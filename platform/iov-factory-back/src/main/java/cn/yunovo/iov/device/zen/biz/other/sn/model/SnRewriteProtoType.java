package cn.yunovo.iov.device.zen.biz.other.sn.model;

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
@Table(name = "t_sn_rewrite")
public class SnRewriteProtoType{
	
	
	/**
	 * ID
	 */
	@Id
	private Integer id;
	
	/**
	 * 之前SN
	 */
	@ColumnType(jdbcType=JdbcType.VARCHAR)
	private String sn;
	
	/**
	 * 最新的sn
	 */
	@ColumnType(jdbcType=JdbcType.VARCHAR)
	private String latestSn;
	
	/**
	 * ICCID
	 */
	@ColumnType(jdbcType=JdbcType.VARCHAR)
	private String iccid;
	
	/**
	 * createTime
	 */
	@ColumnType(jdbcType=JdbcType.TIMESTAMP)
	private Date createTime;
}



