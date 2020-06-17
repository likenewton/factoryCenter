package cn.yunovo.iov.device.zen.biz.other.data.model;


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
@Table(name = "t_data_dictionary")
public class DictionaryProtoType{
	
	
	/**
	 * 主键ID
	 */
	@Id
	private Integer id;
	
	/**
	 * 词KEY
	 */
	@ColumnType(jdbcType=JdbcType.VARCHAR)
	private String wordKey;
	
	/**
	 * 词值
	 */
	@ColumnType(jdbcType=JdbcType.VARCHAR)
	private String wordValue;
	
	/**
	 * 词类型
	 */
	@ColumnType(jdbcType=JdbcType.TINYINT)
	private Integer wordType;
}



