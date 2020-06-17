package cn.yunovo.iov.device.zen.biz.other.log.model;

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
public class DeviceDTO {
	
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
