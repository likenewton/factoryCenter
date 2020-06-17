package cn.yunovo.iov.device.zen.biz.statistics.type.model;

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
	 * 渠道ID
	 */
	private Integer channelId;
	
	/**
	 * 工厂代码
	 */
	private String factoryName;
	
	/**
	 * 机构代码
	 */
	private String orgCode;
	
	/**
	 * 统计类型,1贴片统计，2组装统计，3发货统计
	 */
	private Integer statisticsType;
	
	/**
	 * 创建者ID
	 */
	private String createId;
	
	/**
	 * 创建时间
	 */
	private Date createTime;
}
