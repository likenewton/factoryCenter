package cn.yunovo.iov.factory.biz.report.software.controller;

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
import cn.yunovo.iov.factory.biz.report.software.model.SoftwareDO;
import cn.yunovo.iov.factory.biz.report.software.model.SoftwareDTO;
import cn.yunovo.iov.factory.biz.report.software.model.SoftwareQuery;
import cn.yunovo.iov.factory.biz.report.software.model.SoftwareVO;
import cn.yunovo.iov.factory.biz.report.software.service.SoftwareService;
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
@RequestMapping(value = "/report/softwares")
@Api(value = "[zen 平台，上报设备信息到云端]相关 api")
class SoftwareController {

	@Autowired
	private SoftwareService softwareService;

	/*
	 * 分页查询访问方式：GET http://ip:port/report/softwares?page=1&page_size=2
	 * 排除查询访问方式：GET http://ip:port/report/softwares?name=黄&age=18
	 * 条件查询访问方式：GET http://ip:port/report/softwares?sort=age,desc(按年龄倒叙)
	 * 条件查询访问方式：GET http://ip:port/report/softwares?descs/ascs=age(按年龄倒叙)
	 * 指定返回记录的数量：GET http://ip:port/report/softwares?limit=20
	*/
	@ApiOperation(notes="根据条件获取[推送通道]信息",value = "根据条件获取[推送通道]信息", extensions = @Extension(name = "auditLog", properties = @ExtensionProperty(name = "ignore", value = "true")))
	@RequestMapping(method = RequestMethod.GET)
	public ResultEntity<Object> softwares(HttpServletRequest request, SoftwareQuery softwareQuery, Pages pages,
			Limit limit, Order order, Group group, Offset offset) {
		ResultEntity<Object> result = new ResultEntity<Object>();
		Map<String,Condition> conditionMap = new HashMap<String,Condition>();
		conditionMap.put(Condition.PAGES, pages);
		conditionMap.put(Condition.LIMIT, limit);
		conditionMap.put(Condition.ORDER, order);
		conditionMap.put(Condition.OFFSET, offset);
		conditionMap.put(Condition.GROUP, group);
		result.setData(softwareService.selectSoftware(softwareQuery, conditionMap));
		return result;
	}
	
	// 获取一个对象：GET http://ip:port/report/softwares/{id}
	@ApiOperation(notes="根据ID获取[zen 平台，上报设备信息到云端]信息", value = "根据ID获取[zen 平台，上报设备信息到云端]信息", extensions = {
			@Extension(name = "auditLog", properties = {
					@ExtensionProperty(name = "opType", value = "QUERY") }) })
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public SoftwareVO getSoftwareById(@PathVariable("id") Integer id) {
		SoftwareDTO softwareDTO = softwareService.getSoftwareById(id);
		SoftwareVO softwareVO = BeanMapper.map(softwareDTO, SoftwareVO.class);
		return softwareVO;
	}

	// 删除一个对象：DELETE http://ip:port/report/softwares/{id}
	@ApiOperation(notes="根据ID删除[zen 平台，上报设备信息到云端]信息", value = "根据ID删除[zen 平台，上报设备信息到云端]信息", extensions = {
			@Extension(name = "auditLog", properties = {
					@ExtensionProperty(name = "opType", value = "DELETE") }) })
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResultEntity<Integer> deleteSoftwareById(@PathVariable("id") Integer id) {
		ResultEntity<Integer> result = new ResultEntity<>();
		Integer del = softwareService.deleteSoftwareById(id);
		log.info("delete SoftwareController delete result[{}]", del);
		return result;
	}

	// 保存一个对象：POST http://ip:port/report/softwares
	@ApiOperation(notes="保存[zen 平台，上报设备信息到云端]信息", value = "保存[zen 平台，上报设备信息到云端]信息", extensions = {
			@Extension(name = "auditLog", properties = {
					@ExtensionProperty(name = "opType", value = "INSERT") }) })
	@RequestMapping(method = RequestMethod.POST)
	public SoftwareVO insertSoftware(@Validated @RequestBody SoftwareVO softwareVO)
	{
		SoftwareDO softwareDO = BeanMapper.map(softwareVO, SoftwareDO.class);
		softwareService.insertSoftware(softwareDO);
		
		SoftwareDTO softwareDTO = softwareService.getSoftwareById(softwareDO.getId());
		softwareVO = BeanMapper.map(softwareDTO, SoftwareVO.class);
		
		log.info("insertSoftware SoftwareController insert result[{}]", softwareVO);
		return softwareVO;
	}

	// 用于更新某个资源较完整的内容：PUT http://ip:port/report/softwares
	@ApiOperation(notes="更新[zen 平台，上报设备信息到云端]信息", value = "更新[zen 平台，上报设备信息到云端]信息", extensions = {
			@Extension(name = "auditLog", properties = {
					@ExtensionProperty(name = "opType", value = "UPDATE") }) })
	@RequestMapping(method = RequestMethod.PUT)
	public SoftwareVO editSoftware(@Validated @RequestBody SoftwareVO softwareVO)
	{
		SoftwareDO softwareDO = BeanMapper.map(softwareVO, SoftwareDO.class);
		softwareService.updateSoftware(softwareDO);
		
		SoftwareDTO softwareDTO = softwareService.getSoftwareById(softwareDO.getId());
		softwareVO = BeanMapper.map(softwareDTO, SoftwareVO.class);
		
		log.info("edit$Software SoftwareController edit result[{}]", softwareVO);
		return softwareVO ;
	}
	
	// 用于资源的部分内容的更新：PUT http://ip:port/report/softwares
	@ApiOperation(notes="部分内容的更新[zen 平台，上报设备信息到云端]信息", value = "部分内容的更新[zen 平台，上报设备信息到云端]信息", extensions = {
			@Extension(name = "auditLog", properties = {
					@ExtensionProperty(name = "opType", value = "UPDATE") }) })
	@RequestMapping(method = RequestMethod.PATCH)
	public SoftwareVO updateSoftware(@RequestBody SoftwareVO softwareVO)
	{
		SoftwareDO softwareDO = BeanMapper.map(softwareVO, SoftwareDO.class);
		softwareService.updateSoftware(softwareDO);
		
		SoftwareDTO softwareDTO = softwareService.getSoftwareById(softwareDO.getId());
		softwareVO = BeanMapper.map(softwareDTO, SoftwareVO.class);
		
		log.info("updateSoftware SoftwareController update result[{}]", softwareVO);
		return softwareVO ;
	}	


}