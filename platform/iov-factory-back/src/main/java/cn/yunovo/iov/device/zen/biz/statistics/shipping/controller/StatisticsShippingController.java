package cn.yunovo.iov.device.zen.biz.statistics.shipping.controller;

import cn.yunovo.iov.device.zen.biz.statistics.shipping.model.StatisticsShippingDO;
import cn.yunovo.iov.device.zen.biz.statistics.shipping.model.StatisticsShippingDTO;
import cn.yunovo.iov.device.zen.biz.statistics.shipping.model.StatisticsShippingQuery;
import cn.yunovo.iov.device.zen.biz.statistics.shipping.model.StatisticsShippingVO;
import cn.yunovo.iov.device.zen.biz.statistics.shipping.service.StatisticsShippingService;

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
@RequestMapping(value = "/statistics/shippings")
@Api(value = "[统计发货管理]相关 api")
class StatisticsShippingController {

	@Autowired
	private StatisticsShippingService statisticsShippingService;

	/*
	 * 分页查询访问方式：GET http://ip:port/statistics/shippings?page=1&page_size=2
	 * 排除查询访问方式：GET http://ip:port/statistics/shippings?name=黄&age=18
	 * 条件查询访问方式：GET http://ip:port/statistics/shippings?sort=age,desc(按年龄倒叙)
	 * 条件查询访问方式：GET http://ip:port/statistics/shippings?descs/ascs=age(按年龄倒叙)
	 * 指定返回记录的数量：GET http://ip:port/statistics/shippings?limit=20
	*/
	@ApiOperation(notes="根据条件获取[推送通道]信息",value = "根据条件获取[推送通道]信息", extensions = @Extension(name = "auditLog", properties = @ExtensionProperty(name = "ignore", value = "true")))
	@RequestMapping(method = RequestMethod.GET)
	public ResultEntity<Object> statisticsShippings(HttpServletRequest request, StatisticsShippingQuery statisticsShippingQuery, Pages pages,
			Limit limit, Order order, Group group, Offset offset) {
		ResultEntity<Object> result = new ResultEntity<Object>();
		Map<String,Condition> conditionMap = new HashMap<String,Condition>();
		conditionMap.put(Condition.PAGES, pages);
		conditionMap.put(Condition.LIMIT, limit);
		conditionMap.put(Condition.ORDER, order);
		conditionMap.put(Condition.OFFSET, offset);
		conditionMap.put(Condition.GROUP, group);
		result.setData(statisticsShippingService.selectStatisticsShipping(statisticsShippingQuery, conditionMap));
		return result;
	}
	
	// 获取一个对象：GET http://ip:port/statistics/shippings/{id}
	@ApiOperation(notes="根据ID获取[统计发货管理]信息", value = "根据ID获取[统计发货管理]信息", extensions = {
			@Extension(name = "auditLog", properties = {
					@ExtensionProperty(name = "opType", value = "QUERY") }) })
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public StatisticsShippingVO getStatisticsShippingById(@PathVariable("id") Integer id) {
		StatisticsShippingDTO statisticsShippingDTO = statisticsShippingService.getStatisticsShippingById(id);
		StatisticsShippingVO statisticsShippingVO = BeanMapper.map(statisticsShippingDTO, StatisticsShippingVO.class);
		return statisticsShippingVO;
	}

	// 删除一个对象：DELETE http://ip:port/statistics/shippings/{id}
	@ApiOperation(notes="根据ID删除[统计发货管理]信息", value = "根据ID删除[统计发货管理]信息", extensions = {
			@Extension(name = "auditLog", properties = {
					@ExtensionProperty(name = "opType", value = "DELETE") }) })
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResultEntity<Integer> deleteStatisticsShippingById(@PathVariable("id") Integer id) {
		ResultEntity<Integer> result = new ResultEntity<>();
		Integer del = statisticsShippingService.deleteStatisticsShippingById(id);
		log.info("delete StatisticsShippingController delete result[{}]", del);
		return result;
	}

	// 保存一个对象：POST http://ip:port/statistics/shippings
	@ApiOperation(notes="保存[统计发货管理]信息", value = "保存[统计发货管理]信息", extensions = {
			@Extension(name = "auditLog", properties = {
					@ExtensionProperty(name = "opType", value = "INSERT") }) })
	@RequestMapping(method = RequestMethod.POST)
	public StatisticsShippingVO insertStatisticsShipping(@Validated @RequestBody StatisticsShippingVO statisticsShippingVO)
	{
		StatisticsShippingDO statisticsShippingDO = BeanMapper.map(statisticsShippingVO, StatisticsShippingDO.class);
		statisticsShippingService.insertStatisticsShipping(statisticsShippingDO);
		
		StatisticsShippingDTO statisticsShippingDTO = statisticsShippingService.getStatisticsShippingById(statisticsShippingDO.getId());
		statisticsShippingVO = BeanMapper.map(statisticsShippingDTO, StatisticsShippingVO.class);
		
		log.info("insertStatisticsShipping StatisticsShippingController insert result[{}]", statisticsShippingVO);
		return statisticsShippingVO;
	}

	// 用于更新某个资源较完整的内容：PUT http://ip:port/statistics/shippings
	@ApiOperation(notes="更新[统计发货管理]信息", value = "更新[统计发货管理]信息", extensions = {
			@Extension(name = "auditLog", properties = {
					@ExtensionProperty(name = "opType", value = "UPDATE") }) })
	@RequestMapping(method = RequestMethod.PUT)
	public StatisticsShippingVO editStatisticsShipping(@Validated @RequestBody StatisticsShippingVO statisticsShippingVO)
	{
		StatisticsShippingDO statisticsShippingDO = BeanMapper.map(statisticsShippingVO, StatisticsShippingDO.class);
		statisticsShippingService.updateStatisticsShipping(statisticsShippingDO);
		
		StatisticsShippingDTO statisticsShippingDTO = statisticsShippingService.getStatisticsShippingById(statisticsShippingDO.getId());
		statisticsShippingVO = BeanMapper.map(statisticsShippingDTO, StatisticsShippingVO.class);
		
		log.info("edit$StatisticsShipping StatisticsShippingController edit result[{}]", statisticsShippingVO);
		return statisticsShippingVO ;
	}
	
	// 用于资源的部分内容的更新：PUT http://ip:port/statistics/shippings
	@ApiOperation(notes="部分内容的更新[统计发货管理]信息", value = "部分内容的更新[统计发货管理]信息", extensions = {
			@Extension(name = "auditLog", properties = {
					@ExtensionProperty(name = "opType", value = "UPDATE") }) })
	@RequestMapping(method = RequestMethod.PATCH)
	public StatisticsShippingVO updateStatisticsShipping(@RequestBody StatisticsShippingVO statisticsShippingVO)
	{
		StatisticsShippingDO statisticsShippingDO = BeanMapper.map(statisticsShippingVO, StatisticsShippingDO.class);
		statisticsShippingService.updateStatisticsShipping(statisticsShippingDO);
		
		StatisticsShippingDTO statisticsShippingDTO = statisticsShippingService.getStatisticsShippingById(statisticsShippingDO.getId());
		statisticsShippingVO = BeanMapper.map(statisticsShippingDTO, StatisticsShippingVO.class);
		
		log.info("updateStatisticsShipping StatisticsShippingController update result[{}]", statisticsShippingVO);
		return statisticsShippingVO ;
	}	


}