package cn.yunovo.iov.factory.biz.shipping.channel.model;

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
public class ChannelDO {
	
	/**
	 * 主键ID
	 */
	private Integer id;
	
	/**
	 * 父ID
	 */
	private Integer parentId;
	
	/**
	 * 渠道名称
	 */
	private String channelName;
	
	/**
	 * 品牌名称(机构代码orgCode)
	 */
	private String brandName;
	
	/**
	 * 区域
	 */
	private String area;
	
	/**
	 * 区域ID串，逗号隔开
	 */
	private String areaIds;
	
	/**
	 * 父ID串，逗号隔开
	 */
	private String paths;
	
	/**
	 * 级别
	 */
	private Integer level;
	
	/**
	 * 联系人
	 */
	private String contacts;
	
	/**
	 * 手机号
	 */
	private String phone;
	
	/**
	 * 渠道详细地址
	 */
	private String address;
	
	/**
	 * 创建时间
	 */
	private Date createTime;
	
	/**
	 * 更新时间
	 */
	private Date updateTime;
}
