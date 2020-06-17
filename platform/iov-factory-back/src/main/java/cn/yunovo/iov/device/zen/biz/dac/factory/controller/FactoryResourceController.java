package cn.yunovo.iov.device.zen.biz.dac.factory.controller;

import cn.yunovo.iov.device.zen.biz.dac.factory.model.FactoryResourceDO;
import cn.yunovo.iov.device.zen.biz.dac.factory.model.FactoryResourceDTO;
import cn.yunovo.iov.device.zen.biz.dac.factory.model.FactoryResourceQuery;
import cn.yunovo.iov.device.zen.biz.dac.factory.model.FactoryResourceVO;
import cn.yunovo.iov.device.zen.biz.dac.factory.service.FactoryResourceService;

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
@RequestMapping(value = "/dac/factory/resources")
@Api(value = "[数据权限：资源，主体，规则关系表]相关 api")
class FactoryResourceController {

	@Autowired
	private FactoryResourceService factoryResourceService;

	/*
	 * 分页查询访问方式：GET http://ip:port/dac/factory/resources?page=1&page_size=2
	 * 排除查询访问方式：GET http://ip:port/dac/factory/resources?name=黄&age=18
	 * 条件查询访问方式：GET http://ip:port/dac/factory/resources?sort=age,desc(按年龄倒叙)
	 * 条件查询访问方式：GET http://ip:port/dac/factory/resources?descs/ascs=age(按年龄倒叙)
	 * 指定返回记录的数量：GET http://ip:port/dac/factory/resources?limit=20
	*/
	@ApiOperation(notes="根据条件获取[推送通道]信息",value = "根据条件获取[推送通道]信息", extensions = @Extension(name = "auditLog", properties = @ExtensionProperty(name = "ignore", value = "true")))
	@RequestMapping(method = RequestMethod.GET)
	public ResultEntity<Object> factoryResources(HttpServletRequest request, FactoryResourceQuery factoryResourceQuery, Pages pages,
			Limit limit, Order order, Group group, Offset offset) {
		ResultEntity<Object> result = new ResultEntity<Object>();
		Map<String,Condition> conditionMap = new HashMap<String,Condition>();
		conditionMap.put(Condition.PAGES, pages);
		conditionMap.put(Condition.LIMIT, limit);
		conditionMap.put(Condition.ORDER, order);
		conditionMap.put(Condition.OFFSET, offset);
		conditionMap.put(Condition.GROUP, group);
		result.setData(factoryResourceService.selectFactoryResource(factoryResourceQuery, conditionMap));
		return result;
	}
	
	// 获取一个对象：GET http://ip:port/dac/factory/resources/{id}
	@ApiOperation(notes="根据ID获取[数据权限：资源，主体，规则关系表]信息", value = "根据ID获取[数据权限：资源，主体，规则关系表]信息", extensions = {
			@Extension(name = "auditLog", properties = {
					@ExtensionProperty(name = "opType", value = "QUERY") }) })
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public FactoryResourceVO getFactoryResourceById(@PathVariable("id") Integer id) {
		FactoryResourceDTO factoryResourceDTO = factoryResourceService.getFactoryResourceById(id);
		FactoryResourceVO factoryResourceVO = BeanMapper.map(factoryResourceDTO, FactoryResourceVO.class);
		return factoryResourceVO;
	}

	// 删除一个对象：DELETE http://ip:port/dac/factory/resources/{id}
	@ApiOperation(notes="根据ID删除[数据权限：资源，主体，规则关系表]信息", value = "根据ID删除[数据权限：资源，主体，规则关系表]信息", extensions = {
			@Extension(name = "auditLog", properties = {
					@ExtensionProperty(name = "opType", value = "DELETE") }) })
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResultEntity<Integer> deleteFactoryResourceById(@PathVariable("id") Integer id) {
		ResultEntity<Integer> result = new ResultEntity<>();
		Integer del = factoryResourceService.deleteFactoryResourceById(id);
		log.info("delete FactoryResourceController delete result[{}]", del);
		return result;
	}

	// 保存一个对象：POST http://ip:port/dac/factory/resources
	@ApiOperation(notes="保存[数据权限：资源，主体，规则关系表]信息", value = "保存[数据权限：资源，主体，规则关系表]信息", extensions = {
			@Extension(name = "auditLog", properties = {
					@ExtensionProperty(name = "opType", value = "INSERT") }) })
	@RequestMapping(method = RequestMethod.POST)
	public FactoryResourceVO insertFactoryResource(@Validated @RequestBody FactoryResourceVO factoryResourceVO)
	{
		FactoryResourceDO factoryResourceDO = BeanMapper.map(factoryResourceVO, FactoryResourceDO.class);
		factoryResourceService.insertFactoryResource(factoryResourceDO);
		
		FactoryResourceDTO factoryResourceDTO = factoryResourceService.getFactoryResourceById(factoryResourceDO.getId());
		factoryResourceVO = BeanMapper.map(factoryResourceDTO, FactoryResourceVO.class);
		
		log.info("insertFactoryResource FactoryResourceController insert result[{}]", factoryResourceVO);
		return factoryResourceVO;
	}

	// 用于更新某个资源较完整的内容：PUT http://ip:port/dac/factory/resources
	@ApiOperation(notes="更新[数据权限：资源，主体，规则关系表]信息", value = "更新[数据权限：资源，主体，规则关系表]信息", extensions = {
			@Extension(name = "auditLog", properties = {
					@ExtensionProperty(name = "opType", value = "UPDATE") }) })
	@RequestMapping(method = RequestMethod.PUT)
	public FactoryResourceVO editFactoryResource(@Validated @RequestBody FactoryResourceVO factoryResourceVO)
	{
		FactoryResourceDO factoryResourceDO = BeanMapper.map(factoryResourceVO, FactoryResourceDO.class);
		factoryResourceService.updateFactoryResource(factoryResourceDO);
		
		FactoryResourceDTO factoryResourceDTO = factoryResourceService.getFactoryResourceById(factoryResourceDO.getId());
		factoryResourceVO = BeanMapper.map(factoryResourceDTO, FactoryResourceVO.class);
		
		log.info("edit$FactoryResource FactoryResourceController edit result[{}]", factoryResourceVO);
		return factoryResourceVO ;
	}
	
	// 用于资源的部分内容的更新：PUT http://ip:port/dac/factory/resources
	@ApiOperation(notes="部分内容的更新[数据权限：资源，主体，规则关系表]信息", value = "部分内容的更新[数据权限：资源，主体，规则关系表]信息", extensions = {
			@Extension(name = "auditLog", properties = {
					@ExtensionProperty(name = "opType", value = "UPDATE") }) })
	@RequestMapping(method = RequestMethod.PATCH)
	public FactoryResourceVO updateFactoryResource(@RequestBody FactoryResourceVO factoryResourceVO)
	{
		FactoryResourceDO factoryResourceDO = BeanMapper.map(factoryResourceVO, FactoryResourceDO.class);
		factoryResourceService.updateFactoryResource(factoryResourceDO);
		
		FactoryResourceDTO factoryResourceDTO = factoryResourceService.getFactoryResourceById(factoryResourceDO.getId());
		factoryResourceVO = BeanMapper.map(factoryResourceDTO, FactoryResourceVO.class);
		
		log.info("updateFactoryResource FactoryResourceController update result[{}]", factoryResourceVO);
		return factoryResourceVO ;
	}	


}