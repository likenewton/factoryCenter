package cn.yunovo.iov.factory.biz.statistics.shipping.model;

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
public class StatisticsShippingListDTO {
	
	/**
	 * 主键ID
	 */
	private Integer id;
	
	/**
	 * 渠道ID
	 */
	private Integer channelId;
	
	/**
	 * 品牌名称(机构代码orgCode)
	 */
	private String brandName;
	
	/**
	 * 工厂名称
	 */
	private String factoryName;
	
	/**
	 * 设备数据
	 */
	private Integer deviceNumber;
	
	/**
	 * 导入时间
	 */
	private Date reportTime;
	
	/**
	 * 创建时间
	 */
	private Date createTime;
	
	/**
	 * 修改时间
	 */
	private Date updateTime;
}
