package cn.yunovo.iov.factory.biz.device.sn.controller;

import cn.yunovo.iov.factory.biz.device.sn.model.SnNotStoreDO;
import cn.yunovo.iov.factory.biz.device.sn.model.SnNotStoreDTO;
import cn.yunovo.iov.factory.biz.device.sn.model.SnNotStoreQuery;
import cn.yunovo.iov.factory.biz.device.sn.model.SnNotStoreVO;
import cn.yunovo.iov.factory.biz.device.sn.service.SnNotStoreService;

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
@RequestMapping(value = "/device/sn/not/stores")
@Api(value = "[测试上报数据时，SN未入库记录]相关 api")
class SnNotStoreController {

	@Autowired
	private SnNotStoreService snNotStoreService;

	/*
	 * 分页查询访问方式：GET http://ip:port/device/sn/not/stores?page=1&page_size=2
	 * 排除查询访问方式：GET http://ip:port/device/sn/not/stores?name=黄&age=18
	 * 条件查询访问方式：GET http://ip:port/device/sn/not/stores?sort=age,desc(按年龄倒叙)
	 * 条件查询访问方式：GET http://ip:port/device/sn/not/stores?descs/ascs=age(按年龄倒叙)
	 * 指定返回记录的数量：GET http://ip:port/device/sn/not/stores?limit=20
	*/
	@ApiOperation(notes="根据条件获取[推送通道]信息",value = "根据条件获取[推送通道]信息", extensions = @Extension(name = "auditLog", properties = @ExtensionProperty(name = "ignore", value = "true")))
	@RequestMapping(method = RequestMethod.GET)
	public ResultEntity<Object> snNotStores(HttpServletRequest request, SnNotStoreQuery snNotStoreQuery, Pages pages,
			Limit limit, Order order, Group group, Offset offset) {
		ResultEntity<Object> result = new ResultEntity<Object>();
		Map<String,Condition> conditionMap = new HashMap<String,Condition>();
		conditionMap.put(Condition.PAGES, pages);
		conditionMap.put(Condition.LIMIT, limit);
		conditionMap.put(Condition.ORDER, order);
		conditionMap.put(Condition.OFFSET, offset);
		conditionMap.put(Condition.GROUP, group);
		result.setData(snNotStoreService.selectSnNotStore(snNotStoreQuery, conditionMap));
		return result;
	}
	
	// 获取一个对象：GET http://ip:port/device/sn/not/stores/{id}
	@ApiOperation(notes="根据ID获取[测试上报数据时，SN未入库记录]信息", value = "根据ID获取[测试上报数据时，SN未入库记录]信息", extensions = {
			@Extension(name = "auditLog", properties = {
					@ExtensionProperty(name = "opType", value = "QUERY") }) })
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public SnNotStoreVO getSnNotStoreById(@PathVariable("id") Integer id) {
		SnNotStoreDTO snNotStoreDTO = snNotStoreService.getSnNotStoreById(id);
		SnNotStoreVO snNotStoreVO = BeanMapper.map(snNotStoreDTO, SnNotStoreVO.class);
		return snNotStoreVO;
	}

	// 删除一个对象：DELETE http://ip:port/device/sn/not/stores/{id}
	@ApiOperation(notes="根据ID删除[测试上报数据时，SN未入库记录]信息", value = "根据ID删除[测试上报数据时，SN未入库记录]信息", extensions = {
			@Extension(name = "auditLog", properties = {
					@ExtensionProperty(name = "opType", value = "DELETE") }) })
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResultEntity<Integer> deleteSnNotStoreById(@PathVariable("id") Integer id) {
		ResultEntity<Integer> result = new ResultEntity<>();
		Integer del = snNotStoreService.deleteSnNotStoreById(id);
		log.info("delete SnNotStoreController delete result[{}]", del);
		return result;
	}

	// 保存一个对象：POST http://ip:port/device/sn/not/stores
	@ApiOperation(notes="保存[测试上报数据时，SN未入库记录]信息", value = "保存[测试上报数据时，SN未入库记录]信息", extensions = {
			@Extension(name = "auditLog", properties = {
					@ExtensionProperty(name = "opType", value = "INSERT") }) })
	@RequestMapping(method = RequestMethod.POST)
	public SnNotStoreVO insertSnNotStore(@Validated @RequestBody SnNotStoreVO snNotStoreVO)
	{
		SnNotStoreDO snNotStoreDO = BeanMapper.map(snNotStoreVO, SnNotStoreDO.class);
		snNotStoreService.insertSnNotStore(snNotStoreDO);
		
		SnNotStoreDTO snNotStoreDTO = snNotStoreService.getSnNotStoreById(snNotStoreDO.getId());
		snNotStoreVO = BeanMapper.map(snNotStoreDTO, SnNotStoreVO.class);
		
		log.info("insertSnNotStore SnNotStoreController insert result[{}]", snNotStoreVO);
		return snNotStoreVO;
	}

	// 用于更新某个资源较完整的内容：PUT http://ip:port/device/sn/not/stores
	@ApiOperation(notes="更新[测试上报数据时，SN未入库记录]信息", value = "更新[测试上报数据时，SN未入库记录]信息", extensions = {
			@Extension(name = "auditLog", properties = {
					@ExtensionProperty(name = "opType", value = "UPDATE") }) })
	@RequestMapping(method = RequestMethod.PUT)
	public SnNotStoreVO editSnNotStore(@Validated @RequestBody SnNotStoreVO snNotStoreVO)
	{
		SnNotStoreDO snNotStoreDO = BeanMapper.map(snNotStoreVO, SnNotStoreDO.class);
		snNotStoreService.updateSnNotStore(snNotStoreDO);
		
		SnNotStoreDTO snNotStoreDTO = snNotStoreService.getSnNotStoreById(snNotStoreDO.getId());
		snNotStoreVO = BeanMapper.map(snNotStoreDTO, SnNotStoreVO.class);
		
		log.info("edit$SnNotStore SnNotStoreController edit result[{}]", snNotStoreVO);
		return snNotStoreVO ;
	}
	
	// 用于资源的部分内容的更新：PUT http://ip:port/device/sn/not/stores
	@ApiOperation(notes="部分内容的更新[测试上报数据时，SN未入库记录]信息", value = "部分内容的更新[测试上报数据时，SN未入库记录]信息", extensions = {
			@Extension(name = "auditLog", properties = {
					@ExtensionProperty(name = "opType", value = "UPDATE") }) })
	@RequestMapping(method = RequestMethod.PATCH)
	public SnNotStoreVO updateSnNotStore(@RequestBody SnNotStoreVO snNotStoreVO)
	{
		SnNotStoreDO snNotStoreDO = BeanMapper.map(snNotStoreVO, SnNotStoreDO.class);
		snNotStoreService.updateSnNotStore(snNotStoreDO);
		
		SnNotStoreDTO snNotStoreDTO = snNotStoreService.getSnNotStoreById(snNotStoreDO.getId());
		snNotStoreVO = BeanMapper.map(snNotStoreDTO, SnNotStoreVO.class);
		
		log.info("updateSnNotStore SnNotStoreController update result[{}]", snNotStoreVO);
		return snNotStoreVO ;
	}	


}