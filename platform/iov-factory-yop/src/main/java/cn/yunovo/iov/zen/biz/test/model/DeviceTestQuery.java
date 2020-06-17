package cn.yunovo.iov.zen.biz.test.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * Query：数据查询对象，各层接收上层的查询请求。注意超过2个参数的查询封装，禁止使用Map类来传输。<br/>
 * 
 * 附加说明：可以继承DTO
 * 
 * @author huangzz
 *
 */
@Data
@ToString
@ApiModel(value="工厂设备测试", description="工厂设备测试查询对象")
public class DeviceTestQuery {
	
	
	/**
	 * 设备sn
	 */
	@ApiModelProperty(value = "设备sn", name="sn")
	private String sn;
	
	/**
	 * imei
	 */
	@ApiModelProperty(value = "imei", name="imei")
	private String imei;
	
	/**
	 * 卡ICCID
	 */
	@ApiModelProperty(value = "卡ICCID", name="iccid")
	private String iccid;
	
	/**
	 * 设备类型：0后视频镜 1为大屏机 2行车记录仪
	 */
	@ApiModelProperty(value = "设备类型：0后视频镜 1为大屏机 2行车记录仪", name="dtype")
	private String dtype;
	
	/**
	 * 设备ROM版本
	 */
	@ApiModelProperty(value = "设备ROM版本", name="romVersion")
	private String romVersion;
	
	/**
	 * 设备MCU版本
	 */
	@ApiModelProperty(value = "设备MCU版本", name="mcuVersion")
	private String mcuVersion;
	
	/**
	 * APK版本,APK版本号. 此项为验证apk升级是否成功,若不配置,则不检查.1.0.5为APK版本号
	 */
	@ApiModelProperty(value = "APK版本,APK版本号. 此项为验证apk升级是否成功,若不配置,则不检查.1.0.5为APK版本号", name="apkVersion")
	private String apkVersion;
	
	/**
	 * 为了区分不同工厂,目前组装厂有两个,安畅星和博毅,编号分别为ACX,BY
	 */
	@ApiModelProperty(value = "为了区分不同工厂,目前组装厂有两个,安畅星和博毅,编号分别为ACX,BY", name="factoryName")
	private String factoryName;
	
	/**
	 * 生产阶段，1贴片 2组装
	 */
	@ApiModelProperty(value = "生产阶段，1贴片 2组装", name="productionPhase")
	private Integer productionPhase;
	
	/**
	 * 坐标系，bd09ll 百度（默认）gcj02高德
	 */
	@ApiModelProperty(value = "坐标系，bd09ll 百度（默认）gcj02高德", name="gpsCoortype")
	private String gpsCoortype;
	
	/**
	 * 坐标（经度,维度）
	 */
	@ApiModelProperty(value = "坐标（经度,维度）", name="gpsPoint")
	private String gpsPoint;
	
	/**
	 * 机构代码
	 */
	@ApiModelProperty(value = "机构代码", name="orgCode")
	private String orgCode;
	
	/**
	 * 位置信息
	 */
	@ApiModelProperty(value = "位置信息", name="location")
	private String location;
	
	

	/**
	 * 项目名称
	 */
	@ApiModelProperty(value = "项目名称", name="prjName")
	private String prjName;
	
	/**
	 * 客户产品型号
	 */
	@ApiModelProperty(value = "客户产品型号", name="proName")
	private String proName;
	
	/**
	 * 测试项成功数量
	 */
	@ApiModelProperty(value = "测试项成功数量", name="succNumber")
	private Integer succNumber;
	
	/**
	 * 测试项错误数量
	 */
	@ApiModelProperty(value = "测试项错误数量", name="errorNumber")
	private Integer errorNumber;
	
	/**
	 * 创建时间
	 */
	@ApiModelProperty(value = "创建时间", name="createTime")
	private String createTime;
	
	/**
	 * 更新时间
	 */
	@ApiModelProperty(value = "更新时间", name="updateTime")
	private String updateTime;
	
	/**
	 * 数据按照创建时间查询-开始时间
	 */
	@ApiModelProperty(value = "数据按照创建时间查询-开始时间", name="selStartTime")
	private String selStartTime;
	
	/**
	 * 数据按照创建时间查询-结束时间
	 */
	@ApiModelProperty(value = "数据按照创建时间查询-结束时间", name="selEndTime")
	private String selEndTime;
}
