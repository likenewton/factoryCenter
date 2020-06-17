package cn.yunovo.iov.device.zen.biz.statistics.repetition.model;

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
public class RepetitionReportDTO {
	
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
