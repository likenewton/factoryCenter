package cn.yunovo.iov.device.zen.biz.other.data.model;


import lombok.Data;
import lombok.ToString;

/**
 * 数据传输对象：SampleDTO，Sample为业务领域相关的名称<br/>
 * 
 * @author huangzz
 *
 */
@Data
@ToString
public class DictionaryDTO {
	
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
