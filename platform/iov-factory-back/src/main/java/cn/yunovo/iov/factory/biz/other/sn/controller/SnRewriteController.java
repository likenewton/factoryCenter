package cn.yunovo.iov.factory.biz.other.sn.controller;

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
import cn.yunovo.iov.factory.biz.other.sn.model.SnRewriteDO;
import cn.yunovo.iov.factory.biz.other.sn.model.SnRewriteDTO;
import cn.yunovo.iov.factory.biz.other.sn.model.SnRewriteQuery;
import cn.yunovo.iov.factory.biz.other.sn.model.SnRewriteVO;
import cn.yunovo.iov.factory.biz.other.sn.service.SnRewriteService;
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
@RequestMapping(value = "/other/sn/rewrites")
@Api(value = "[]相关 api")
class SnRewriteController {

	@Autowired
	private SnRewriteService snRewriteService;

	/*
	 * 分页查询访问方式：GET http://ip:port/other/sn/rewrites?page=1&page_size=2
	 * 排除查询访问方式：GET http://ip:port/other/sn/rewrites?name=黄&age=18
	 * 条件查询访问方式：GET http://ip:port/other/sn/rewrites?sort=age,desc(按年龄倒叙)
	 * 条件查询访问方式：GET http://ip:port/other/sn/rewrites?descs/ascs=age(按年龄倒叙)
	 * 指定返回记录的数量：GET http://ip:port/other/sn/rewrites?limit=20
	*/
	@ApiOperation(notes="根据条件获取[推送通道]信息",value = "根据条件获取[推送通道]信息", extensions = @Extension(name = "auditLog", properties = @ExtensionProperty(name = "ignore", value = "true")))
	@RequestMapping(method = RequestMethod.GET)
	public ResultEntity<Object> snRewrites(HttpServletRequest request, SnRewriteQuery snRewriteQuery, Pages pages,
			Limit limit, Order order, Group group, Offset offset) {
		ResultEntity<Object> result = new ResultEntity<Object>();
		Map<String,Condition> conditionMap = new HashMap<String,Condition>();
		conditionMap.put(Condition.PAGES, pages);
		conditionMap.put(Condition.LIMIT, limit);
		conditionMap.put(Condition.ORDER, order);
		conditionMap.put(Condition.OFFSET, offset);
		conditionMap.put(Condition.GROUP, group);
		result.setData(snRewriteService.selectSnRewrite(snRewriteQuery, conditionMap));
		return result;
	}
	
	// 获取一个对象：GET http://ip:port/other/sn/rewrites/{id}
	@ApiOperation(notes="根据ID获取[]信息", value = "根据ID获取[]信息", extensions = {
			@Extension(name = "auditLog", properties = {
					@ExtensionProperty(name = "opType", value = "QUERY") }) })
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public SnRewriteVO getSnRewriteById(@PathVariable("id") Integer id) {
		SnRewriteDTO snRewriteDTO = snRewriteService.getSnRewriteById(id);
		SnRewriteVO snRewriteVO = BeanMapper.map(snRewriteDTO, SnRewriteVO.class);
		return snRewriteVO;
	}

	// 删除一个对象：DELETE http://ip:port/other/sn/rewrites/{id}
	@ApiOperation(notes="根据ID删除[]信息", value = "根据ID删除[]信息", extensions = {
			@Extension(name = "auditLog", properties = {
					@ExtensionProperty(name = "opType", value = "DELETE") }) })
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResultEntity<Integer> deleteSnRewriteById(@PathVariable("id") Integer id) {
		ResultEntity<Integer> result = new ResultEntity<>();
		Integer del = snRewriteService.deleteSnRewriteById(id);
		log.info("delete SnRewriteController delete result[{}]", del);
		return result;
	}

	// 保存一个对象：POST http://ip:port/other/sn/rewrites
	@ApiOperation(notes="保存[]信息", value = "保存[]信息", extensions = {
			@Extension(name = "auditLog", properties = {
					@ExtensionProperty(name = "opType", value = "INSERT") }) })
	@RequestMapping(method = RequestMethod.POST)
	public SnRewriteVO insertSnRewrite(@Validated @RequestBody SnRewriteVO snRewriteVO)
	{
		SnRewriteDO snRewriteDO = BeanMapper.map(snRewriteVO, SnRewriteDO.class);
		snRewriteService.insertSnRewrite(snRewriteDO);
		
		SnRewriteDTO snRewriteDTO = snRewriteService.getSnRewriteById(snRewriteDO.getId());
		snRewriteVO = BeanMapper.map(snRewriteDTO, SnRewriteVO.class);
		
		log.info("insertSnRewrite SnRewriteController insert result[{}]", snRewriteVO);
		return snRewriteVO;
	}

	// 用于更新某个资源较完整的内容：PUT http://ip:port/other/sn/rewrites
	@ApiOperation(notes="更新[]信息", value = "更新[]信息", extensions = {
			@Extension(name = "auditLog", properties = {
					@ExtensionProperty(name = "opType", value = "UPDATE") }) })
	@RequestMapping(method = RequestMethod.PUT)
	public SnRewriteVO editSnRewrite(@Validated @RequestBody SnRewriteVO snRewriteVO)
	{
		SnRewriteDO snRewriteDO = BeanMapper.map(snRewriteVO, SnRewriteDO.class);
		snRewriteService.updateSnRewrite(snRewriteDO);
		
		SnRewriteDTO snRewriteDTO = snRewriteService.getSnRewriteById(snRewriteDO.getId());
		snRewriteVO = BeanMapper.map(snRewriteDTO, SnRewriteVO.class);
		
		log.info("edit$SnRewrite SnRewriteController edit result[{}]", snRewriteVO);
		return snRewriteVO ;
	}
	
	// 用于资源的部分内容的更新：PUT http://ip:port/other/sn/rewrites
	@ApiOperation(notes="部分内容的更新[]信息", value = "部分内容的更新[]信息", extensions = {
			@Extension(name = "auditLog", properties = {
					@ExtensionProperty(name = "opType", value = "UPDATE") }) })
	@RequestMapping(method = RequestMethod.PATCH)
	public SnRewriteVO updateSnRewrite(@RequestBody SnRewriteVO snRewriteVO)
	{
		SnRewriteDO snRewriteDO = BeanMapper.map(snRewriteVO, SnRewriteDO.class);
		snRewriteService.updateSnRewrite(snRewriteDO);
		
		SnRewriteDTO snRewriteDTO = snRewriteService.getSnRewriteById(snRewriteDO.getId());
		snRewriteVO = BeanMapper.map(snRewriteDTO, SnRewriteVO.class);
		
		log.info("updateSnRewrite SnRewriteController update result[{}]", snRewriteVO);
		return snRewriteVO ;
	}	


}