package cn.yunovo.iov.factory.biz.production.test.model;

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
public class DeviceTestDTO {
	
	/**
	 * 主键ID
	 */
	private Integer id;
	
	/**
	 * 设备sn
	 */
	private String sn;
	
	/**
	 * imei
	 */
	private String imei;
	
	/**
	 * 卡ICCID
	 */
	private String iccid;
	
	/**
	 * 设备类型：0后视频镜 1为大屏机 2行车记录仪
	 */
	private String dtype;
	
	/**
	 * 设备ROM版本
	 */
	private String romVersion;
	
	/**
	 * 设备MCU版本
	 */
	private String mcuVersion;
	
	/**
	 * APK版本,APK版本号. 此项为验证apk升级是否成功,若不配置,则不检查.1.0.5为APK版本号
	 */
	private String apkVersion;
	
	/**
	 * 为了区分不同工厂,目前组装厂有两个,安畅星和博毅,编号分别为ACX,BY
	 */
	private String factoryName;
	
	/**
	 * 生产阶段，1贴片 2组装
	 */
	private Integer productionPhase;
	
	/**
	 * 坐标系，bd09ll 百度（默认）gcj02高德
	 */
	private String gpsCoortype;
	
	/**
	 * 坐标（经度,维度）
	 */
	private String gpsPoint;
	
	/**
	 * 机构代码
	 */
	private String orgCode;
	
	/**
	 * 位置信息
	 */
	private String location;
	
	/**
	 * 项目名称
	 */
	private String prjName;
	
	/**
	 * 客户产品型号
	 */
	private String proName;
	
	/**
	 * 测试项成功数量
	 */
	private Integer succNumber;
	
	/**
	 * 测试项错误数量
	 */
	private Integer errorNumber;
	
	/**
	 * 创建时间
	 */
	private Date createTime;
	
	/**
	 * 更新时间
	 */
	private Date updateTime;
}
