package cn.yunovo.iov.device.zen.biz.other.log.controller;

import cn.yunovo.iov.device.zen.biz.other.log.model.DeviceDO;
import cn.yunovo.iov.device.zen.biz.other.log.model.DeviceDTO;
import cn.yunovo.iov.device.zen.biz.other.log.model.DeviceQuery;
import cn.yunovo.iov.device.zen.biz.other.log.model.DeviceVO;
import cn.yunovo.iov.device.zen.biz.other.log.service.DeviceService;

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
@RequestMapping(value = "/other/devices")
@Api(value = "[]相关 api")
class DeviceController {

	@Autowired
	private DeviceService deviceService;

	/*
	 * 分页查询访问方式：GET http://ip:port/other/devices?page=1&page_size=2
	 * 排除查询访问方式：GET http://ip:port/other/devices?name=黄&age=18
	 * 条件查询访问方式：GET http://ip:port/other/devices?sort=age,desc(按年龄倒叙)
	 * 条件查询访问方式：GET http://ip:port/other/devices?descs/ascs=age(按年龄倒叙)
	 * 指定返回记录的数量：GET http://ip:port/other/devices?limit=20
	*/
	@ApiOperation(notes="根据条件获取[推送通道]信息",value = "根据条件获取[推送通道]信息", extensions = @Extension(name = "auditLog", properties = @ExtensionProperty(name = "ignore", value = "true")))
	@RequestMapping(method = RequestMethod.GET)
	public ResultEntity<Object> devices(HttpServletRequest request, DeviceQuery deviceQuery, Pages pages,
			Limit limit, Order order, Group group, Offset offset) {
		ResultEntity<Object> result = new ResultEntity<Object>();
		Map<String,Condition> conditionMap = new HashMap<String,Condition>();
		conditionMap.put(Condition.PAGES, pages);
		conditionMap.put(Condition.LIMIT, limit);
		conditionMap.put(Condition.ORDER, order);
		conditionMap.put(Condition.OFFSET, offset);
		conditionMap.put(Condition.GROUP, group);
		result.setData(deviceService.selectDevice(deviceQuery, conditionMap));
		return result;
	}
	
	// 获取一个对象：GET http://ip:port/other/devices/{id}
	@ApiOperation(notes="根据ID获取[]信息", value = "根据ID获取[]信息", extensions = {
			@Extension(name = "auditLog", properties = {
					@ExtensionProperty(name = "opType", value = "QUERY") }) })
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public DeviceVO getDeviceById(@PathVariable("id") Integer id) {
		DeviceDTO deviceDTO = deviceService.getDeviceById(id);
		DeviceVO deviceVO = BeanMapper.map(deviceDTO, DeviceVO.class);
		return deviceVO;
	}

	// 删除一个对象：DELETE http://ip:port/other/devices/{id}
	@ApiOperation(notes="根据ID删除[]信息", value = "根据ID删除[]信息", extensions = {
			@Extension(name = "auditLog", properties = {
					@ExtensionProperty(name = "opType", value = "DELETE") }) })
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResultEntity<Integer> deleteDeviceById(@PathVariable("id") Integer id) {
		ResultEntity<Integer> result = new ResultEntity<>();
		Integer del = deviceService.deleteDeviceById(id);
		log.info("delete DeviceController delete result[{}]", del);
		return result;
	}

	// 保存一个对象：POST http://ip:port/other/devices
	@ApiOperation(notes="保存[]信息", value = "保存[]信息", extensions = {
			@Extension(name = "auditLog", properties = {
					@ExtensionProperty(name = "opType", value = "INSERT") }) })
	@RequestMapping(method = RequestMethod.POST)
	public DeviceVO insertDevice(@Validated @RequestBody DeviceVO deviceVO)
	{
		DeviceDO deviceDO = BeanMapper.map(deviceVO, DeviceDO.class);
		deviceService.insertDevice(deviceDO);
		
		DeviceDTO deviceDTO = deviceService.getDeviceById(deviceDO.getId());
		deviceVO = BeanMapper.map(deviceDTO, DeviceVO.class);
		
		log.info("insertDevice DeviceController insert result[{}]", deviceVO);
		return deviceVO;
	}

	// 用于更新某个资源较完整的内容：PUT http://ip:port/other/devices
	@ApiOperation(notes="更新[]信息", value = "更新[]信息", extensions = {
			@Extension(name = "auditLog", properties = {
					@ExtensionProperty(name = "opType", value = "UPDATE") }) })
	@RequestMapping(method = RequestMethod.PUT)
	public DeviceVO editDevice(@Validated @RequestBody DeviceVO deviceVO)
	{
		DeviceDO deviceDO = BeanMapper.map(deviceVO, DeviceDO.class);
		deviceService.updateDevice(deviceDO);
		
		DeviceDTO deviceDTO = deviceService.getDeviceById(deviceDO.getId());
		deviceVO = BeanMapper.map(deviceDTO, DeviceVO.class);
		
		log.info("edit$Device DeviceController edit result[{}]", deviceVO);
		return deviceVO ;
	}
	
	// 用于资源的部分内容的更新：PUT http://ip:port/other/devices
	@ApiOperation(notes="部分内容的更新[]信息", value = "部分内容的更新[]信息", extensions = {
			@Extension(name = "auditLog", properties = {
					@ExtensionProperty(name = "opType", value = "UPDATE") }) })
	@RequestMapping(method = RequestMethod.PATCH)
	public DeviceVO updateDevice(@RequestBody DeviceVO deviceVO)
	{
		DeviceDO deviceDO = BeanMapper.map(deviceVO, DeviceDO.class);
		deviceService.updateDevice(deviceDO);
		
		DeviceDTO deviceDTO = deviceService.getDeviceById(deviceDO.getId());
		deviceVO = BeanMapper.map(deviceDTO, DeviceVO.class);
		
		log.info("updateDevice DeviceController update result[{}]", deviceVO);
		return deviceVO ;
	}	


}