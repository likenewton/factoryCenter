package cn.yunovo.iov.device.zen.biz.statistics.paster.model;

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
public class StatisticsPasterDO {
	
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
