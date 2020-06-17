package cn.yunovo.iov.factory.biz.shipping.channel.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.sunshine.dcda.system.service.model.SystemAreaQueryBean;
import org.sunshine.dcda.system.service.model.SystemAreaVo;
import org.sunshine.dcda.view.system.viewcomponent.ISystemAreaViewComponent;

import com.github.ore.framework.web.api.ResultEntity;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Extension;
import io.swagger.annotations.ExtensionProperty;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Scope("prototype")
@RestController
@RequestMapping(value = "/shipping/ares")
@Api(value = "[渠道管理]相关 api")
class AreaController {

	@Autowired
	private ISystemAreaViewComponent systemAreaViewComponent;

	@ApiOperation(notes="根据条件获取[区域]信息",value = "根据条件获取[区域]信息", extensions = @Extension(name = "auditLog", properties = @ExtensionProperty(name = "ignore", value = "true")))
	@RequestMapping(method = RequestMethod.GET)
	public ResultEntity<Object> ares(SystemAreaQueryBean systemAreaQueryBean) {
		ResultEntity<Object> result = new ResultEntity<Object>();
		try {
			List<SystemAreaVo> ares = systemAreaViewComponent.querySystemAreaList(systemAreaQueryBean);
			result.setData(ares);
		} catch (Exception e) {
			log.error("获取区域异常",e);
		}
		return result;
	}
	
	
}