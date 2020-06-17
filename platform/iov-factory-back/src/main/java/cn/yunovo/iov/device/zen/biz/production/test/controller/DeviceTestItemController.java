package cn.yunovo.iov.device.zen.biz.production.test.controller;

import cn.yunovo.iov.device.zen.biz.production.test.model.DeviceTestItemDO;
import cn.yunovo.iov.device.zen.biz.production.test.model.DeviceTestItemDTO;
import cn.yunovo.iov.device.zen.biz.production.test.model.DeviceTestItemQuery;
import cn.yunovo.iov.device.zen.biz.production.test.model.DeviceTestItemVO;
import cn.yunovo.iov.device.zen.biz.production.test.service.DeviceTestItemService;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.PathVariable;
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
@RequestMapping(value = "/production/device/test/items")
@Api(value = "[设备测试项]相关 api")
class DeviceTestItemController {

	@Autowired
	private DeviceTestItemService deviceTestItemService;

	/*
	 * 分页查询访问方式：GET http://ip:port/production/device/test/items?page=1&page_size=2
	 * 排除查询访问方式：GET http://ip:port/production/device/test/items?name=黄&age=18
	 * 条件查询访问方式：GET http://ip:port/production/device/test/items?sort=age,desc(按年龄倒叙)
	 * 条件查询访问方式：GET http://ip:port/production/device/test/items?descs/ascs=age(按年龄倒叙)
	 * 指定返回记录的数量：GET http://ip:port/production/device/test/items?limit=20
	*/
	@ApiOperation(notes="根据条件获取[推送通道]信息",value = "根据条件获取[推送通道]信息", extensions = @Extension(name = "auditLog", properties = @ExtensionProperty(name = "ignore", value = "true")))
	@RequestMapping(method = RequestMethod.GET)
	public ResultEntity<Object> deviceTestItems(HttpServletRequest request, DeviceTestItemQuery deviceTestItemQuery, Pages pages,
			Limit limit, Order order, Group group, Offset offset) {
		ResultEntity<Object> result = new ResultEntity<Object>();
		Map<String,Condition> conditionMap = new HashMap<String,Condition>();
		conditionMap.put(Condition.PAGES, pages);
		conditionMap.put(Condition.LIMIT, limit);
		conditionMap.put(Condition.ORDER, order);
		conditionMap.put(Condition.OFFSET, offset);
		conditionMap.put(Condition.GROUP, group);
		result.setData(deviceTestItemService.selectDeviceTestItem(deviceTestItemQuery, conditionMap));
		return result;
	}
	
	// 获取一个对象：GET http://ip:port/production/device/test/items/{id}
	@ApiOperation(notes="根据ID获取[设备测试项]信息", value = "根据ID获取[设备测试项]信息", extensions = {
			@Extension(name = "auditLog", properties = {
					@ExtensionProperty(name = "opType", value = "QUERY") }) })
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public DeviceTestItemVO getDeviceTestItemById(@PathVariable("id") Integer id) {
		DeviceTestItemDTO deviceTestItemDTO = deviceTestItemService.getDeviceTestItemById(id);
		DeviceTestItemVO deviceTestItemVO = BeanMapper.map(deviceTestItemDTO, DeviceTestItemVO.class);
		return deviceTestItemVO;
	}

	// 删除一个对象：DELETE http://ip:port/production/device/test/items/{id}
	@ApiOperation(notes="根据ID删除[设备测试项]信息", value = "根据ID删除[设备测试项]信息", extensions = {
			@Extension(name = "auditLog", properties = {
					@ExtensionProperty(name = "opType", value = "DELETE") }) })
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResultEntity<Integer> deleteDeviceTestItemById(@PathVariable("id") Integer id) {
		ResultEntity<Integer> result = new ResultEntity<>();
		Integer del = deviceTestItemService.deleteDeviceTestItemById(id);
		log.info("delete DeviceTestItemController delete result[{}]", del);
		return result;
	}

	// 保存一个对象：POST http://ip:port/production/device/test/items
	@ApiOperation(notes="保存[设备测试项]信息", value = "保存[设备测试项]信息", extensions = {
			@Extension(name = "auditLog", properties = {
					@ExtensionProperty(name = "opType", value = "INSERT") }) })
	@RequestMapping(method = RequestMethod.POST)
	public DeviceTestItemVO insertDeviceTestItem(@Validated @RequestBody DeviceTestItemVO deviceTestItemVO)
	{
		DeviceTestItemDO deviceTestItemDO = BeanMapper.map(deviceTestItemVO, DeviceTestItemDO.class);
		deviceTestItemService.insertDeviceTestItem(deviceTestItemDO);
		
		DeviceTestItemDTO deviceTestItemDTO = deviceTestItemService.getDeviceTestItemById(deviceTestItemDO.getId());
		deviceTestItemVO = BeanMapper.map(deviceTestItemDTO, DeviceTestItemVO.class);
		
		log.info("insertDeviceTestItem DeviceTestItemController insert result[{}]", deviceTestItemVO);
		return deviceTestItemVO;
	}

	// 用于更新某个资源较完整的内容：PUT http://ip:port/production/device/test/items
	@ApiOperation(notes="更新[设备测试项]信息", value = "更新[设备测试项]信息", extensions = {
			@Extension(name = "auditLog", properties = {
					@ExtensionProperty(name = "opType", value = "UPDATE") }) })
	@RequestMapping(method = RequestMethod.PUT)
	public DeviceTestItemVO editDeviceTestItem(@Validated @RequestBody DeviceTestItemVO deviceTestItemVO)
	{
		DeviceTestItemDO deviceTestItemDO = BeanMapper.map(deviceTestItemVO, DeviceTestItemDO.class);
		deviceTestItemService.updateDeviceTestItem(deviceTestItemDO);
		
		DeviceTestItemDTO deviceTestItemDTO = deviceTestItemService.getDeviceTestItemById(deviceTestItemDO.getId());
		deviceTestItemVO = BeanMapper.map(deviceTestItemDTO, DeviceTestItemVO.class);
		
		log.info("edit$DeviceTestItem DeviceTestItemController edit result[{}]", deviceTestItemVO);
		return deviceTestItemVO ;
	}
	
	// 用于资源的部分内容的更新：PUT http://ip:port/production/device/test/items
	@ApiOperation(notes="部分内容的更新[设备测试项]信息", value = "部分内容的更新[设备测试项]信息", extensions = {
			@Extension(name = "auditLog", properties = {
					@ExtensionProperty(name = "opType", value = "UPDATE") }) })
	@RequestMapping(method = RequestMethod.PATCH)
	public DeviceTestItemVO updateDeviceTestItem(@RequestBody DeviceTestItemVO deviceTestItemVO)
	{
		DeviceTestItemDO deviceTestItemDO = BeanMapper.map(deviceTestItemVO, DeviceTestItemDO.class);
		deviceTestItemService.updateDeviceTestItem(deviceTestItemDO);
		
		DeviceTestItemDTO deviceTestItemDTO = deviceTestItemService.getDeviceTestItemById(deviceTestItemDO.getId());
		deviceTestItemVO = BeanMapper.map(deviceTestItemDTO, DeviceTestItemVO.class);
		
		log.info("updateDeviceTestItem DeviceTestItemController update result[{}]", deviceTestItemVO);
		return deviceTestItemVO ;
	}	


}