package cn.yunovo.iov.device.zen.biz.other.log.model;

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
public class DeviceDO {
	
	/**
	 * id
	 */
	private Integer id;
	
	/**
	 * iccid
	 */
	private String iccid;
	
	/**
	 * 0:修改SN,1:更新组织机构,2:修改ICCID
	 */
	private Integer optType;
	
	/**
	 * 描述
	 */
	private String optDesc;
	
	/**
	 * 上报者
	 */
	private String reporter;
	
	/**
	 * 创建时间
	 */
	private Date addDatetime;
}
