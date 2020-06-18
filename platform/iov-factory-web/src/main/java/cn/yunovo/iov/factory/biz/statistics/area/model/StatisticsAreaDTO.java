package cn.yunovo.iov.factory.biz.statistics.area.model;

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
public class StatisticsAreaDTO {
	
	/**
	 * 主键ID
	 */
	private Integer id;
	
	/**
	 * 设备发货数量
	 */
	private Integer deviceNumber;
	
	/**
	 * 渠道ID
	 */
	private Integer channelId;
	
	/**
	 * 工厂名称
	 */
	private String factoryName;
	
	/**
	 * 品牌名称(机构代码orgCode)
	 */
	private String brandName;
	
	/**
	 * 区域
	 */
	private String area;
	
	/**
	 * 创建时间
	 */
	private Date createTime;
	
	/**
	 * 修改时间
	 */
	private Date updateTime;
}
