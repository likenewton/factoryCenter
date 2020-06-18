package cn.yunovo.iov.factory.framework;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.jasig.cas.client.validation.Assertion;
import org.springframework.stereotype.Component;

import cn.yunovo.iov.boot.autoconfigure.cas.h5.bean.LoginInfo;
import cn.yunovo.iov.cas.client.authentication.H5ClientAuthenticationFilter;
import cn.yunovo.iov.factory.framework.dac.bean.LoginUser;

@Component
public class LoginInfoUtil {

	public static final ThreadLocal<LoginUser> LOGINUSER_LOCAL = new ThreadLocal<LoginUser>();

	public static LoginInfo getLoginBaseInfo(HttpServletRequest request) {
		Assertion assertion = (Assertion) request.getAttribute(H5ClientAuthenticationFilter.CONST_CAS_ASSERTION);
		Map<String, Object> attr = assertion.getPrincipal().getAttributes();
		LoginInfo loginInfo = new LoginInfo();
		loginInfo.setLoginName(String.valueOf(attr.get("loginName")));
		loginInfo.setUserName(String.valueOf(attr.get("userName")));
		loginInfo.setId(String.valueOf(attr.get("id")));
		loginInfo.setOrganCode(String.valueOf(attr.get("organCode")));
		loginInfo.setUserType(Integer.valueOf(attr.get("userType").toString()));

		if (null == LOGINUSER_LOCAL.get()) {
			LoginUser loginUser = null;

			if (1 == loginInfo.getUserType()) {
				// 机构用户，做机构代码和用户登录名转换
				loginUser = LoginUser.create().userId(loginInfo.getId()).userType(loginInfo.getUserType()).loginName(loginInfo.getOrganCode());
			} else {
				loginUser = LoginUser.create().userId(loginInfo.getId()).userType(loginInfo.getUserType()).loginName(loginInfo.getLoginName());
			}

			LOGINUSER_LOCAL.set(loginUser);
		}

		return loginInfo;
	}
}
