package cn.yunovo.iov.factory.biz.statistics.sumtotal.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import cn.yunovo.iov.boot.autoconfigure.request.select.Group;
import cn.yunovo.iov.boot.autoconfigure.request.select.Limit;
import cn.yunovo.iov.boot.autoconfigure.request.select.Offset;
import cn.yunovo.iov.boot.autoconfigure.request.select.Order;
import cn.yunovo.iov.boot.autoconfigure.request.select.Pages;
import cn.yunovo.iov.factory.biz.statistics.assemble.model.StatisticsAssembleQuery;
import cn.yunovo.iov.factory.biz.statistics.assemble.service.StatisticsAssembleService;
import cn.yunovo.iov.factory.biz.statistics.paster.model.StatisticsPasterQuery;
import cn.yunovo.iov.factory.biz.statistics.paster.service.StatisticsPasterService;
import cn.yunovo.iov.factory.biz.statistics.shipping.model.StatisticsShippingListQuery;
import cn.yunovo.iov.factory.biz.statistics.shipping.service.StatisticsShippingListService;
import cn.yunovo.iov.factory.biz.statistics.sumtotal.model.StatisticsSumTotalDO;
import cn.yunovo.iov.factory.biz.statistics.sumtotal.model.StatisticsSumTotalDTO;
import cn.yunovo.iov.factory.biz.statistics.sumtotal.model.StatisticsSumTotalQuery;
import cn.yunovo.iov.factory.biz.statistics.sumtotal.model.StatisticsSumTotalVO;
import cn.yunovo.iov.factory.biz.statistics.sumtotal.service.StatisticsSumTotalService;
import cn.yunovo.iov.factory.framework.LoginInfoUtil;
import cn.yunovo.iov.framework.commons.beanutils.bean.BeanMapper;
import cn.yunovo.iov.framework.commons.lang.date.DateFormatConstants;
import cn.yunovo.iov.framework.commons.lang.date.DateGeneralUtils;
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
@RequestMapping(value = "/statistics/sum/totals")
@Api(value = "[统计组织上报]相关 api")
class StatisticsSumTotalController {

	@Autowired
	private StatisticsSumTotalService statisticsSumTotalService;
	
	@Autowired
	private StatisticsShippingListService statisticsShippingListService;
	
	@Autowired
	private StatisticsPasterService statisticsPasterService;
	
	@Autowired
	private StatisticsAssembleService statisticsAssembleService;
	
	@Autowired
	private LoginInfoUtil loginInfoUtil;
	

	/*
	 * 分页查询访问方式：GET http://ip:port/statistics/sum/totals?page=1&page_size=2
	 * 排除查询访问方式：GET http://ip:port/statistics/sum/totals?name=黄&age=18
	 * 条件查询访问方式：GET http://ip:port/statistics/sum/totals?sort=age,desc(按年龄倒叙)
	 * 条件查询访问方式：GET http://ip:port/statistics/sum/totals?descs/ascs=age(按年龄倒叙)
	 * 指定返回记录的数量：GET http://ip:port/statistics/sum/totals?limit=20
	*/
	@ApiOperation(notes="根据条件获取[推送通道]信息",value = "根据条件获取[推送通道]信息", extensions = @Extension(name = "auditLog", properties = @ExtensionProperty(name = "ignore", value = "true")))
	@RequestMapping(method = RequestMethod.GET)
	public ResultEntity<Object> statisticsSumTotals(HttpServletRequest request, StatisticsSumTotalQuery statisticsSumTotalQuery, Pages pages,
			Limit limit, Order order, Group group, Offset offset) {
		ResultEntity<Object> result = new ResultEntity<Object>();
		
		// 平台用户
		if(0 == loginInfoUtil.getLoginBaseInfo().getUserType()) {
			Map<String,Condition> conditionMap = new HashMap<String,Condition>();
			conditionMap.put(Condition.PAGES, pages);
			conditionMap.put(Condition.LIMIT, limit);
			conditionMap.put(Condition.ORDER, order);
			conditionMap.put(Condition.OFFSET, offset);
			conditionMap.put(Condition.GROUP, group);
			result.setData(statisticsSumTotalService.selectStatisticsSumTotal(statisticsSumTotalQuery, conditionMap));
			return result;
		}
		
		
		Integer todayNumber = 0;
		Integer sumTotal = 0;
		String reportTimeString = DateGeneralUtils.format(new Date(), DateFormatConstants.yyyy_MM_dd);

		List<StatisticsSumTotalVO> list = new ArrayList<StatisticsSumTotalVO>();
		
		// 统计发货总数
		StatisticsShippingListQuery statisticsShippingListQuery = new StatisticsShippingListQuery();
		statisticsShippingListQuery.setReportTime(reportTimeString);
		todayNumber = statisticsShippingListService.statisticsCurrentDay(statisticsShippingListQuery);

		// 全部发货总数
		statisticsShippingListQuery = new StatisticsShippingListQuery();
		sumTotal = statisticsShippingListService.statisticsCurrentDay(statisticsShippingListQuery);

		StatisticsSumTotalVO shippingSumTotalVO = new StatisticsSumTotalVO();
		shippingSumTotalVO.setTodayNumber(todayNumber==null?0:todayNumber);
		shippingSumTotalVO.setSumTotal(sumTotal==null?0:sumTotal);
		shippingSumTotalVO.setStatisticsType(3);
		list.add(shippingSumTotalVO);
		
		todayNumber = 0;
		sumTotal = 0;

		// 统计今天贴片总数
		StatisticsPasterQuery statisticsPasterQuery = new StatisticsPasterQuery();
		statisticsPasterQuery.setReportTime(reportTimeString);
		todayNumber = statisticsPasterService.statisticsCurrentDay(statisticsPasterQuery);

		// 全部贴片总数
		statisticsPasterQuery = new StatisticsPasterQuery();
		sumTotal = statisticsPasterService.statisticsCurrentDay(statisticsPasterQuery);
		
		StatisticsSumTotalVO pasterSumTotalVO = new StatisticsSumTotalVO();
		pasterSumTotalVO.setTodayNumber(todayNumber==null?0:todayNumber);
		pasterSumTotalVO.setSumTotal(sumTotal==null?0:sumTotal);
		pasterSumTotalVO.setStatisticsType(1);
		list.add(pasterSumTotalVO);
		
		todayNumber = 0;
		sumTotal = 0;

		// 统计今天组装总数
		StatisticsAssembleQuery statisticsAssembleQuery = new StatisticsAssembleQuery();
		statisticsAssembleQuery.setReportTime(reportTimeString);
		todayNumber = statisticsAssembleService.statisticsCurrentDay(statisticsAssembleQuery);

		// 全部组装总数
		statisticsAssembleQuery = new StatisticsAssembleQuery();
		sumTotal = statisticsAssembleService.statisticsCurrentDay(statisticsAssembleQuery);
		
		StatisticsSumTotalVO assembleSumTotalVO = new StatisticsSumTotalVO();
		assembleSumTotalVO.setTodayNumber(todayNumber==null?0:todayNumber);
		assembleSumTotalVO.setSumTotal(sumTotal==null?0:sumTotal);
		assembleSumTotalVO.setStatisticsType(2);
		list.add(assembleSumTotalVO);
		
		
		result.setData(list);
		return result;
	}
	
	// 获取一个对象：GET http://ip:port/statistics/sum/totals/{id}
	@ApiOperation(notes="根据ID获取[统计组织上报]信息", value = "根据ID获取[统计组织上报]信息", extensions = {
			@Extension(name = "auditLog", properties = {
					@ExtensionProperty(name = "opType", value = "QUERY") }) })
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public StatisticsSumTotalVO getStatisticsSumTotalById(@PathVariable("id") Integer id) {
		StatisticsSumTotalDTO statisticsSumTotalDTO = statisticsSumTotalService.getStatisticsSumTotalById(id);
		StatisticsSumTotalVO statisticsSumTotalVO = BeanMapper.map(statisticsSumTotalDTO, StatisticsSumTotalVO.class);
		return statisticsSumTotalVO;
	}

	// 删除一个对象：DELETE http://ip:port/statistics/sum/totals/{id}
	@ApiOperation(notes="根据ID删除[统计组织上报]信息", value = "根据ID删除[统计组织上报]信息", extensions = {
			@Extension(name = "auditLog", properties = {
					@ExtensionProperty(name = "opType", value = "DELETE") }) })
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResultEntity<Integer> deleteStatisticsSumTotalById(@PathVariable("id") Integer id) {
		ResultEntity<Integer> result = new ResultEntity<>();
		Integer del = statisticsSumTotalService.deleteStatisticsSumTotalById(id);
		log.info("delete StatisticsSumTotalController delete result[{}]", del);
		return result;
	}

	// 保存一个对象：POST http://ip:port/statistics/sum/totals
	@ApiOperation(notes="保存[统计组织上报]信息", value = "保存[统计组织上报]信息", extensions = {
			@Extension(name = "auditLog", properties = {
					@ExtensionProperty(name = "opType", value = "INSERT") }) })
	@RequestMapping(method = RequestMethod.POST)
	public StatisticsSumTotalVO insertStatisticsSumTotal(@Validated @RequestBody StatisticsSumTotalVO statisticsSumTotalVO)
	{
		StatisticsSumTotalDO statisticsSumTotalDO = BeanMapper.map(statisticsSumTotalVO, StatisticsSumTotalDO.class);
		statisticsSumTotalService.insertStatisticsSumTotal(statisticsSumTotalDO);
		
		StatisticsSumTotalDTO statisticsSumTotalDTO = statisticsSumTotalService.getStatisticsSumTotalById(statisticsSumTotalDO.getId());
		statisticsSumTotalVO = BeanMapper.map(statisticsSumTotalDTO, StatisticsSumTotalVO.class);
		
		log.info("insertStatisticsSumTotal StatisticsSumTotalController insert result[{}]", statisticsSumTotalVO);
		return statisticsSumTotalVO;
	}

	// 用于更新某个资源较完整的内容：PUT http://ip:port/statistics/sum/totals
	@ApiOperation(notes="更新[统计组织上报]信息", value = "更新[统计组织上报]信息", extensions = {
			@Extension(name = "auditLog", properties = {
					@ExtensionProperty(name = "opType", value = "UPDATE") }) })
	@RequestMapping(method = RequestMethod.PUT)
	public StatisticsSumTotalVO editStatisticsSumTotal(@Validated @RequestBody StatisticsSumTotalVO statisticsSumTotalVO)
	{
		StatisticsSumTotalDO statisticsSumTotalDO = BeanMapper.map(statisticsSumTotalVO, StatisticsSumTotalDO.class);
		statisticsSumTotalService.updateStatisticsSumTotal(statisticsSumTotalDO);
		
		StatisticsSumTotalDTO statisticsSumTotalDTO = statisticsSumTotalService.getStatisticsSumTotalById(statisticsSumTotalDO.getId());
		statisticsSumTotalVO = BeanMapper.map(statisticsSumTotalDTO, StatisticsSumTotalVO.class);
		
		log.info("edit$StatisticsSumTotal StatisticsSumTotalController edit result[{}]", statisticsSumTotalVO);
		return statisticsSumTotalVO ;
	}
	
	// 用于资源的部分内容的更新：PUT http://ip:port/statistics/sum/totals
	@ApiOperation(notes="部分内容的更新[统计组织上报]信息", value = "部分内容的更新[统计组织上报]信息", extensions = {
			@Extension(name = "auditLog", properties = {
					@ExtensionProperty(name = "opType", value = "UPDATE") }) })
	@RequestMapping(method = RequestMethod.PATCH)
	public StatisticsSumTotalVO updateStatisticsSumTotal(@RequestBody StatisticsSumTotalVO statisticsSumTotalVO)
	{
		StatisticsSumTotalDO statisticsSumTotalDO = BeanMapper.map(statisticsSumTotalVO, StatisticsSumTotalDO.class);
		statisticsSumTotalService.updateStatisticsSumTotal(statisticsSumTotalDO);
		
		StatisticsSumTotalDTO statisticsSumTotalDTO = statisticsSumTotalService.getStatisticsSumTotalById(statisticsSumTotalDO.getId());
		statisticsSumTotalVO = BeanMapper.map(statisticsSumTotalDTO, StatisticsSumTotalVO.class);
		
		log.info("updateStatisticsSumTotal StatisticsSumTotalController update result[{}]", statisticsSumTotalVO);
		return statisticsSumTotalVO ;
	}	


}