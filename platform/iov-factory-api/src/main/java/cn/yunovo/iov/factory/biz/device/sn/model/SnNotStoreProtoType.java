package cn.yunovo.iov.factory.biz.device.sn.model;

import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;
import org.apache.ibatis.type.JdbcType;

import lombok.Data;
import lombok.ToString;
import tk.mybatis.mapper.annotation.ColumnType;

/**
 * 测试上报数据时，SN未入库记录 实体
 */
@Data
@ToString
@Table(name = "t_sn_notstore")
public class SnNotStoreProtoType{
	
	
	/**
	 * 主键ID
	 */
	@Id
	private Integer id;
	
	/**
	 * 设备SN
	 */
	@ColumnType(jdbcType=JdbcType.VARCHAR)
	private String sn;
	
	/**
	 * 创建时间
	 */
	@ColumnType(jdbcType=JdbcType.TIMESTAMP)
	private Date createTime;
}



