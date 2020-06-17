package cn.yunovo.iov.factory.biz.dac.flogistics.controller;

import cn.yunovo.iov.factory.biz.dac.flogistics.model.FlogisticsResourceDO;
import cn.yunovo.iov.factory.biz.dac.flogistics.model.FlogisticsResourceDTO;
import cn.yunovo.iov.factory.biz.dac.flogistics.model.FlogisticsResourceQuery;
import cn.yunovo.iov.factory.biz.dac.flogistics.model.FlogisticsResourceVO;
import cn.yunovo.iov.factory.biz.dac.flogistics.service.FlogisticsResourceService;

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
@RequestMapping(value = "/dac/flogistics/resources")
@Api(value = "[物流用户数据权限：资源，主体，规则关系表]相关 api")
class FlogisticsResourceController {

	@Autowired
	private FlogisticsResourceService flogisticsResourceService;

	/*
	 * 分页查询访问方式：GET http://ip:port/dac/flogistics/resources?page=1&page_size=2
	 * 排除查询访问方式：GET http://ip:port/dac/flogistics/resources?name=黄&age=18
	 * 条件查询访问方式：GET http://ip:port/dac/flogistics/resources?sort=age,desc(按年龄倒叙)
	 * 条件查询访问方式：GET http://ip:port/dac/flogistics/resources?descs/ascs=age(按年龄倒叙)
	 * 指定返回记录的数量：GET http://ip:port/dac/flogistics/resources?limit=20
	*/
	@ApiOperation(notes="根据条件获取[推送通道]信息",value = "根据条件获取[推送通道]信息", extensions = @Extension(name = "auditLog", properties = @ExtensionProperty(name = "ignore", value = "true")))
	@RequestMapping(method = RequestMethod.GET)
	public ResultEntity<Object> flogisticsResources(HttpServletRequest request, FlogisticsResourceQuery flogisticsResourceQuery, Pages pages,
			Limit limit, Order order, Group group, Offset offset) {
		ResultEntity<Object> result = new ResultEntity<Object>();
		Map<String,Condition> conditionMap = new HashMap<String,Condition>();
		conditionMap.put(Condition.PAGES, pages);
		conditionMap.put(Condition.LIMIT, limit);
		conditionMap.put(Condition.ORDER, order);
		conditionMap.put(Condition.OFFSET, offset);
		conditionMap.put(Condition.GROUP, group);
		result.setData(flogisticsResourceService.selectFlogisticsResource(flogisticsResourceQuery, conditionMap));
		return result;
	}
	
	// 获取一个对象：GET http://ip:port/dac/flogistics/resources/{id}
	@ApiOperation(notes="根据ID获取[物流用户数据权限：资源，主体，规则关系表]信息", value = "根据ID获取[物流用户数据权限：资源，主体，规则关系表]信息", extensions = {
			@Extension(name = "auditLog", properties = {
					@ExtensionProperty(name = "opType", value = "QUERY") }) })
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public FlogisticsResourceVO getFlogisticsResourceById(@PathVariable("id") Integer id) {
		FlogisticsResourceDTO flogisticsResourceDTO = flogisticsResourceService.getFlogisticsResourceById(id);
		FlogisticsResourceVO flogisticsResourceVO = BeanMapper.map(flogisticsResourceDTO, FlogisticsResourceVO.class);
		return flogisticsResourceVO;
	}

	// 删除一个对象：DELETE http://ip:port/dac/flogistics/resources/{id}
	@ApiOperation(notes="根据ID删除[物流用户数据权限：资源，主体，规则关系表]信息", value = "根据ID删除[物流用户数据权限：资源，主体，规则关系表]信息", extensions = {
			@Extension(name = "auditLog", properties = {
					@ExtensionProperty(name = "opType", value = "DELETE") }) })
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResultEntity<Integer> deleteFlogisticsResourceById(@PathVariable("id") Integer id) {
		ResultEntity<Integer> result = new ResultEntity<>();
		Integer del = flogisticsResourceService.deleteFlogisticsResourceById(id);
		log.info("delete FlogisticsResourceController delete result[{}]", del);
		return result;
	}

	// 保存一个对象：POST http://ip:port/dac/flogistics/resources
	@ApiOperation(notes="保存[物流用户数据权限：资源，主体，规则关系表]信息", value = "保存[物流用户数据权限：资源，主体，规则关系表]信息", extensions = {
			@Extension(name = "auditLog", properties = {
					@ExtensionProperty(name = "opType", value = "INSERT") }) })
	@RequestMapping(method = RequestMethod.POST)
	public FlogisticsResourceVO insertFlogisticsResource(@Validated @RequestBody FlogisticsResourceVO flogisticsResourceVO)
	{
		FlogisticsResourceDO flogisticsResourceDO = BeanMapper.map(flogisticsResourceVO, FlogisticsResourceDO.class);
		flogisticsResourceService.insertFlogisticsResource(flogisticsResourceDO);
		
		FlogisticsResourceDTO flogisticsResourceDTO = flogisticsResourceService.getFlogisticsResourceById(flogisticsResourceDO.getId());
		flogisticsResourceVO = BeanMapper.map(flogisticsResourceDTO, FlogisticsResourceVO.class);
		
		log.info("insertFlogisticsResource FlogisticsResourceController insert result[{}]", flogisticsResourceVO);
		return flogisticsResourceVO;
	}

	// 用于更新某个资源较完整的内容：PUT http://ip:port/dac/flogistics/resources
	@ApiOperation(notes="更新[物流用户数据权限：资源，主体，规则关系表]信息", value = "更新[物流用户数据权限：资源，主体，规则关系表]信息", extensions = {
			@Extension(name = "auditLog", properties = {
					@ExtensionProperty(name = "opType", value = "UPDATE") }) })
	@RequestMapping(method = RequestMethod.PUT)
	public FlogisticsResourceVO editFlogisticsResource(@Validated @RequestBody FlogisticsResourceVO flogisticsResourceVO)
	{
		FlogisticsResourceDO flogisticsResourceDO = BeanMapper.map(flogisticsResourceVO, FlogisticsResourceDO.class);
		flogisticsResourceService.updateFlogisticsResource(flogisticsResourceDO);
		
		FlogisticsResourceDTO flogisticsResourceDTO = flogisticsResourceService.getFlogisticsResourceById(flogisticsResourceDO.getId());
		flogisticsResourceVO = BeanMapper.map(flogisticsResourceDTO, FlogisticsResourceVO.class);
		
		log.info("edit$FlogisticsResource FlogisticsResourceController edit result[{}]", flogisticsResourceVO);
		return flogisticsResourceVO ;
	}
	
	// 用于资源的部分内容的更新：PUT http://ip:port/dac/flogistics/resources
	@ApiOperation(notes="部分内容的更新[物流用户数据权限：资源，主体，规则关系表]信息", value = "部分内容的更新[物流用户数据权限：资源，主体，规则关系表]信息", extensions = {
			@Extension(name = "auditLog", properties = {
					@ExtensionProperty(name = "opType", value = "UPDATE") }) })
	@RequestMapping(method = RequestMethod.PATCH)
	public FlogisticsResourceVO updateFlogisticsResource(@RequestBody FlogisticsResourceVO flogisticsResourceVO)
	{
		FlogisticsResourceDO flogisticsResourceDO = BeanMapper.map(flogisticsResourceVO, FlogisticsResourceDO.class);
		flogisticsResourceService.updateFlogisticsResource(flogisticsResourceDO);
		
		FlogisticsResourceDTO flogisticsResourceDTO = flogisticsResourceService.getFlogisticsResourceById(flogisticsResourceDO.getId());
		flogisticsResourceVO = BeanMapper.map(flogisticsResourceDTO, FlogisticsResourceVO.class);
		
		log.info("updateFlogisticsResource FlogisticsResourceController update result[{}]", flogisticsResourceVO);
		return flogisticsResourceVO ;
	}	


}