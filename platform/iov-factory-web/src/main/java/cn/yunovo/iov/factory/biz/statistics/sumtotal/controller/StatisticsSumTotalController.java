package cn.yunovo.iov.factory.biz.statistics.sumtotal.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.ore.framework.web.api.ResultEntity;

import cn.yunovo.iov.factory.biz.statistics.assemble.model.StatisticsAssembleQuery;
import cn.yunovo.iov.factory.biz.statistics.assemble.service.StatisticsAssembleService;
import cn.yunovo.iov.factory.biz.statistics.paster.model.StatisticsPasterQuery;
import cn.yunovo.iov.factory.biz.statistics.paster.service.StatisticsPasterService;
import cn.yunovo.iov.factory.biz.statistics.shipping.model.StatisticsShippingQuery;
import cn.yunovo.iov.factory.biz.statistics.shipping.service.StatisticsShippingService;
import cn.yunovo.iov.factory.biz.statistics.sumtotal.model.StatisticsSumTotalVO;
import cn.yunovo.iov.framework.commons.lang.date.DateFormatConstants;
import cn.yunovo.iov.framework.commons.lang.date.DateGeneralUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Extension;
import io.swagger.annotations.ExtensionProperty;


/**
 * Web层(展示层/显示层)：主要是对访问控制进行转发，各类基本参数校验，或者不复用的业务简单处理等。
 * 
 * @author huangzz
 *
 */
@Scope("prototype")
@RestController
@RequestMapping(value = "/statistics/sum/totals")
@Api(value = "[统计组织上报]相关 api")
class StatisticsSumTotalController {

	@Autowired
	private StatisticsShippingService statisticsShippingService;
	
	@Autowired
	private StatisticsPasterService statisticsPasterService;
	
	@Autowired
	private StatisticsAssembleService statisticsAssembleService;

	/*
	 * 分页查询访问方式：GET http://ip:port/statistics/sum/totals?page=1&page_size=2
	 * 排除查询访问方式：GET http://ip:port/statistics/sum/totals?name=黄&age=18
	 * 条件查询访问方式：GET http://ip:port/statistics/sum/totals?sort=age,desc(按年龄倒叙)
	 * 条件查询访问方式：GET http://ip:port/statistics/sum/totals?descs/ascs=age(按年龄倒叙)
	 * 指定返回记录的数量：GET http://ip:port/statistics/sum/totals?limit=20
	*/
	@ApiOperation(notes="根据条件获取[推送通道]信息",value = "根据条件获取[推送通道]信息", extensions = @Extension(name = "auditLog", properties = @ExtensionProperty(name = "ignore", value = "true")))
	@RequestMapping(method = RequestMethod.GET)
	public ResultEntity<Object> statisticsSumTotals() {
		ResultEntity<Object> result = new ResultEntity<Object>();
		
		Integer todayNumber = 0;
		Integer sumTotal = 0;
		String reportTimeString = DateGeneralUtils.format(new Date(), DateFormatConstants.yyyy_MM_dd);

		List<StatisticsSumTotalVO> list = new ArrayList<StatisticsSumTotalVO>();
		
		// 统计发货总数
		StatisticsShippingQuery statisticsShippingQuery = new StatisticsShippingQuery();
		statisticsShippingQuery.setCreateTime(reportTimeString);
		todayNumber = statisticsShippingService.statisticsCurrentDay(statisticsShippingQuery);

		// 全部发货总数
		statisticsShippingQuery = new StatisticsShippingQuery();
		sumTotal = statisticsShippingService.statisticsCurrentDay(statisticsShippingQuery);

		StatisticsSumTotalVO shippingSumTotalVO = new StatisticsSumTotalVO();
		shippingSumTotalVO.setTodayNumber(todayNumber == null ? 0 : todayNumber);
		shippingSumTotalVO.setSumTotal(sumTotal == null ? 0 : sumTotal);
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
		pasterSumTotalVO.setTodayNumber(todayNumber == null ? 0 : todayNumber);
		pasterSumTotalVO.setSumTotal(sumTotal == null ? 0 : sumTotal);
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
		assembleSumTotalVO.setTodayNumber(todayNumber == null ? 0 : todayNumber);
		assembleSumTotalVO.setSumTotal(sumTotal == null ? 0 : sumTotal);
		assembleSumTotalVO.setStatisticsType(2);
		list.add(assembleSumTotalVO);
		
		
		result.setData(list);
		return result;
	}
	
	

}

