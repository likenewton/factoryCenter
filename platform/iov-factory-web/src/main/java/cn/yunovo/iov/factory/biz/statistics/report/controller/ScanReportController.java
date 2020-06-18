package cn.yunovo.iov.factory.biz.statistics.report.controller;

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
import cn.yunovo.iov.factory.biz.statistics.report.model.ScanReportDO;
import cn.yunovo.iov.factory.biz.statistics.report.model.ScanReportDTO;
import cn.yunovo.iov.factory.biz.statistics.report.model.ScanReportQuery;
import cn.yunovo.iov.factory.biz.statistics.report.model.ScanReportVO;
import cn.yunovo.iov.factory.biz.statistics.report.service.ScanReportService;
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
@RequestMapping(value = "/statistics/scan/reports")
@Api(value = "[]相关 api")
class ScanReportController {

	@Autowired
	private ScanReportService scanReportService;

	/*
	 * 分页查询访问方式：GET http://ip:port/statistics/scan/reports?page=1&page_size=2
	 * 排除查询访问方式：GET http://ip:port/statistics/scan/reports?name=黄&age=18
	 * 条件查询访问方式：GET http://ip:port/statistics/scan/reports?sort=age,desc(按年龄倒叙)
	 * 条件查询访问方式：GET http://ip:port/statistics/scan/reports?descs/ascs=age(按年龄倒叙)
	 * 指定返回记录的数量：GET http://ip:port/statistics/scan/reports?limit=20
	*/
	@ApiOperation(notes="根据条件获取[推送通道]信息",value = "根据条件获取[推送通道]信息", extensions = @Extension(name = "auditLog", properties = @ExtensionProperty(name = "ignore", value = "true")))
	@RequestMapping(method = RequestMethod.GET)
	public ResultEntity<Object> scanReports(HttpServletRequest request, ScanReportQuery scanReportQuery, Pages pages,
			Limit limit, Order order, Group group, Offset offset) {
		ResultEntity<Object> result = new ResultEntity<Object>();
		Map<String,Condition> conditionMap = new HashMap<String,Condition>();
		conditionMap.put(Condition.PAGES, pages);
		conditionMap.put(Condition.LIMIT, limit);
		conditionMap.put(Condition.ORDER, order);
		conditionMap.put(Condition.OFFSET, offset);
		conditionMap.put(Condition.GROUP, group);
		result.setData(scanReportService.selectScanReport(scanReportQuery, conditionMap));
		return result;
	}
	
	// 获取一个对象：GET http://ip:port/statistics/scan/reports/{id}
	@ApiOperation(notes="根据ID获取[]信息", value = "根据ID获取[]信息", extensions = {
			@Extension(name = "auditLog", properties = {
					@ExtensionProperty(name = "opType", value = "QUERY") }) })
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ScanReportVO getScanReportById(@PathVariable("id") Integer id) {
		ScanReportDTO scanReportDTO = scanReportService.getScanReportById(id);
		ScanReportVO scanReportVO = BeanMapper.map(scanReportDTO, ScanReportVO.class);
		return scanReportVO;
	}

	// 删除一个对象：DELETE http://ip:port/statistics/scan/reports/{id}
	@ApiOperation(notes="根据ID删除[]信息", value = "根据ID删除[]信息", extensions = {
			@Extension(name = "auditLog", properties = {
					@ExtensionProperty(name = "opType", value = "DELETE") }) })
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResultEntity<Integer> deleteScanReportById(@PathVariable("id") Integer id) {
		ResultEntity<Integer> result = new ResultEntity<>();
		Integer del = scanReportService.deleteScanReportById(id);
		log.info("delete ScanReportController delete result[{}]", del);
		return result;
	}

	// 保存一个对象：POST http://ip:port/statistics/scan/reports
	@ApiOperation(notes="保存[]信息", value = "保存[]信息", extensions = {
			@Extension(name = "auditLog", properties = {
					@ExtensionProperty(name = "opType", value = "INSERT") }) })
	@RequestMapping(method = RequestMethod.POST)
	public ScanReportVO insertScanReport(@Validated @RequestBody ScanReportVO scanReportVO)
	{
		ScanReportDO scanReportDO = BeanMapper.map(scanReportVO, ScanReportDO.class);
		scanReportService.insertScanReport(scanReportDO);
		
		ScanReportDTO scanReportDTO = scanReportService.getScanReportById(scanReportDO.getId());
		scanReportVO = BeanMapper.map(scanReportDTO, ScanReportVO.class);
		
		log.info("insertScanReport ScanReportController insert result[{}]", scanReportVO);
		return scanReportVO;
	}

	// 用于更新某个资源较完整的内容：PUT http://ip:port/statistics/scan/reports
	@ApiOperation(notes="更新[]信息", value = "更新[]信息", extensions = {
			@Extension(name = "auditLog", properties = {
					@ExtensionProperty(name = "opType", value = "UPDATE") }) })
	@RequestMapping(method = RequestMethod.PUT)
	public ScanReportVO editScanReport(@Validated @RequestBody ScanReportVO scanReportVO)
	{
		ScanReportDO scanReportDO = BeanMapper.map(scanReportVO, ScanReportDO.class);
		scanReportService.updateScanReport(scanReportDO);
		
		ScanReportDTO scanReportDTO = scanReportService.getScanReportById(scanReportDO.getId());
		scanReportVO = BeanMapper.map(scanReportDTO, ScanReportVO.class);
		
		log.info("edit$ScanReport ScanReportController edit result[{}]", scanReportVO);
		return scanReportVO ;
	}
	
	// 用于资源的部分内容的更新：PUT http://ip:port/statistics/scan/reports
	@ApiOperation(notes="部分内容的更新[]信息", value = "部分内容的更新[]信息", extensions = {
			@Extension(name = "auditLog", properties = {
					@ExtensionProperty(name = "opType", value = "UPDATE") }) })
	@RequestMapping(method = RequestMethod.PATCH)
	public ScanReportVO updateScanReport(@RequestBody ScanReportVO scanReportVO)
	{
		ScanReportDO scanReportDO = BeanMapper.map(scanReportVO, ScanReportDO.class);
		scanReportService.updateScanReport(scanReportDO);
		
		ScanReportDTO scanReportDTO = scanReportService.getScanReportById(scanReportDO.getId());
		scanReportVO = BeanMapper.map(scanReportDTO, ScanReportVO.class);
		
		log.info("updateScanReport ScanReportController update result[{}]", scanReportVO);
		return scanReportVO ;
	}	


}