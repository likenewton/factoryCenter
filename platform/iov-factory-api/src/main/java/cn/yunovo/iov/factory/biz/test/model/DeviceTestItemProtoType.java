package cn.yunovo.iov.factory.biz.test.model;


import javax.persistence.Id;
import javax.persistence.Table;
import org.apache.ibatis.type.JdbcType;

import lombok.Data;
import lombok.ToString;
import tk.mybatis.mapper.annotation.ColumnType;

/**
 * 设备测试项 实体
 */
@Data
@ToString
@Table(name = "t_device_testitem")
public class DeviceTestItemProtoType{
	
	
	/**
	 * 主键
	 */
	@Id
	private Integer id;
	
	/**
	 * 测试主键ID
	 */
	@ColumnType(jdbcType=JdbcType.INTEGER)
	private Integer testId;
	
	/**
	 * 测试项
	 */
	@ColumnType(jdbcType=JdbcType.VARCHAR)
	private String testItem;
	
	/**
	 * 测试值
	 */
	@ColumnType(jdbcType=JdbcType.VARCHAR)
	private String testValue;
	
	/**
	 * 测试结果
	 */
	@ColumnType(jdbcType=JdbcType.VARCHAR)
	private Integer testResult;
	
	/**
	 * 1自动测试项,2手动测试项
	 */
	@ColumnType(jdbcType=JdbcType.TINYINT)
	private Integer testMethod;
}



