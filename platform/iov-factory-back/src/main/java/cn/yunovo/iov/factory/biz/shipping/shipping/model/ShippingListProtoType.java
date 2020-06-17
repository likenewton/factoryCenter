package cn.yunovo.iov.factory.biz.shipping.shipping.model;

import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;
import org.apache.ibatis.type.JdbcType;

import lombok.Data;
import lombok.ToString;
import tk.mybatis.mapper.annotation.ColumnType;

/**
 * 发货导入清单 实体
 */
@Data
@ToString
@Table(name = "t_shipping_list")
public class ShippingListProtoType{
	
	
	/**
	 * 主键ID
	 */
	@Id
	private Integer id;
	
	/**
	 * 渠道ID
	 */
	@ColumnType(jdbcType=JdbcType.INTEGER)
	private Integer channelId;
	
	/**
	 * 渠道区域
	 */
	@ColumnType(jdbcType=JdbcType.VARCHAR)
	private String area;
	
	/**
	 * 品牌名称(机构代码orgCode)
	 */
	@ColumnType(jdbcType=JdbcType.VARCHAR)
	private String brandName;
	
	/**
	 * 工厂名称
	 */
	@ColumnType(jdbcType=JdbcType.VARCHAR)
	private String factoryName;
	
	/**
	 * 云智码
	 */
	@ColumnType(jdbcType=JdbcType.VARCHAR)
	private String yunovoCode;
	
	/**
	 * 设备数据
	 */
	@ColumnType(jdbcType=JdbcType.INTEGER)
	private Integer deviceNumber;
	
	/**
	 * 型号
	 */
	@ColumnType(jdbcType=JdbcType.VARCHAR)
	private String modelNumber;
	
	/**
	 * 工单号
	 */
	@ColumnType(jdbcType=JdbcType.VARCHAR)
	private String workOrderno;
	
	/**
	 * 导入备注
	 */
	@ColumnType(jdbcType=JdbcType.VARCHAR)
	private String remark;
	
	/**
	 * 操作人
	 */
	@ColumnType(jdbcType=JdbcType.VARCHAR)
	private String operator;
	
	/**
	 * 上报时间
	 */
	@ColumnType(jdbcType=JdbcType.TIMESTAMP)
	private Date importTime;
	
	/**
	 * 生产日期
	 */
	@ColumnType(jdbcType=JdbcType.VARCHAR)
	private String productDate;
	
	/**
	 * 创建时间
	 */
	@ColumnType(jdbcType=JdbcType.TIMESTAMP)
	private Date createTime;
	
	/**
	 * 修改时间
	 */
	@ColumnType(jdbcType=JdbcType.TIMESTAMP)
	private Date updateTime;
}



