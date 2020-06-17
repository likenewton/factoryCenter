package cn.yunovo.iov.device.zen.biz.production.test.model;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import cn.yunovo.iov.device.zen.framework.excel.ExcelResources;

import javax.validation.constraints.Digits;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * 展示对象：SampleVO，Sample一般为网页名称<br/>
 * 
 * @author huangzz
 *
 */
@Data
@ToString
@ApiModel(value="工厂设备测试", description="工厂设备测试查询对象")
public class DeviceTestVO {
	
	
	/**
	 * 主键ID
	 */
	@ApiModelProperty(value = "主键ID", name="id", hidden = true)
	private Integer id;
	
	/**
	 * 设备sn
	 */
	@ApiModelProperty(value = "设备sn", name="sn")
	@NotBlank(message="[sn]为必填项")
	@Size(min = 0, max = 255, message = "[sn]长度在[0,255]之间")
	@ExcelResources(title="sn",order=1,width=8000)
	private String sn;
	
	/**
	 * imei
	 */
	@ApiModelProperty(value = "imei", name="imei")
	@NotBlank(message="[imei]为必填项")
	@Size(min = 0, max = 255, message = "[imei]长度在[0,255]之间")
	@ExcelResources(title="imei",order=2,width=8000)
	private String imei;
	
	/**
	 * 卡ICCID
	 */
	@ApiModelProperty(value = "卡ICCID", name="iccid")
	@Size(min = 0, max = 32, message = "[iccid]长度在[0,32]之间")
	@ExcelResources(title="iccid",order=3,width=8000)
	private String iccid;
	
	/**
	 * 设备类型：0后视频镜 1为大屏机 2行车记录仪
	 */
	@ApiModelProperty(value = "设备类型：0后视频镜 1为大屏机 2行车记录仪", name="dtype")
	@NotBlank(message="[dtype]为必填项")
	@ExcelResources(title="设备类型：0后视频镜 1为大屏机 2行车记录仪",order=4,width=8000)
	@Size(min = 0, max = 128, message = "[dtype]长度在[0,128]之间")
	private String dtype;
	
	/**
	 * 设备ROM版本
	 */
	@ApiModelProperty(value = "设备ROM版本", name="romVersion")
	@NotBlank(message="[romVersion]为必填项")
	@Size(min = 0, max = 128, message = "[romVersion]长度在[0,128]之间")
	@ExcelResources(title="设备ROM版本",order=8,width=8000)
	private String romVersion;
	
	/**
	 * 设备MCU版本
	 */
	@ApiModelProperty(value = "设备MCU版本", name="mcuVersion")
	@NotBlank(message="[mcuVersion]为必填项")
	@Size(min = 0, max = 128, message = "[mcuVersion]长度在[0,128]之间")
	@ExcelResources(title="设备MCU版本",order=10,width=8000)
	private String mcuVersion;
	
	/**
	 * APK版本,APK版本号. 此项为验证apk升级是否成功,若不配置,则不检查.1.0.5为APK版本号
	 */
	@ApiModelProperty(value = "APK版本,APK版本号. 此项为验证apk升级是否成功,若不配置,则不检查.1.0.5为APK版本号", name="apkVersion")
	@NotBlank(message="[apkVersion]为必填项")
	@Size(min = 0, max = 128, message = "[apkVersion]长度在[0,128]之间")
	@ExcelResources(title="APK版本",order=9,width=8000)
	private String apkVersion;
	
	/**
	 * 为了区分不同工厂,目前组装厂有两个,安畅星和博毅,编号分别为ACX,BY
	 */
	@ApiModelProperty(value = "为了区分不同工厂,目前组装厂有两个,安畅星和博毅,编号分别为ACX,BY", name="factoryName")
	@Size(min = 0, max = 128, message = "[factoryName]长度在[0,128]之间")
	//@ExcelResources(title="工厂",order=8,width=8000)
	private String factoryName;
	
	/**
	 * 生产阶段，1贴片 2组装
	 */
	@ApiModelProperty(value = "生产阶段，1贴片 2组装", name="productionPhase")
	@Digits(fraction = 0, integer = Integer.MAX_VALUE)
	//@ExcelResources(title="生产阶段1贴片 2组装",order=9,width=8000)
	private Integer productionPhase;
	
	/**
	 * 坐标系，bd09ll 百度（默认）gcj02高德
	 */
	@ApiModelProperty(value = "坐标系，bd09ll 百度（默认）gcj02高德", name="gpsCoortype")
	@Size(min = 0, max = 32, message = "[gpsCoortype]长度在[0,32]之间")
	//@ExcelResources(title="坐标系bd09ll 百度（默认）gcj02高德",order=10,width=8000)
	private String gpsCoortype;
	
	/**
	 * 坐标（经度,维度）
	 */
	@ApiModelProperty(value = "坐标（经度,维度）", name="gpsPoint")
	@Size(min = 0, max = 64, message = "[gpsPoint]长度在[0,64]之间")
	//@ExcelResources(title="坐标（经度,维度）",order=11,width=8000)
	private String gpsPoint;
	
	/**
	 * 机构代码
	 */
	@ApiModelProperty(value = "机构代码", name="orgCode")
	@NotBlank(message="[orgCode]为必填项")
	@Size(min = 0, max = 64, message = "[orgCode]长度在[0,64]之间")
	@ExcelResources(title="机构代码",order=5,width=8000)
	private String orgCode;
	
	/**
	 * 位置信息
	 */
	@ApiModelProperty(value = "位置信息", name="location")
	@Size(min = 0, max = 255, message = "[location]长度在[0,255]之间")
	@ExcelResources(title="位置信息",order=11,width=8000)
	private String location;
	
	/**
	 * 项目名称
	 */
	@ApiModelProperty(value = "项目名称", name="prjName")
	@Size(min = 0, max = 255, message = "[prjName]长度在[0,255]之间")
	//@ExcelResources(title="项目名称",order=14,width=8000)
	private String prjName;
	
	/**
	 * 客户产品型号
	 */
	@ApiModelProperty(value = "客户产品型号", name="proName")
	@Size(min = 0, max = 255, message = "[proName]长度在[0,255]之间")
	//@ExcelResources(title="客户产品型号",order=15,width=8000)
	private String proName;
	
	/**
	 * 测试项成功数量
	 */
	@ApiModelProperty(value = "测试项成功数量", name="succNumber")
	@ExcelResources(title="测试项成功数量",order=6,width=8000)
	private Integer succNumber;
	
	/**
	 * 测试项错误数量
	 */
	@ApiModelProperty(value = "测试项错误数量", name="errorNumber")
	@ExcelResources(title="测试项错误数量",order=7,width=8000)
	private Integer errorNumber;
	
	/**
	 * 创建时间
	 */
	@ApiModelProperty(value = "创建时间", name="createTime", hidden = true)
	@ExcelResources(title="创建时间",order=12,width=8000)
	private String createTime;
	
	/**
	 * 更新时间
	 */
	@ApiModelProperty(value = "更新时间", name="updateTime", hidden = true)
	private String updateTime;
	
}
