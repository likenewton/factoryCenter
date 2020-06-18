package cn.yunovo.iov.factory.biz.statistics.model;

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
public class StatisticsTypeDTO {
	
	/**
	 * 主键ID
	 */
	private Integer id;
	
	/**
	 * 统计类型,1贴片统计，2组装统计
	 */
	private Integer statisticsType;
	
	/**
	 * 工厂代码
	 */
	private String factoryName;
	
	/**
	 * 机构代码
	 */
	private String orgCode;
	
	/**
	 * 创建时间
	 */
	private Date createTime;
}
