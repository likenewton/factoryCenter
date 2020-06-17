package cn.yunovo.iov.zen.biz.packing.model;

import java.util.Date;

import lombok.Data;
import lombok.ToString;

/**
 * 与数据库表结构一一对应，通过DAO层向上传输数据源对象。<br/>
 * 
 * @author huangzz
 *
 */
@Data
@ToString
public class PackingReportDO {

	private String imei;
	private String swCode;
	private String yunovoCode;
	private String reporter;
	private Date createDatetime;
	
}
