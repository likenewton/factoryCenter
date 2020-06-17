package cn.yunovo.iov.factory.biz.statistics.shipping.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import org.springframework.validation.annotation.Validated;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.ore.framework.web.api.ResultEntity;

import cn.yunovo.iov.framework.commons.beanutils.bean.BeanMapper;
import cn.yunovo.iov.framework.commons.lang.date.DateFormatConstants;
import cn.yunovo.iov.framework.commons.lang.date.DateGeneralUtils;
import cn.yunovo.iov.boot.autoconfigure.request.select.Condition;
import cn.yunovo.iov.boot.autoconfigure.request.select.Group;
import cn.yunovo.iov.boot.autoconfigure.request.select.Limit;
import cn.yunovo.iov.boot.autoconfigure.request.select.Offset;
import cn.yunovo.iov.boot.autoconfigure.request.select.Order;
import cn.yunovo.iov.boot.autoconfigure.request.select.Pages;
import cn.yunovo.iov.factory.biz.statistics.shipping.model.StatisticsShippingListDO;
import cn.yunovo.iov.factory.biz.statistics.shipping.model.StatisticsShippingListDTO;
import cn.yunovo.iov.factory.biz.statistics.shipping.model.StatisticsShippingListQuery;
import cn.yunovo.iov.factory.biz.statistics.shipping.model.StatisticsShippingListVO;
import cn.yunovo.iov.factory.biz.statistics.shipping.service.StatisticsShippingListService;
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
@RequestMapping(value = "/statistics/shipping/lists")
@Api(value = "[统计发货管理]相关 api")
class StatisticsShippingListController {

	@Autowired
	private StatisticsShippingListService statisticsShippingListService;

	/*
	 * 分页查询访问方式：GET http://ip:port/statistics/shipping/lists?page=1&page_size=2
	 * 排除查询访问方式：GET http://ip:port/statistics/shipping/lists?name=黄&age=18
	 * 条件查询访问方式：GET http://ip:port/statistics/shipping/lists?sort=age,desc(按年龄倒叙)
	 * 条件查询访问方式：GET http://ip:port/statistics/shipping/lists?descs/ascs=age(按年龄倒叙)
	 * 指定返回记录的数量：GET http://ip:port/statistics/shipping/lists?limit=20
	*/
	@ApiOperation(notes="根据条件获取[推送通道]信息",value = "根据条件获取[推送通道]信息", extensions = @Extension(name = "auditLog", properties = @ExtensionProperty(name = "ignore", value = "true")))
	@RequestMapping(method = RequestMethod.GET)
	public ResultEntity<Object> statisticsShippingLists(HttpServletRequest request, StatisticsShippingListQuery statisticsShippingListQuery, Pages pages,
			Limit limit, Order order, Group group, Offset offset) {
		ResultEntity<Object> result = new ResultEntity<Object>();
		Map<String,Condition> conditionMap = new HashMap<String,Condition>();
		conditionMap.put(Condition.PAGES, pages);
		conditionMap.put(Condition.LIMIT, limit);
		conditionMap.put(Condition.ORDER, order);
		conditionMap.put(Condition.OFFSET, offset);
		conditionMap.put(Condition.GROUP, group);
		result.setData(statisticsShippingListService.selectStatisticsShippingList(statisticsShippingListQuery, conditionMap));
		return result;
	}
	
	// 获取一个对象：GET http://ip:port/statistics/shipping/lists/{id}
	@ApiOperation(notes="根据ID获取[统计发货管理]信息", value = "根据ID获取[统计发货管理]信息", extensions = {
			@Extension(name = "auditLog", properties = {
					@ExtensionProperty(name = "opType", value = "QUERY") }) })
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public StatisticsShippingListVO getStatisticsShippingListById(@PathVariable("id") Integer id) {
		StatisticsShippingListDTO statisticsShippingListDTO = statisticsShippingListService.getStatisticsShippingListById(id);
		StatisticsShippingListVO statisticsShippingListVO = BeanMapper.map(statisticsShippingListDTO, StatisticsShippingListVO.class);
		return statisticsShippingListVO;
	}

	// 删除一个对象：DELETE http://ip:port/statistics/shipping/lists/{id}
	@ApiOperation(notes="根据ID删除[统计发货管理]信息", value = "根据ID删除[统计发货管理]信息", extensions = {
			@Extension(name = "auditLog", properties = {
					@ExtensionProperty(name = "opType", value = "DELETE") }) })
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResultEntity<Integer> deleteStatisticsShippingListById(@PathVariable("id") Integer id) {
		ResultEntity<Integer> result = new ResultEntity<>();
		Integer del = statisticsShippingListService.deleteStatisticsShippingListById(id);
		log.info("delete StatisticsShippingListController delete result[{}]", del);
		return result;
	}

	// 保存一个对象：POST http://ip:port/statistics/shipping/lists
	@ApiOperation(notes="保存[统计发货管理]信息", value = "保存[统计发货管理]信息", extensions = {
			@Extension(name = "auditLog", properties = {
					@ExtensionProperty(name = "opType", value = "INSERT") }) })
	@RequestMapping(method = RequestMethod.POST)
	public StatisticsShippingListVO insertStatisticsShippingList(@Validated @RequestBody StatisticsShippingListVO statisticsShippingListVO)
	{
		StatisticsShippingListDO statisticsShippingListDO = BeanMapper.map(statisticsShippingListVO, StatisticsShippingListDO.class);
		statisticsShippingListService.insertStatisticsShippingList(statisticsShippingListDO);
		
		StatisticsShippingListDTO statisticsShippingListDTO = statisticsShippingListService.getStatisticsShippingListById(statisticsShippingListDO.getId());
		statisticsShippingListVO = BeanMapper.map(statisticsShippingListDTO, StatisticsShippingListVO.class);
		
		log.info("insertStatisticsShippingList StatisticsShippingListController insert result[{}]", statisticsShippingListVO);
		return statisticsShippingListVO;
	}

	// 用于更新某个资源较完整的内容：PUT http://ip:port/statistics/shipping/lists
	@ApiOperation(notes="更新[统计发货管理]信息", value = "更新[统计发货管理]信息", extensions = {
			@Extension(name = "auditLog", properties = {
					@ExtensionProperty(name = "opType", value = "UPDATE") }) })
	@RequestMapping(method = RequestMethod.PUT)
	public StatisticsShippingListVO editStatisticsShippingList(@Validated @RequestBody StatisticsShippingListVO statisticsShippingListVO)
	{
		StatisticsShippingListDO statisticsShippingListDO = BeanMapper.map(statisticsShippingListVO, StatisticsShippingListDO.class);
		statisticsShippingListService.updateStatisticsShippingList(statisticsShippingListDO);
		
		StatisticsShippingListDTO statisticsShippingListDTO = statisticsShippingListService.getStatisticsShippingListById(statisticsShippingListDO.getId());
		statisticsShippingListVO = BeanMapper.map(statisticsShippingListDTO, StatisticsShippingListVO.class);
		
		log.info("edit$StatisticsShippingList StatisticsShippingListController edit result[{}]", statisticsShippingListVO);
		return statisticsShippingListVO ;
	}
	
	// 用于资源的部分内容的更新：PUT http://ip:port/statistics/shipping/lists
	@ApiOperation(notes="部分内容的更新[统计发货管理]信息", value = "部分内容的更新[统计发货管理]信息", extensions = {
			@Extension(name = "auditLog", properties = {
					@ExtensionProperty(name = "opType", value = "UPDATE") }) })
	@RequestMapping(method = RequestMethod.PATCH)
	public StatisticsShippingListVO updateStatisticsShippingList(@RequestBody StatisticsShippingListVO statisticsShippingListVO)
	{
		StatisticsShippingListDO statisticsShippingListDO = BeanMapper.map(statisticsShippingListVO, StatisticsShippingListDO.class);
		statisticsShippingListService.updateStatisticsShippingList(statisticsShippingListDO);
		
		StatisticsShippingListDTO statisticsShippingListDTO = statisticsShippingListService.getStatisticsShippingListById(statisticsShippingListDO.getId());
		statisticsShippingListVO = BeanMapper.map(statisticsShippingListDTO, StatisticsShippingListVO.class);
		
		log.info("updateStatisticsShippingList StatisticsShippingListController update result[{}]", statisticsShippingListVO);
		return statisticsShippingListVO ;
	}	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/state",method = RequestMethod.GET)
	public ResultEntity<List<Map<String,Object>>> state(StatisticsShippingListQuery statisticsShippingListQuery) {
		
		String selStartTime = null;
		String selEndTime = null;
		Date currentDate = new Date();
		if (StringUtils.isBlank(statisticsShippingListQuery.getSelStartTime())) {
			selStartTime = DateGeneralUtils.format(DateGeneralUtils.addDays(currentDate, -14), DateFormatConstants.yyyy_MM_dd);
			statisticsShippingListQuery.setSelStartTime(selStartTime);
		}

		if (StringUtils.isBlank(statisticsShippingListQuery.getSelEndTime())) {
			selEndTime = DateGeneralUtils.format(currentDate, DateFormatConstants.yyyy_MM_dd);
			statisticsShippingListQuery.setSelEndTime(selEndTime);
		}

		Map<String, String> dayMap = new HashMap<String, String>();
		for (int i = 0; i < 15; i++) {
			String day = DateGeneralUtils.format(DateGeneralUtils.addDays(currentDate, -i), DateFormatConstants.yyyy_MM_dd);
			dayMap.put(day, day);
		}
		ResultEntity<List<Map<String,Object>>> result = new ResultEntity<List<Map<String,Object>>>();
		Object obj = statisticsShippingListService.selectStatisticsShippingList(statisticsShippingListQuery, null);
		List<StatisticsShippingListVO> list = (List<StatisticsShippingListVO>) obj;
		Map<String,List<StatisticsShippingListVO>> map = new HashMap<String,List<StatisticsShippingListVO>>();
		List<Map<String,Object>> resultList = new ArrayList<Map<String,Object>>();
		
		for(StatisticsShippingListVO vo:list) {
			if(!map.containsKey(vo.getReportTime())) {
				List<StatisticsShippingListVO> timeList = new ArrayList<StatisticsShippingListVO>();
				timeList.add(vo);
				map.put(vo.getReportTime(), timeList);
				
			}else {
				List<StatisticsShippingListVO> timeList = map.get(vo.getReportTime());
				timeList.add(vo);
			}
		}
		
        List<Object> re = map.keySet().stream().collect(Collectors.toList());
        Map<String, String> timeMap = new HashMap<String, String>();
        for(Object o:re) {
        	Map<String,Object> reMap = new HashMap<String,Object>();
        	String key = o.toString();
        	reMap.put("time", key);
        	reMap.put("list", map.get(key));
        	timeMap.put(key, key);
        	resultList.add(reMap);
        }
        
        List<String> dayList = dayMap.keySet().stream().collect(Collectors.toList());
		for (String day : dayList) {
			if (!timeMap.containsKey(day)) {
				Map<String, Object> reMap = new HashMap<String, Object>();
				reMap.put("time", day);
				reMap.put("list", null);
				resultList.add(reMap);
			}
		}
		
		result.setData(resultList);
		return result;
	}	


}