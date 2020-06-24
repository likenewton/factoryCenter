package cn.yunovo.iov.factory.framework;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.jasig.cas.client.validation.Assertion;
import org.springframework.stereotype.Component;

import cn.yunovo.iov.boot.autoconfigure.cas.h5.bean.LoginInfo;
import cn.yunovo.iov.boot.autoconfigure.dac.DacHelper;
import cn.yunovo.iov.boot.autoconfigure.dac.bean.UserInfo;
import cn.yunovo.iov.cas.client.authentication.H5ClientAuthenticationFilter;

@Component
public class LoginInfoUtil {

	public static LoginInfo getLoginBaseInfo(HttpServletRequest request) {
		Assertion assertion = (Assertion) request.getAttribute(H5ClientAuthenticationFilter.CONST_CAS_ASSERTION);
		Map<String, Object> attr = assertion.getPrincipal().getAttributes();
		LoginInfo loginInfo = new LoginInfo();
		loginInfo.setLoginName(String.valueOf(attr.get("loginName")));
		loginInfo.setUserName(String.valueOf(attr.get("userName")));
		loginInfo.setId(String.valueOf(attr.get("id")));
		loginInfo.setOrganCode(String.valueOf(attr.get("organCode")));
		loginInfo.setUserType(Integer.valueOf(attr.get("userType").toString()));

		UserInfo loginUser = null;

		if (1 == loginInfo.getUserType()) {
			// 机构用户，做机构代码和用户登录名转换
			loginUser = UserInfo.create().userId(loginInfo.getId()).userType(loginInfo.getUserType()).loginName(loginInfo.getOrganCode());
		} else {
			loginUser = UserInfo.create().userId(loginInfo.getId()).userType(loginInfo.getUserType()).loginName(loginInfo.getLoginName());
		}

		// 在获取用户入口设置了数据权限,保证DacHelper获取到用户信息
		DacHelper.setUser(loginUser);

		return loginInfo;
	}
}
