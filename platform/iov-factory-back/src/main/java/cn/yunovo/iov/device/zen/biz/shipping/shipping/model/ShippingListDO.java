package cn.yunovo.iov.device.zen.biz.shipping.shipping.model;

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
public class ShippingListDO {
	
	/**
	 * 主键ID
	 */
	private Integer id;
	
	/**
	 * 渠道ID
	 */
	private Integer channelId;
	
	/**
	 * 渠道区域
	 */
	private String area;
	
	/**
	 * 品牌名称(机构代码orgCode)
	 */
	private String brandName;
	
	/**
	 * 工厂名称
	 */
	private String factoryName;
	
	/**
	 * 云智码
	 */
	private String yunovoCode;
	
	/**
	 * 设备数据
	 */
	private Integer deviceNumber;
	
	/**
	 * 型号
	 */
	private String modelNumber;
	
	/**
	 * 工单号
	 */
	private String workOrderno;
	
	/**
	 * 导入备注
	 */
	private String remark;
	
	/**
	 * 操作人
	 */
	private String operator;
	
	/**
	 * 上报时间
	 */
	private Date importTime;
	
	/**
	 * 生产日期
	 */
	private String productDate;
	
	/**
	 * 创建时间
	 */
	private Date createTime;
	
	/**
	 * 修改时间
	 */
	private Date updateTime;
}
