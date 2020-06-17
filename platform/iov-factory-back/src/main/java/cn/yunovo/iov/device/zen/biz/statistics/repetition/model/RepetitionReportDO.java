package cn.yunovo.iov.device.zen.biz.statistics.repetition.model;

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
public class RepetitionReportDO {
	
	/**
	 * 主键ID
	 */
	private Integer id;
	
	/**
	 * 字段名称
	 */
	private String fieldName;
	
	/**
	 * 字段值
	 */
	private String fieldValue;
	
	/**
	 * 上报次数
	 */
	private Integer reportTimes;
	
	/**
	 * 最后上报时间
	 */
	private Date lastDatetime;
	
	/**
	 * 创建时间
	 */
	private Date createTime;
	
	/**
	 * 更新时间
	 */
	private Date updateTime;
}
