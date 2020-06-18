package cn.yunovo.iov.factory.biz.software.model;

import java.util.Date;

import lombok.Data;

@Data
public class SoftwareInfoDO {

	/**
	 * 软件代号
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
