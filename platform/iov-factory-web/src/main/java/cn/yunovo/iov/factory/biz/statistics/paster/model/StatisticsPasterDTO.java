package cn.yunovo.iov.factory.biz.statistics.paster.model;

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
public class StatisticsPasterDTO {
	
	/**
	 * 主键ID
	 */
	private Integer id;
	
	/**
	 * 品牌名称(机构代码orgCode)
	 */
	private String brandName;
	
	/**
	 * 工厂名称
	 */
	private String factoryName;
	
	/**
	 * 错误数量
	 */
	private Integer errorNumber;
	
	/**
	 * 贴片数量
	 */
	private Integer pasterNumber;
	
	/**
	 * 上报时间
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
