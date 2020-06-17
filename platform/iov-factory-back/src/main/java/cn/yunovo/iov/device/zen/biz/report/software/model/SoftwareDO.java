package cn.yunovo.iov.device.zen.biz.report.software.model;

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
public class SoftwareDO {
	
	/**
	 * 主键
	 */
	private Integer id;
	
	/**
	 * 软件代码
	 */
	private String swCode;
	
	/**
	 * ROM 版本号
	 */
	private String romVersion;
	
	/**
	 * 设备项目型号：D10
	 */
	private String projectModel;
	
	/**
	 * 设备ROM包机构编码：OG-00171
	 */
	private String orgCode;
	
	/**
	 * 上报者
	 */
	private String reporter;
	
	/**
	 * 新增时间
	 */
	private Date addDatetime;
	
	/**
	 * 更新时间
	 */
	private Date updateDatetime;
}
