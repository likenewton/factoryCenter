package cn.yunovo.iov.device.zen.biz.statistics.report.model;

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
public class ScanReportDO {
	
	/**
	 * 主键ID
	 */
	private Integer id;
	
	/**
	 * 统计时间
	 */
	private Date stateDatetime;
	
	/**
	 * 扫描上报次数
	 */
	private Integer reportTimes;
	
	/**
	 * 创建时间
	 */
	private Date createTime;
	
	/**
	 * 更新时间
	 */
	private Date updateTime;
}
