package cn.yunovo.iov.device.zen.biz.other.log.model;

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
@Table(name = "t_service_log")
public class DeviceProtoType{
	
	
	/**
	 * id
	 */
	@Id
	private Integer id;
	
	/**
	 * iccid
	 */
	@ColumnType(jdbcType=JdbcType.VARCHAR)
	private String iccid;
	
	/**
	 * 0:修改SN,1:更新组织机构,2:修改ICCID
	 */
	@ColumnType(jdbcType=JdbcType.TINYINT)
	private Integer optType;
	
	/**
	 * 描述
	 */
	@ColumnType(jdbcType=JdbcType.VARCHAR)
	private String optDesc;
	
	/**
	 * 上报者
	 */
	@ColumnType(jdbcType=JdbcType.VARCHAR)
	private String reporter;
	
	/**
	 * 创建时间
	 */
	@ColumnType(jdbcType=JdbcType.TIMESTAMP)
	private Date addDatetime;
}



