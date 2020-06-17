package cn.yunovo.iov.factory.biz.statistics.sumtotal.model;

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
public class StatisticsSumTotalDTO {
	
	/**
	 * 主键ID
	 */
	private Integer id;
	
	/**
	 * 统计类型，1贴片统计，2组装统计，3发货统计
	 */
	private Integer statisticsType;
	
	/**
	 * 设备数量
	 */
	private Integer todayNumber;
	
	/**
	 * 累计总数量
	 */
	private Integer sumTotal;
	
	/**
	 * 创建时间
	 */
	private Date createTime;
	
	/**
	 * 更新时间
	 */
	private Date updateTime;
}
