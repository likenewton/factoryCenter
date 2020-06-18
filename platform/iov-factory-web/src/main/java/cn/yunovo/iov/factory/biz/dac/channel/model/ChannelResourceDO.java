package cn.yunovo.iov.factory.biz.dac.channel.model;

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
public class ChannelResourceDO {
	
	/**
	 * 主键ID
	 */
	private Integer id;
	
	/**
	 * 数据表中的主键
	 */
	private Integer dataId;
	
	/**
	 * 数据提供的表,值为主表
	 */
	private String dataProvider;
	
	/**
	 * 数据创建者ID
	 */
	private String creatorId;
	
	/**
	 * 来源创建者ID
	 */
	private String sourceCreatorId;
	
	/**
	 * 创建时间
	 */
	private Date createTime;
	
	/**
	 * 更新时间
	 */
	private Date updateTime;
}
