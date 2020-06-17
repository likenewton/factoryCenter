package cn.yunovo.iov.device.zen.biz.production.test.controller;

import cn.yunovo.iov.device.zen.biz.production.test.model.DeviceTestDO;
import cn.yunovo.iov.device.zen.biz.production.test.model.DeviceTestDTO;
import cn.yunovo.iov.device.zen.biz.production.test.model.DeviceTestQuery;
import cn.yunovo.iov.device.zen.biz.production.test.model.DeviceTestVO;
import cn.yunovo.iov.device.zen.biz.production.test.service.DeviceTestService;
import cn.yunovo.iov.device.zen.framework.excel.ExcelUtil;
import cn.yunovo.iov.device.zen.framework.excel.FileDownloader;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import cn.yunovo.iov.boot.autoconfigure.request.select.Export;
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
@RequestMapping(value = "/production/device/tests")
@Api(value = "[工厂设备测试]相关 api")
class DeviceTestController {

	@Autowired
	private DeviceTestService deviceTestService;

	private void export(HttpServletRequest request, HttpServletResponse response, List<DeviceTestVO> list) {

		response.setHeader("Set-Cookie", "fileDownload=true; path=/");
		response.reset();
		long beginTime = System.currentTimeMillis();
		FileDownloader fileDownloader = null;
		// 基于流导出
		// OutputStream out = new BufferedOutputStream(response.getOutputStream());
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ExcelUtil.getInstance().exportObj2Excel(out, list, DeviceTestVO.class);
		fileDownloader = new FileDownloader(out.toByteArray(), "上报导出.xlsx");
		fileDownloader.download(request, response);

		long endTime = System.currentTimeMillis();
		log.info("导出数据耗时：【" + (endTime - beginTime) / 1000 + "】秒");
	}

	/*
	 * 分页查询访问方式：GET http://ip:port/production/device/tests?page=1&page_size=2
	 * 排除查询访问方式：GET http://ip:port/production/device/tests?name=黄&age=18
	 * 条件查询访问方式：GET http://ip:port/production/device/tests?sort=age,desc(按年龄倒叙)
	 * 条件查询访问方式：GET http://ip:port/production/device/tests?descs/ascs=age(按年龄倒叙)
	 * 指定返回记录的数量：GET http://ip:port/production/device/tests?limit=20
	*/
	@ApiOperation(notes="根据条件获取[推送通道]信息",value = "根据条件获取[推送通道]信息", extensions = @Extension(name = "auditLog", properties = @ExtensionProperty(name = "ignore", value = "true")))
	@RequestMapping(method = RequestMethod.GET)
	public ResultEntity<Object> deviceTests(HttpServletRequest request,HttpServletResponse response, DeviceTestQuery deviceTestQuery, Pages pages,
			Limit limit, Order order, Group group, Offset offset, Export export) {
		ResultEntity<Object> result = new ResultEntity<Object>();
		Map<String,Condition> conditionMap = new HashMap<String,Condition>();
		conditionMap.put(Condition.PAGES, pages);
		conditionMap.put(Condition.LIMIT, limit);
		conditionMap.put(Condition.ORDER, order);
		conditionMap.put(Condition.OFFSET, offset);
		conditionMap.put(Condition.GROUP, group);
		
		if (null != export.getExport()) {
			conditionMap.put(Condition.PAGES, null);
		}else {
			result.setData(deviceTestService.selectDeviceTest(deviceTestQuery, conditionMap));
		}
		
		if (null != export.getExport()) {
			export(request, response, (List<DeviceTestVO>) deviceTestService.selectDeviceTest(deviceTestQuery, conditionMap));
			return null;
		}
		
		return result;
	}
	
	// 获取一个对象：GET http://ip:port/production/device/tests/{id}
	@ApiOperation(notes="根据ID获取[工厂设备测试]信息", value = "根据ID获取[工厂设备测试]信息", extensions = {
			@Extension(name = "auditLog", properties = {
					@ExtensionProperty(name = "opType", value = "QUERY") }) })
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public DeviceTestVO getDeviceTestById(@PathVariable("id") Integer id) {
		DeviceTestDTO deviceTestDTO = deviceTestService.getDeviceTestById(id);
		DeviceTestVO deviceTestVO = BeanMapper.map(deviceTestDTO, DeviceTestVO.class);
		return deviceTestVO;
	}

	// 删除一个对象：DELETE http://ip:port/production/device/tests/{id}
	@ApiOperation(notes="根据ID删除[工厂设备测试]信息", value = "根据ID删除[工厂设备测试]信息", extensions = {
			@Extension(name = "auditLog", properties = {
					@ExtensionProperty(name = "opType", value = "DELETE") }) })
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResultEntity<Integer> deleteDeviceTestById(@PathVariable("id") Integer id) {
		ResultEntity<Integer> result = new ResultEntity<>();
		Integer del = deviceTestService.deleteDeviceTestById(id);
		log.info("delete DeviceTestController delete result[{}]", del);
		return result;
	}

	// 保存一个对象：POST http://ip:port/production/device/tests
	@ApiOperation(notes="保存[工厂设备测试]信息", value = "保存[工厂设备测试]信息", extensions = {
			@Extension(name = "auditLog", properties = {
					@ExtensionProperty(name = "opType", value = "INSERT") }) })
	@RequestMapping(method = RequestMethod.POST)
	public DeviceTestVO insertDeviceTest(@Validated @RequestBody DeviceTestVO deviceTestVO)
	{
		DeviceTestDO deviceTestDO = BeanMapper.map(deviceTestVO, DeviceTestDO.class);
		deviceTestService.insertDeviceTest(deviceTestDO);
		
		DeviceTestDTO deviceTestDTO = deviceTestService.getDeviceTestById(deviceTestDO.getId());
		deviceTestVO = BeanMapper.map(deviceTestDTO, DeviceTestVO.class);
		
		log.info("insertDeviceTest DeviceTestController insert result[{}]", deviceTestVO);
		return deviceTestVO;
	}

	// 用于更新某个资源较完整的内容：PUT http://ip:port/production/device/tests
	@ApiOperation(notes="更新[工厂设备测试]信息", value = "更新[工厂设备测试]信息", extensions = {
			@Extension(name = "auditLog", properties = {
					@ExtensionProperty(name = "opType", value = "UPDATE") }) })
	@RequestMapping(method = RequestMethod.PUT)
	public DeviceTestVO editDeviceTest(@Validated @RequestBody DeviceTestVO deviceTestVO)
	{
		DeviceTestDO deviceTestDO = BeanMapper.map(deviceTestVO, DeviceTestDO.class);
		deviceTestService.updateDeviceTest(deviceTestDO);
		
		DeviceTestDTO deviceTestDTO = deviceTestService.getDeviceTestById(deviceTestDO.getId());
		deviceTestVO = BeanMapper.map(deviceTestDTO, DeviceTestVO.class);
		
		log.info("edit$DeviceTest DeviceTestController edit result[{}]", deviceTestVO);
		return deviceTestVO ;
	}
	
	// 用于资源的部分内容的更新：PUT http://ip:port/production/device/tests
	@ApiOperation(notes="部分内容的更新[工厂设备测试]信息", value = "部分内容的更新[工厂设备测试]信息", extensions = {
			@Extension(name = "auditLog", properties = {
					@ExtensionProperty(name = "opType", value = "UPDATE") }) })
	@RequestMapping(method = RequestMethod.PATCH)
	public DeviceTestVO updateDeviceTest(@RequestBody DeviceTestVO deviceTestVO)
	{
		DeviceTestDO deviceTestDO = BeanMapper.map(deviceTestVO, DeviceTestDO.class);
		deviceTestService.updateDeviceTest(deviceTestDO);
		
		DeviceTestDTO deviceTestDTO = deviceTestService.getDeviceTestById(deviceTestDO.getId());
		deviceTestVO = BeanMapper.map(deviceTestDTO, DeviceTestVO.class);
		
		log.info("updateDeviceTest DeviceTestController update result[{}]", deviceTestVO);
		return deviceTestVO ;
	}	


}