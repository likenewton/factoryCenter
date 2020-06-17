package cn.yunovo.iov.device.zen.biz.statistics.report.model;

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
public class ScanReportDTO {
	
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
