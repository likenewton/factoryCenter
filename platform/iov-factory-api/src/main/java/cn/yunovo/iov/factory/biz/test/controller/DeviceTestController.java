package cn.yunovo.iov.factory.biz.test.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import org.springframework.validation.annotation.Validated;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.ore.framework.web.api.ResultEntity;

import cn.yunovo.iov.framework.commons.beanutils.bean.BeanMapper;
import cn.yunovo.iov.boot.autoconfigure.request.select.Condition;
import cn.yunovo.iov.boot.autoconfigure.request.select.Group;
import cn.yunovo.iov.boot.autoconfigure.request.select.Limit;
import cn.yunovo.iov.boot.autoconfigure.request.select.Offset;
import cn.yunovo.iov.boot.autoconfigure.request.select.Order;
import cn.yunovo.iov.boot.autoconfigure.request.select.Pages;
import cn.yunovo.iov.factory.biz.VendorService;
import cn.yunovo.iov.factory.biz.device.manager.DeviceReportManager;
import cn.yunovo.iov.factory.biz.device.model.Device;
import cn.yunovo.iov.factory.biz.device.model.Device.DeviceRelInfo;
import cn.yunovo.iov.factory.biz.statistics.model.StatisticsTypeDO;
import cn.yunovo.iov.factory.biz.statistics.service.StatisticsTypeService;
import cn.yunovo.iov.factory.biz.test.model.DeviceTestDO;
import cn.yunovo.iov.factory.biz.test.model.DeviceTestItemDO;
import cn.yunovo.iov.factory.biz.test.model.DeviceTestItemVO;
import cn.yunovo.iov.factory.biz.test.model.DeviceTestQuery;
import cn.yunovo.iov.factory.biz.test.model.DeviceTestVO;
import cn.yunovo.iov.factory.biz.test.service.DeviceTestItemService;
import cn.yunovo.iov.factory.biz.test.service.DeviceTestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ExtensionProperty;
import io.swagger.annotations.Extension;
import lombok.extern.slf4j.Slf4j;

/**
 * Web层(展示层/显示层)：主要是对访问控制进行转发，各类基本参数校验，或者不复用的业务简单处理等。
 * 
 * @author huangzz
 *
 */
@Slf4j
@Scope("prototype")
@RestController
@RequestMapping(value = "/production/device/tests")
@Api(value = "[工厂设备测试]相关 api")
class DeviceTestController {

	@Autowired
	private DeviceTestService deviceTestService;

	@Autowired
	private DeviceReportManager deviceReportManager;

	/*
	 * 分页查询访问方式：GET http://ip:port/production/device/tests?page=1&page_size=2 排除查询访问方式：GET http://ip:port/production/device/tests?name=黄&age=18 条件查询访问方式：GET http://ip:port/production/device/tests?sort=age,desc(按年龄倒叙) 条件查询访问方式：GET
	 * http://ip:port/production/device/tests?descs/ascs=age(按年龄倒叙) 指定返回记录的数量：GET http://ip:port/production/device/tests?limit=20
	 */
	@ApiOperation(notes = "根据条件获取[推送通道]信息", value = "根据条件获取[推送通道]信息", extensions = @Extension(name = "auditLog", properties = @ExtensionProperty(name = "ignore", value = "true")))
	@RequestMapping(method = RequestMethod.GET)
	public ResultEntity<Object> deviceTests(HttpServletRequest request, DeviceTestQuery deviceTestQuery, Pages pages, Limit limit, Order order, Group group, Offset offset) {
		ResultEntity<Object> result = new ResultEntity<Object>();
		Map<String, Condition> conditionMap = new HashMap<String, Condition>();
		conditionMap.put(Condition.PAGES, pages);
		conditionMap.put(Condition.LIMIT, limit);
		conditionMap.put(Condition.ORDER, order);
		conditionMap.put(Condition.OFFSET, offset);
		conditionMap.put(Condition.GROUP, group);
		result.setData(deviceTestService.selectDeviceTest(deviceTestQuery, conditionMap));
		return result;
	}

	// 保存一个对象：POST http://ip:port/production/device/tests
	@ApiOperation(notes = "保存[工厂设备测试]信息", value = "保存[工厂设备测试]信息", extensions = { @Extension(name = "auditLog", properties = { @ExtensionProperty(name = "opType", value = "INSERT") }) })
	@RequestMapping(method = RequestMethod.POST)
	public ResultEntity<DeviceTestVO> insertDeviceTest(@Validated @RequestBody DeviceTestVO deviceTestVO) {
		ResultEntity<DeviceTestVO> result = new ResultEntity<DeviceTestVO>();
		if (StringUtils.isNotBlank(deviceTestVO.getSn())) {
			ResultEntity<String> resultEntity = VendorService.queryDevice(deviceTestVO.getSn());
			if (null != resultEntity) {
				if (null == resultEntity.getData()) {
					result.setMsg(resultEntity.getMsg());
					result.setCode(resultEntity.getCode());
					if (1 == deviceTestVO.getProductionPhase()) {
						log.info("通过SN查询设备:{}", deviceTestVO);
						deviceReportManager.saveServiceLog(deviceTestVO.getIccid(), 5, "tester", resultEntity.getMsg());

					} else if (2 == deviceTestVO.getProductionPhase()) {
						log.info("通过SN查询设备:{}", deviceTestVO);
						deviceReportManager.saveServiceLog(deviceTestVO.getIccid(), 5, "tester", resultEntity.getMsg());
					}
					return result;
				}
			} else {
				result.setMsg("通过SN查询设备异常,SN[" + deviceTestVO.getSn() + "]");
				return result;
			}
		}

		deviceTestService.saveDevice(deviceTestVO);

		log.info("insertDeviceTest DeviceTestController insert result[{}]", deviceTestVO);
		result.setData(deviceTestVO);
		return result;
	}

	

}