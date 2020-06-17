package cn.yunovo.iov.device.zen.biz.dac.user.model;


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
@Table(name = "t_dac_usermapper")
public class DacUserProtoType{
	
	
	/**
	 * 主键ID
	 */
	@Id
	private Integer id;
	
	/**
	 * 用户ID
	 */
	@ColumnType(jdbcType=JdbcType.VARCHAR)
	private String userId;
	
	/**
	 * 用户名称
	 */
	@ColumnType(jdbcType=JdbcType.VARCHAR)
	private String userMapper;
	
	/**
	 * 用户类型:1机构(品牌)，2工厂
	 */
	@ColumnType(jdbcType=JdbcType.TINYINT)
	private Integer userType;
}



