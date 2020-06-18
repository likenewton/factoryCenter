package cn.yunovo.iov.factory.framework.dac.bean;

import lombok.Getter;

@Getter
public class LoginUser {

	private String userId;
	
	private String loginName;

	private Integer userType;

	public static LoginUser create() {
		LoginUser user = new LoginUser();
		return user;
	}
	
	public LoginUser userId(String userId) {
		this.userId = userId;
		return this;
	}
	
	public LoginUser loginName(String loginName) {
		this.loginName = loginName;
		return this;
	}
	
	
	public LoginUser userType(Integer userType) {
		this.userType = userType;
		return this;
	}
	
}
