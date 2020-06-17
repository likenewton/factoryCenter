package cn.yunovo.iov.factory.biz.dac.user.model;

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
public class DacUserDO {
	
	/**
	 * 主键ID
	 */
	private Integer id;
	
	/**
	 * 用户ID
	 */
	private String userId;
	
	/**
	 * 用户名称
	 */
	private String userMapper;
	
	/**
	 * 用户类型:1机构(品牌)，2工厂
	 */
	private Integer userType;
}
