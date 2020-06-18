package cn.yunovo.iov.factory.biz.statistics.area.model;

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
public class StatisticsAreaDO {
	
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
