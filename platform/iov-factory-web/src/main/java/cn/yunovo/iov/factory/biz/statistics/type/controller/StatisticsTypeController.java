package cn.yunovo.iov.factory.biz.statistics.type.controller;

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
import cn.yunovo.iov.factory.biz.statistics.type.model.StatisticsTypeDO;
import cn.yunovo.iov.factory.biz.statistics.type.model.StatisticsTypeDTO;
import cn.yunovo.iov.factory.biz.statistics.type.model.StatisticsTypeQuery;
import cn.yunovo.iov.factory.biz.statistics.type.model.StatisticsTypeVO;
import cn.yunovo.iov.factory.biz.statistics.type.service.StatisticsTypeService;
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
@RequestMapping(value = "/statistics/types")
@Api(value = "[]相关 api")
class StatisticsTypeController {

	@Autowired
	private StatisticsTypeService statisticsTypeService;

	/*
	 * 分页查询访问方式：GET http://ip:port/statistics/types?page=1&page_size=2
	 * 排除查询访问方式：GET http://ip:port/statistics/types?name=黄&age=18
	 * 条件查询访问方式：GET http://ip:port/statistics/types?sort=age,desc(按年龄倒叙)
	 * 条件查询访问方式：GET http://ip:port/statistics/types?descs/ascs=age(按年龄倒叙)
	 * 指定返回记录的数量：GET http://ip:port/statistics/types?limit=20
	*/
	@ApiOperation(notes="根据条件获取[推送通道]信息",value = "根据条件获取[推送通道]信息", extensions = @Extension(name = "auditLog", properties = @ExtensionProperty(name = "ignore", value = "true")))
	@RequestMapping(method = RequestMethod.GET)
	public ResultEntity<Object> statisticsTypes(HttpServletRequest request, StatisticsTypeQuery statisticsTypeQuery, Pages pages,
			Limit limit, Order order, Group group, Offset offset) {
		ResultEntity<Object> result = new ResultEntity<Object>();
		Map<String,Condition> conditionMap = new HashMap<String,Condition>();
		conditionMap.put(Condition.PAGES, pages);
		conditionMap.put(Condition.LIMIT, limit);
		conditionMap.put(Condition.ORDER, order);
		conditionMap.put(Condition.OFFSET, offset);
		conditionMap.put(Condition.GROUP, group);
		result.setData(statisticsTypeService.selectStatisticsType(statisticsTypeQuery, conditionMap));
		return result;
	}
	
	// 获取一个对象：GET http://ip:port/statistics/types/{id}
	@ApiOperation(notes="根据ID获取[]信息", value = "根据ID获取[]信息", extensions = {
			@Extension(name = "auditLog", properties = {
					@ExtensionProperty(name = "opType", value = "QUERY") }) })
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public StatisticsTypeVO getStatisticsTypeById(@PathVariable("id") Integer id) {
		StatisticsTypeDTO statisticsTypeDTO = statisticsTypeService.getStatisticsTypeById(id);
		StatisticsTypeVO statisticsTypeVO = BeanMapper.map(statisticsTypeDTO, StatisticsTypeVO.class);
		return statisticsTypeVO;
	}

	// 删除一个对象：DELETE http://ip:port/statistics/types/{id}
	@ApiOperation(notes="根据ID删除[]信息", value = "根据ID删除[]信息", extensions = {
			@Extension(name = "auditLog", properties = {
					@ExtensionProperty(name = "opType", value = "DELETE") }) })
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResultEntity<Integer> deleteStatisticsTypeById(@PathVariable("id") Integer id) {
		ResultEntity<Integer> result = new ResultEntity<>();
		Integer del = statisticsTypeService.deleteStatisticsTypeById(id);
		log.info("delete StatisticsTypeController delete result[{}]", del);
		return result;
	}

	// 保存一个对象：POST http://ip:port/statistics/types
	@ApiOperation(notes="保存[]信息", value = "保存[]信息", extensions = {
			@Extension(name = "auditLog", properties = {
					@ExtensionProperty(name = "opType", value = "INSERT") }) })
	@RequestMapping(method = RequestMethod.POST)
	public StatisticsTypeVO insertStatisticsType(@Validated @RequestBody StatisticsTypeVO statisticsTypeVO)
	{
		StatisticsTypeDO statisticsTypeDO = BeanMapper.map(statisticsTypeVO, StatisticsTypeDO.class);
		statisticsTypeService.insertStatisticsType(statisticsTypeDO);
		
		StatisticsTypeDTO statisticsTypeDTO = statisticsTypeService.getStatisticsTypeById(statisticsTypeDO.getId());
		statisticsTypeVO = BeanMapper.map(statisticsTypeDTO, StatisticsTypeVO.class);
		
		log.info("insertStatisticsType StatisticsTypeController insert result[{}]", statisticsTypeVO);
		return statisticsTypeVO;
	}

	// 用于更新某个资源较完整的内容：PUT http://ip:port/statistics/types
	@ApiOperation(notes="更新[]信息", value = "更新[]信息", extensions = {
			@Extension(name = "auditLog", properties = {
					@ExtensionProperty(name = "opType", value = "UPDATE") }) })
	@RequestMapping(method = RequestMethod.PUT)
	public StatisticsTypeVO editStatisticsType(@Validated @RequestBody StatisticsTypeVO statisticsTypeVO)
	{
		StatisticsTypeDO statisticsTypeDO = BeanMapper.map(statisticsTypeVO, StatisticsTypeDO.class);
		statisticsTypeService.updateStatisticsType(statisticsTypeDO);
		
		StatisticsTypeDTO statisticsTypeDTO = statisticsTypeService.getStatisticsTypeById(statisticsTypeDO.getId());
		statisticsTypeVO = BeanMapper.map(statisticsTypeDTO, StatisticsTypeVO.class);
		
		log.info("edit$StatisticsType StatisticsTypeController edit result[{}]", statisticsTypeVO);
		return statisticsTypeVO ;
	}
	
	// 用于资源的部分内容的更新：PUT http://ip:port/statistics/types
	@ApiOperation(notes="部分内容的更新[]信息", value = "部分内容的更新[]信息", extensions = {
			@Extension(name = "auditLog", properties = {
					@ExtensionProperty(name = "opType", value = "UPDATE") }) })
	@RequestMapping(method = RequestMethod.PATCH)
	public StatisticsTypeVO updateStatisticsType(@RequestBody StatisticsTypeVO statisticsTypeVO)
	{
		StatisticsTypeDO statisticsTypeDO = BeanMapper.map(statisticsTypeVO, StatisticsTypeDO.class);
		statisticsTypeService.updateStatisticsType(statisticsTypeDO);
		
		StatisticsTypeDTO statisticsTypeDTO = statisticsTypeService.getStatisticsTypeById(statisticsTypeDO.getId());
		statisticsTypeVO = BeanMapper.map(statisticsTypeDTO, StatisticsTypeVO.class);
		
		log.info("updateStatisticsType StatisticsTypeController update result[{}]", statisticsTypeVO);
		return statisticsTypeVO ;
	}	


}