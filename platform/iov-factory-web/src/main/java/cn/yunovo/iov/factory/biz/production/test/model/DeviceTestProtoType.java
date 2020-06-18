package cn.yunovo.iov.factory.biz.production.test.model;

import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;
import org.apache.ibatis.type.JdbcType;

import lombok.Data;
import lombok.ToString;
import tk.mybatis.mapper.annotation.ColumnType;

/**
 * 工厂设备测试 实体
 */
@Data
@ToString
@Table(name = "t_device_test")
public class DeviceTestProtoType{
	
	
	/**
	 * 主键ID
	 */
	@Id
	private Integer id;
	
	/**
	 * 设备sn
	 */
	@ColumnType(jdbcType=JdbcType.VARCHAR)
	private String sn;
	
	/**
	 * imei
	 */
	@ColumnType(jdbcType=JdbcType.VARCHAR)
	private String imei;
	
	/**
	 * 卡ICCID
	 */
	@ColumnType(jdbcType=JdbcType.VARCHAR)
	private String iccid;
	
	/**
	 * 设备类型：0后视频镜 1为大屏机 2行车记录仪
	 */
	@ColumnType(jdbcType=JdbcType.VARCHAR)
	private String dtype;
	
	/**
	 * 设备ROM版本
	 */
	@ColumnType(jdbcType=JdbcType.VARCHAR)
	private String romVersion;
	
	/**
	 * 设备MCU版本
	 */
	@ColumnType(jdbcType=JdbcType.VARCHAR)
	private String mcuVersion;
	
	/**
	 * APK版本,APK版本号. 此项为验证apk升级是否成功,若不配置,则不检查.1.0.5为APK版本号
	 */
	@ColumnType(jdbcType=JdbcType.VARCHAR)
	private String apkVersion;
	
	/**
	 * 为了区分不同工厂,目前组装厂有两个,安畅星和博毅,编号分别为ACX,BY
	 */
	@ColumnType(jdbcType=JdbcType.VARCHAR)
	private String factoryName;
	
	/**
	 * 生产阶段，1贴片 2组装
	 */
	@ColumnType(jdbcType=JdbcType.TINYINT)
	private Integer productionPhase;
	
	/**
	 * 坐标系，bd09ll 百度（默认）gcj02高德
	 */
	@ColumnType(jdbcType=JdbcType.VARCHAR)
	private String gpsCoortype;
	
	/**
	 * 坐标（经度,维度）
	 */
	@ColumnType(jdbcType=JdbcType.VARCHAR)
	private String gpsPoint;
	
	/**
	 * 机构代码
	 */
	@ColumnType(jdbcType=JdbcType.VARCHAR)
	private String orgCode;
	
	/**
	 * 位置信息
	 */
	@ColumnType(jdbcType=JdbcType.VARCHAR)
	private String location;
	
	/**
	 * 项目名称
	 */
	@ColumnType(jdbcType=JdbcType.VARCHAR)
	private String prjName;
	
	/**
	 * 客户产品型号
	 */
	@ColumnType(jdbcType=JdbcType.VARCHAR)
	private String proName;
	
	/**
	 * 测试项成功数量
	 */
	@ColumnType(jdbcType=JdbcType.INTEGER)
	private Integer succNumber;
	
	/**
	 * 测试项错误数量
	 */
	@ColumnType(jdbcType=JdbcType.INTEGER)
	private Integer errorNumber;
	
	/**
	 * 创建时间
	 */
	@ColumnType(jdbcType=JdbcType.TIMESTAMP)
	private Date createTime;
	
	/**
	 * 更新时间
	 */
	@ColumnType(jdbcType=JdbcType.TIMESTAMP)
	private Date updateTime;
}



