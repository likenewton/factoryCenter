package cn.yunovo.iov.device.zen.biz.statistics.area.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
import cn.yunovo.iov.device.zen.biz.statistics.area.model.StatisticsAreaDO;
import cn.yunovo.iov.device.zen.biz.statistics.area.model.StatisticsAreaDTO;
import cn.yunovo.iov.device.zen.biz.statistics.area.model.StatisticsAreaQuery;
import cn.yunovo.iov.device.zen.biz.statistics.area.model.StatisticsAreaVO;
import cn.yunovo.iov.device.zen.biz.statistics.area.service.StatisticsAreaService;
import cn.yunovo.iov.framework.commons.beanutils.bean.BeanMapper;
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
@RequestMapping(value = "/statistics/areas")
@Api(value = "[统计发货管理]相关 api")
class StatisticsAreaController {

	@Autowired
	private StatisticsAreaService statisticsAreaService;

	/*
	 * 分页查询访问方式：GET http://ip:port/statistics/areas?page=1&page_size=2
	 * 排除查询访问方式：GET http://ip:port/statistics/areas?name=黄&age=18
	 * 条件查询访问方式：GET http://ip:port/statistics/areas?sort=age,desc(按年龄倒叙)
	 * 条件查询访问方式：GET http://ip:port/statistics/areas?descs/ascs=age(按年龄倒叙)
	 * 指定返回记录的数量：GET http://ip:port/statistics/areas?limit=20
	*/
	@SuppressWarnings("unchecked")
	@ApiOperation(notes="根据条件获取[推送通道]信息",value = "根据条件获取[推送通道]信息", extensions = @Extension(name = "auditLog", properties = @ExtensionProperty(name = "ignore", value = "true")))
	@RequestMapping(method = RequestMethod.GET)
	public ResultEntity<Object> statisticsAreas(HttpServletRequest request, StatisticsAreaQuery statisticsAreaQuery, Pages pages,
			Limit limit, Order order, Group group, Offset offset) {
		ResultEntity<Object> result = new ResultEntity<Object>();
		Map<String,Condition> conditionMap = new HashMap<String,Condition>();
		conditionMap.put(Condition.PAGES, pages);
		conditionMap.put(Condition.LIMIT, limit);
		conditionMap.put(Condition.ORDER, order);
		conditionMap.put(Condition.OFFSET, offset);
		conditionMap.put(Condition.GROUP, group);
		//result.setData(statisticsAreaService.selectStatisticsArea(statisticsAreaQuery, conditionMap));
		Object obj = statisticsAreaService.selectStatisticsArea(statisticsAreaQuery, conditionMap);
		result.setData(obj);
		
		
		if (null != obj) {
			List<StatisticsAreaVO> list = (List<StatisticsAreaVO>) obj;
			Map<String, StatisticsAreaVO> areMap = new HashMap<String, StatisticsAreaVO>();
			List<StatisticsAreaVO> resultList = new ArrayList<StatisticsAreaVO>();

			for (StatisticsAreaVO vo : list) {
				String key = "";
				// 按省份统计
				if (null == statisticsAreaQuery.getBrandName()) {
					key = vo.getArea();
				} else {
					// 按品牌+省份统计
					key = vo.getArea() + vo.getBrandName();
				}
				
				if (!areMap.containsKey(key)) {

					areMap.put(key, vo);
					resultList.add(vo);

				} else {
					StatisticsAreaVO statisticsAreaVO = areMap.get(key);
					int deviceNumber = statisticsAreaVO.getDeviceNumber() + vo.getDeviceNumber();
					statisticsAreaVO.setDeviceNumber(deviceNumber);
				}
			}
			result.setData(resultList);
		}
		
		return result;
	}
	
	// 获取一个对象：GET http://ip:port/statistics/areas/{id}
	@ApiOperation(notes="根据ID获取[统计发货管理]信息", value = "根据ID获取[统计发货管理]信息", extensions = {
			@Extension(name = "auditLog", properties = {
					@ExtensionProperty(name = "opType", value = "QUERY") }) })
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public StatisticsAreaVO getStatisticsAreaById(@PathVariable("id") Integer id) {
		StatisticsAreaDTO statisticsAreaDTO = statisticsAreaService.getStatisticsAreaById(id);
		StatisticsAreaVO statisticsAreaVO = BeanMapper.map(statisticsAreaDTO, StatisticsAreaVO.class);
		return statisticsAreaVO;
	}

	// 删除一个对象：DELETE http://ip:port/statistics/areas/{id}
	@ApiOperation(notes="根据ID删除[统计发货管理]信息", value = "根据ID删除[统计发货管理]信息", extensions = {
			@Extension(name = "auditLog", properties = {
					@ExtensionProperty(name = "opType", value = "DELETE") }) })
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResultEntity<Integer> deleteStatisticsAreaById(@PathVariable("id") Integer id) {
		ResultEntity<Integer> result = new ResultEntity<>();
		Integer del = statisticsAreaService.deleteStatisticsAreaById(id);
		log.info("delete StatisticsAreaController delete result[{}]", del);
		return result;
	}

	// 保存一个对象：POST http://ip:port/statistics/areas
	@ApiOperation(notes="保存[统计发货管理]信息", value = "保存[统计发货管理]信息", extensions = {
			@Extension(name = "auditLog", properties = {
					@ExtensionProperty(name = "opType", value = "INSERT") }) })
	@RequestMapping(method = RequestMethod.POST)
	public StatisticsAreaVO insertStatisticsArea(@Validated @RequestBody StatisticsAreaVO statisticsAreaVO)
	{
		StatisticsAreaDO statisticsAreaDO = BeanMapper.map(statisticsAreaVO, StatisticsAreaDO.class);
		statisticsAreaService.insertStatisticsArea(statisticsAreaDO);
		
		StatisticsAreaDTO statisticsAreaDTO = statisticsAreaService.getStatisticsAreaById(statisticsAreaDO.getId());
		statisticsAreaVO = BeanMapper.map(statisticsAreaDTO, StatisticsAreaVO.class);
		
		log.info("insertStatisticsArea StatisticsAreaController insert result[{}]", statisticsAreaVO);
		return statisticsAreaVO;
	}

	// 用于更新某个资源较完整的内容：PUT http://ip:port/statistics/areas
	@ApiOperation(notes="更新[统计发货管理]信息", value = "更新[统计发货管理]信息", extensions = {
			@Extension(name = "auditLog", properties = {
					@ExtensionProperty(name = "opType", value = "UPDATE") }) })
	@RequestMapping(method = RequestMethod.PUT)
	public StatisticsAreaVO editStatisticsArea(@Validated @RequestBody StatisticsAreaVO statisticsAreaVO)
	{
		StatisticsAreaDO statisticsAreaDO = BeanMapper.map(statisticsAreaVO, StatisticsAreaDO.class);
		statisticsAreaService.updateStatisticsArea(statisticsAreaDO);
		
		StatisticsAreaDTO statisticsAreaDTO = statisticsAreaService.getStatisticsAreaById(statisticsAreaDO.getId());
		statisticsAreaVO = BeanMapper.map(statisticsAreaDTO, StatisticsAreaVO.class);
		
		log.info("edit$StatisticsArea StatisticsAreaController edit result[{}]", statisticsAreaVO);
		return statisticsAreaVO ;
	}
	
	// 用于资源的部分内容的更新：PUT http://ip:port/statistics/areas
	@ApiOperation(notes="部分内容的更新[统计发货管理]信息", value = "部分内容的更新[统计发货管理]信息", extensions = {
			@Extension(name = "auditLog", properties = {
					@ExtensionProperty(name = "opType", value = "UPDATE") }) })
	@RequestMapping(method = RequestMethod.PATCH)
	public StatisticsAreaVO updateStatisticsArea(@RequestBody StatisticsAreaVO statisticsAreaVO)
	{
		StatisticsAreaDO statisticsAreaDO = BeanMapper.map(statisticsAreaVO, StatisticsAreaDO.class);
		statisticsAreaService.updateStatisticsArea(statisticsAreaDO);
		
		StatisticsAreaDTO statisticsAreaDTO = statisticsAreaService.getStatisticsAreaById(statisticsAreaDO.getId());
		statisticsAreaVO = BeanMapper.map(statisticsAreaDTO, StatisticsAreaVO.class);
		
		log.info("updateStatisticsArea StatisticsAreaController update result[{}]", statisticsAreaVO);
		return statisticsAreaVO ;
	}	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/state", method = RequestMethod.GET)
	public ResultEntity<List<Map<String, Object>>> state(StatisticsAreaQuery statisticsAreaQuery) {


		ResultEntity<List<Map<String, Object>>> result = new ResultEntity<List<Map<String, Object>>>();
		Object obj = statisticsAreaService.selectStatisticsArea(statisticsAreaQuery, null);
		List<StatisticsAreaVO> list = (List<StatisticsAreaVO>) obj;
		Map<String, List<StatisticsAreaVO>> map = new HashMap<String, List<StatisticsAreaVO>>();
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();

		for (StatisticsAreaVO vo : list) {
			if (!map.containsKey(vo.getArea())) {
				List<StatisticsAreaVO> timeList = new ArrayList<StatisticsAreaVO>();
				timeList.add(vo);
				map.put(vo.getArea(), timeList);

			} else {
				List<StatisticsAreaVO> timeList = map.get(vo.getArea());
				timeList.add(vo);
			}
		}

		List<Object> re = map.keySet().stream().collect(Collectors.toList());
		Map<String, String> timeMap = new HashMap<String, String>();
		for (Object o : re) {
			Map<String, Object> reMap = new HashMap<String, Object>();
			String key = o.toString();
			reMap.put("province", key);
			reMap.put("list", map.get(key));
			timeMap.put(key, key);
			resultList.add(reMap);
		}

		result.setData(resultList);
		return result;
	}


}