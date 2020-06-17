package cn.yunovo.iov.factory.biz.statistics.assemble.model;

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
public class StatisticsAssembleDO {
	
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
	 * 设备数量
	 */
	private Integer deviceNumber;
	
	/**
	 * 错误数量
	 */
	private Integer errorNumber;
	
	/**
	 * 上报时间
	 */
	private Date reportTime;
	
	/**
	 * 创建时间
	 */
	private Date createTime;
	
	/**
	 * 更新时间
	 */
	private Date updateTime;
}
