package cn.yunovo.iov.factory.biz.dac.resource.model;

import java.util.Date;

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
public class DataResourceDTO {
	
	/**
	 * 主键ID
	 */
	private Integer id;
	
	/**
	 * 数据表中的主键
	 */
	private Integer dataId;
	
	/**
	 * 数据提供的表,值为主表
	 */
	private String dataProvider;
	
	/**
	 * 数据创建者ID
	 */
	private String creatorId;
	
	/**
	 * 来源创建者ID
	 */
	private String sourceCreatorId;
	
	/**
	 * 创建时间
	 */
	private Date createTime;
	
	/**
	 * 更新时间
	 */
	private Date updateTime;
}
