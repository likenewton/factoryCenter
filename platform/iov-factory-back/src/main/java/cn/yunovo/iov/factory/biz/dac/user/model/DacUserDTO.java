package cn.yunovo.iov.factory.biz.dac.user.model;


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
public class DacUserDTO {
	
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
