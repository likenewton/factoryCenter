package cn.yunovo.iov.device.zen.biz.report.device.controller;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.ore.framework.web.api.ResultEntity;

import cn.yunovo.iov.boot.autoconfigure.request.select.Condition;
import cn.yunovo.iov.boot.autoconfigure.request.select.Export;
import cn.yunovo.iov.boot.autoconfigure.request.select.Group;
import cn.yunovo.iov.boot.autoconfigure.request.select.Limit;
import cn.yunovo.iov.boot.autoconfigure.request.select.Offset;
import cn.yunovo.iov.boot.autoconfigure.request.select.Order;
import cn.yunovo.iov.boot.autoconfigure.request.select.Pages;
import cn.yunovo.iov.device.zen.biz.report.device.model.DeviceCardDO;
import cn.yunovo.iov.device.zen.biz.report.device.model.DeviceCardDTO;
import cn.yunovo.iov.device.zen.biz.report.device.model.DeviceCardQuery;
import cn.yunovo.iov.device.zen.biz.report.device.model.DeviceCardVO;
import cn.yunovo.iov.device.zen.biz.report.device.service.DeviceCardService;
import cn.yunovo.iov.device.zen.framework.excel.ExcelUtil;
import cn.yunovo.iov.device.zen.framework.excel.FileDownloader;
import cn.yunovo.iov.framework.commons.beanutils.bean.BeanMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Extension;
import io.swagger.annotations.ExtensionProperty;
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
@RequestMapping(value = "/report/device/cards")
@Api(value = "[三码关系表]相关 api")
class DeviceCardController {

	@Autowired
	private DeviceCardService deviceCardService;
	
	private void export(HttpServletRequest request, HttpServletResponse response, List<DeviceCardVO> list) {
		
		response.setHeader("Set-Cookie", "fileDownload=true; path=/");
		response.reset();
		long beginTime = System.currentTimeMillis();
		FileDownloader fileDownloader = null;
		// 基于流导出
		// OutputStream out = new BufferedOutputStream(response.getOutputStream());
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ExcelUtil.getInstance().exportObj2Excel(out, list, DeviceCardVO.class);
		fileDownloader = new FileDownloader(out.toByteArray(), "上报三码关系信息.xlsx");
		fileDownloader.download(request, response);
	
		long endTime = System.currentTimeMillis();
		log.info("导出数据耗时：【" + (endTime - beginTime) / 1000 + "】秒");
	}

	/*
	 * 分页查询访问方式：GET http://ip:port/report/device/cards?page=1&page_size=2
	 * 排除查询访问方式：GET http://ip:port/report/device/cards?name=黄&age=18
	 * 条件查询访问方式：GET http://ip:port/report/device/cards?sort=age,desc(按年龄倒叙)
	 * 条件查询访问方式：GET http://ip:port/report/device/cards?descs/ascs=age(按年龄倒叙)
	 * 指定返回记录的数量：GET http://ip:port/report/device/cards?limit=20
	*/
	@SuppressWarnings("unchecked")
	@ApiOperation(notes="根据条件获取[推送通道]信息",value = "根据条件获取[推送通道]信息", extensions = @Extension(name = "auditLog", properties = @ExtensionProperty(name = "ignore", value = "true")))
	@RequestMapping(method = RequestMethod.GET)
	public ResultEntity<Object> deviceCards(HttpServletRequest request,HttpServletResponse response, DeviceCardQuery deviceCardQuery, Pages pages,
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
		}
		
		Object obj = deviceCardService.selectDeviceCard(deviceCardQuery, conditionMap);
		result.setData(obj);
	
		if (null != export.getExport()) {
			export(request, response, (List<DeviceCardVO>) obj);
			return null;
		}
		
		return result;
	}
	
	// 获取一个对象：GET http://ip:port/report/device/cards/{id}
	@ApiOperation(notes="根据ID获取[三码关系表]信息", value = "根据ID获取[三码关系表]信息", extensions = {
			@Extension(name = "auditLog", properties = {
					@ExtensionProperty(name = "opType", value = "QUERY") }) })
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public DeviceCardVO getDeviceCardById(@PathVariable("id") Integer id) {
		DeviceCardDTO deviceCardDTO = deviceCardService.getDeviceCardById(id);
		DeviceCardVO deviceCardVO = BeanMapper.map(deviceCardDTO, DeviceCardVO.class);
		return deviceCardVO;
	}

	// 删除一个对象：DELETE http://ip:port/report/device/cards/{id}
	@ApiOperation(notes="根据ID删除[三码关系表]信息", value = "根据ID删除[三码关系表]信息", extensions = {
			@Extension(name = "auditLog", properties = {
					@ExtensionProperty(name = "opType", value = "DELETE") }) })
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResultEntity<Integer> deleteDeviceCardById(@PathVariable("id") Integer id) {
		ResultEntity<Integer> result = new ResultEntity<>();
		Integer del = deviceCardService.deleteDeviceCardById(id);
		log.info("delete DeviceCardController delete result[{}]", del);
		return result;
	}

	// 保存一个对象：POST http://ip:port/report/device/cards
	@ApiOperation(notes="保存[三码关系表]信息", value = "保存[三码关系表]信息", extensions = {
			@Extension(name = "auditLog", properties = {
					@ExtensionProperty(name = "opType", value = "INSERT") }) })
	@RequestMapping(method = RequestMethod.POST)
	public DeviceCardVO insertDeviceCard(@Validated @RequestBody DeviceCardVO deviceCardVO)
	{
		DeviceCardDO deviceCardDO = BeanMapper.map(deviceCardVO, DeviceCardDO.class);
		deviceCardService.insertDeviceCard(deviceCardDO);
		
		DeviceCardDTO deviceCardDTO = deviceCardService.getDeviceCardById(deviceCardDO.getId());
		deviceCardVO = BeanMapper.map(deviceCardDTO, DeviceCardVO.class);
		
		log.info("insertDeviceCard DeviceCardController insert result[{}]", deviceCardVO);
		return deviceCardVO;
	}

	// 用于更新某个资源较完整的内容：PUT http://ip:port/report/device/cards
	@ApiOperation(notes="更新[三码关系表]信息", value = "更新[三码关系表]信息", extensions = {
			@Extension(name = "auditLog", properties = {
					@ExtensionProperty(name = "opType", value = "UPDATE") }) })
	@RequestMapping(method = RequestMethod.PUT)
	public DeviceCardVO editDeviceCard(@Validated @RequestBody DeviceCardVO deviceCardVO)
	{
		DeviceCardDO deviceCardDO = BeanMapper.map(deviceCardVO, DeviceCardDO.class);
		deviceCardService.updateDeviceCard(deviceCardDO);
		
		DeviceCardDTO deviceCardDTO = deviceCardService.getDeviceCardById(deviceCardDO.getId());
		deviceCardVO = BeanMapper.map(deviceCardDTO, DeviceCardVO.class);
		
		log.info("edit$DeviceCard DeviceCardController edit result[{}]", deviceCardVO);
		return deviceCardVO ;
	}
	
	// 用于资源的部分内容的更新：PUT http://ip:port/report/device/cards
	@ApiOperation(notes="部分内容的更新[三码关系表]信息", value = "部分内容的更新[三码关系表]信息", extensions = {
			@Extension(name = "auditLog", properties = {
					@ExtensionProperty(name = "opType", value = "UPDATE") }) })
	@RequestMapping(method = RequestMethod.PATCH)
	public DeviceCardVO updateDeviceCard(@RequestBody DeviceCardVO deviceCardVO)
	{
		DeviceCardDO deviceCardDO = BeanMapper.map(deviceCardVO, DeviceCardDO.class);
		deviceCardService.updateDeviceCard(deviceCardDO);
		
		DeviceCardDTO deviceCardDTO = deviceCardService.getDeviceCardById(deviceCardDO.getId());
		deviceCardVO = BeanMapper.map(deviceCardDTO, DeviceCardVO.class);
		
		log.info("updateDeviceCard DeviceCardController update result[{}]", deviceCardVO);
		return deviceCardVO ;
	}



}