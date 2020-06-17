package cn.yunovo.iov.zen.biz.statistics.model;

import java.util.Date;
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
public class StatisticsTypeDO {
	
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
