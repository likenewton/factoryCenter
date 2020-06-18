package cn.yunovo.iov.factory.framework;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.jasig.cas.client.validation.Assertion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.yunovo.iov.boot.autoconfigure.cas.h5.bean.LoginInfo;
import cn.yunovo.iov.cas.client.authentication.H5ClientAuthenticationFilter;

@Component
public class LoginInfoUtil {
	
	@Autowired
	private HttpServletRequest request;
	
	public LoginInfo getLoginBaseInfo() {
		Assertion assertion = (Assertion) request.getAttribute(H5ClientAuthenticationFilter.CONST_CAS_ASSERTION);
		Map<String, Object> attr = assertion.getPrincipal().getAttributes();
		LoginInfo loginInfo = new LoginInfo();
		loginInfo.setLoginName(String.valueOf(attr.get("loginName")));
		loginInfo.setUserName(String.valueOf(attr.get("userName")));
		loginInfo.setId(String.valueOf(attr.get("id")));
		loginInfo.setOrganCode(String.valueOf(attr.get("organCode")));
		loginInfo.setUserType(Integer.valueOf(attr.get("userType").toString()));
		
		// 机构用户，做机构代码和用户登录名转换
		if(1 == loginInfo.getUserType()) {
			loginInfo.setLoginName(loginInfo.getOrganCode());
		}
		return loginInfo;
	}
}
