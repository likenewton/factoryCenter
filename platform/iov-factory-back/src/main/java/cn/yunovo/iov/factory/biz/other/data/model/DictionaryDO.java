package cn.yunovo.iov.factory.biz.other.data.model;

import lombok.Data;
import lombok.ToString;

/**
 * 
 * 与数据库表结构一一对应，通过DAO层向上传输数据源对象。<br/>
 * 
 * @author huangzz
 *
 */
@Data
@ToString
public class DictionaryDO {
	
	/**
	 * 主键ID
	 */
	private Integer id;
	
	/**
	 * 词KEY
	 */
	private String wordKey;
	
	/**
	 * 词值
	 */
	private String wordValue;
	
	/**
	 * 词类型
	 */
	private Integer wordType;
}
