package cn.yunovo.iov.factory.biz.shipping.channel.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.jasig.cas.client.validation.Assertion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.sunshine.dcda.system.service.model.CooperateOrganQueryBean;
import org.sunshine.dcda.system.service.model.CooperateOrganVo;
import org.sunshine.dcda.view.system.viewcomponent.ICooperateOrganViewComponent;

import com.github.ore.framework.web.api.ResultEntity;

import cn.yunovo.iov.boot.autoconfigure.cas.h5.bean.LoginInfo;
import cn.yunovo.iov.cas.client.authentication.H5ClientAuthenticationFilter;
import cn.yunovo.iov.factory.framework.LoginInfoUtil;
import cn.yunovo.iov.factory.framework.config.NacosValueConfig;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Extension;
import io.swagger.annotations.ExtensionProperty;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Scope("prototype")
@RestController
@RequestMapping(value = "/shipping/orgs")
@Api(value = "[渠道管理]相关 api")
class OrganController {

	@Autowired
	private ICooperateOrganViewComponent cooperateOrganViewComponent;
	
	@Autowired
	private NacosValueConfig nacosValueConfig;

	
	private String getLoginBaseInfo(HttpServletRequest request) {
		if(null != request.getAttribute(H5ClientAuthenticationFilter.CONST_CAS_ASSERTION)) {
			Assertion assertion = (Assertion) request.getAttribute(H5ClientAuthenticationFilter.CONST_CAS_ASSERTION);
			Map<String, Object> attr = assertion.getPrincipal().getAttributes();
			LoginInfo loginInfo = new LoginInfo();
			loginInfo.setLoginName(String.valueOf(attr.get("loginName")));
			loginInfo.setUserName(String.valueOf(attr.get("userName")));
			loginInfo.setId(String.valueOf(attr.get("id")));
			loginInfo.setOrganCode(String.valueOf(attr.get("organCode")));
			return loginInfo.getId();
		}
		return null;
		
	}
	
	@ApiOperation(notes="根据条件获取[获取组织机构]信息",value = "根据条件获取[获取组织机构]信息", extensions = @Extension(name = "auditLog", properties = @ExtensionProperty(name = "ignore", value = "true")))
	@RequestMapping(method = RequestMethod.GET)
	public ResultEntity<Object> orgs(HttpServletRequest request,CooperateOrganQueryBean cooperateOrganQueryBean) {
		ResultEntity<Object> result = new ResultEntity<Object>();
		try {
			String[] orgCodes = nacosValueConfig.orgCodes.split(",");
			Map<String, String> orgCodesMap = new HashMap<String, String> ();
			for(String orgCode:orgCodes) {
				orgCodesMap.put(orgCode,orgCode);
			}
			List<CooperateOrganVo> filterList = new ArrayList<CooperateOrganVo>();
			
			//品牌机构的时候
			List<CooperateOrganVo> orgList = cooperateOrganViewComponent.queryCooperateOrganList(cooperateOrganQueryBean, getLoginBaseInfo(request));
			if(1 == LoginInfoUtil.getLoginBaseInfo(request).getUserType()) {
				for(CooperateOrganVo vo: orgList) {
					if(orgCodesMap.containsKey(vo.getCode())) {
						if(LoginInfoUtil.getLoginBaseInfo(request).getOrganCode().equals(orgCodesMap.get(vo.getCode()))) {
							filterList.add(vo);
						}
					}
				}
			}else {
				for(CooperateOrganVo vo: orgList) {
					if(orgCodesMap.containsKey(vo.getCode())) {
						filterList.add(vo);
					}
				}
			}
			
			result.setData(filterList);
		} catch (Exception e) {
			log.error("获取组织机构异常",e);
		}
		return result;
	}
	
	
}