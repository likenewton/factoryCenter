package cn.yunovo.iov.factory.biz.statistics.shipping.model;

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
public class StatisticsShippingDO {
	
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
	 * 渠道区域
	 */
	private String area;
	
	/**
	 * 工厂名称
	 */
	private String factoryName;
	
	/**
	 * 设备数据
	 */
	private Integer deviceNumber;
	
	/**
	 * 最后导入时间
	 */
	private Date lastImporttime;
	
	/**
	 * 创建时间
	 */
	private Date createTime;
	
	/**
	 * 修改时间
	 */
	private Date updateTime;
}
