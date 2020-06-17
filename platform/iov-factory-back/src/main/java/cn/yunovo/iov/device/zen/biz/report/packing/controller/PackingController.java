package cn.yunovo.iov.device.zen.biz.report.packing.controller;

import cn.yunovo.iov.device.zen.biz.report.packing.model.PackingDO;
import cn.yunovo.iov.device.zen.biz.report.packing.model.PackingDTO;
import cn.yunovo.iov.device.zen.biz.report.packing.model.PackingQuery;
import cn.yunovo.iov.device.zen.biz.report.packing.model.PackingVO;
import cn.yunovo.iov.device.zen.biz.report.packing.service.PackingService;

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
@RequestMapping(value = "/report/packings")
@Api(value = "[设备生产阶段，上报组装信息到云端]相关 api")
class PackingController {

	@Autowired
	private PackingService packingService;

	/*
	 * 分页查询访问方式：GET http://ip:port/report/packings?page=1&page_size=2
	 * 排除查询访问方式：GET http://ip:port/report/packings?name=黄&age=18
	 * 条件查询访问方式：GET http://ip:port/report/packings?sort=age,desc(按年龄倒叙)
	 * 条件查询访问方式：GET http://ip:port/report/packings?descs/ascs=age(按年龄倒叙)
	 * 指定返回记录的数量：GET http://ip:port/report/packings?limit=20
	*/
	@ApiOperation(notes="根据条件获取[推送通道]信息",value = "根据条件获取[推送通道]信息", extensions = @Extension(name = "auditLog", properties = @ExtensionProperty(name = "ignore", value = "true")))
	@RequestMapping(method = RequestMethod.GET)
	public ResultEntity<Object> packings(HttpServletRequest request, PackingQuery packingQuery, Pages pages,
			Limit limit, Order order, Group group, Offset offset) {
		ResultEntity<Object> result = new ResultEntity<Object>();
		Map<String,Condition> conditionMap = new HashMap<String,Condition>();
		conditionMap.put(Condition.PAGES, pages);
		conditionMap.put(Condition.LIMIT, limit);
		conditionMap.put(Condition.ORDER, order);
		conditionMap.put(Condition.OFFSET, offset);
		conditionMap.put(Condition.GROUP, group);
		result.setData(packingService.selectPacking(packingQuery, conditionMap));
		return result;
	}
	
	// 获取一个对象：GET http://ip:port/report/packings/{id}
	@ApiOperation(notes="根据ID获取[设备生产阶段，上报组装信息到云端]信息", value = "根据ID获取[设备生产阶段，上报组装信息到云端]信息", extensions = {
			@Extension(name = "auditLog", properties = {
					@ExtensionProperty(name = "opType", value = "QUERY") }) })
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public PackingVO getPackingById(@PathVariable("id") Integer id) {
		PackingDTO packingDTO = packingService.getPackingById(id);
		PackingVO packingVO = BeanMapper.map(packingDTO, PackingVO.class);
		return packingVO;
	}

	// 删除一个对象：DELETE http://ip:port/report/packings/{id}
	@ApiOperation(notes="根据ID删除[设备生产阶段，上报组装信息到云端]信息", value = "根据ID删除[设备生产阶段，上报组装信息到云端]信息", extensions = {
			@Extension(name = "auditLog", properties = {
					@ExtensionProperty(name = "opType", value = "DELETE") }) })
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResultEntity<Integer> deletePackingById(@PathVariable("id") Integer id) {
		ResultEntity<Integer> result = new ResultEntity<>();
		Integer del = packingService.deletePackingById(id);
		log.info("delete PackingController delete result[{}]", del);
		return result;
	}

	// 保存一个对象：POST http://ip:port/report/packings
	@ApiOperation(notes="保存[设备生产阶段，上报组装信息到云端]信息", value = "保存[设备生产阶段，上报组装信息到云端]信息", extensions = {
			@Extension(name = "auditLog", properties = {
					@ExtensionProperty(name = "opType", value = "INSERT") }) })
	@RequestMapping(method = RequestMethod.POST)
	public PackingVO insertPacking(@Validated @RequestBody PackingVO packingVO)
	{
		PackingDO packingDO = BeanMapper.map(packingVO, PackingDO.class);
		packingService.insertPacking(packingDO);
		
		PackingDTO packingDTO = packingService.getPackingById(packingDO.getId());
		packingVO = BeanMapper.map(packingDTO, PackingVO.class);
		
		log.info("insertPacking PackingController insert result[{}]", packingVO);
		return packingVO;
	}

	// 用于更新某个资源较完整的内容：PUT http://ip:port/report/packings
	@ApiOperation(notes="更新[设备生产阶段，上报组装信息到云端]信息", value = "更新[设备生产阶段，上报组装信息到云端]信息", extensions = {
			@Extension(name = "auditLog", properties = {
					@ExtensionProperty(name = "opType", value = "UPDATE") }) })
	@RequestMapping(method = RequestMethod.PUT)
	public PackingVO editPacking(@Validated @RequestBody PackingVO packingVO)
	{
		PackingDO packingDO = BeanMapper.map(packingVO, PackingDO.class);
		packingService.updatePacking(packingDO);
		
		PackingDTO packingDTO = packingService.getPackingById(packingDO.getId());
		packingVO = BeanMapper.map(packingDTO, PackingVO.class);
		
		log.info("edit$Packing PackingController edit result[{}]", packingVO);
		return packingVO ;
	}
	
	// 用于资源的部分内容的更新：PUT http://ip:port/report/packings
	@ApiOperation(notes="部分内容的更新[设备生产阶段，上报组装信息到云端]信息", value = "部分内容的更新[设备生产阶段，上报组装信息到云端]信息", extensions = {
			@Extension(name = "auditLog", properties = {
					@ExtensionProperty(name = "opType", value = "UPDATE") }) })
	@RequestMapping(method = RequestMethod.PATCH)
	public PackingVO updatePacking(@RequestBody PackingVO packingVO)
	{
		PackingDO packingDO = BeanMapper.map(packingVO, PackingDO.class);
		packingService.updatePacking(packingDO);
		
		PackingDTO packingDTO = packingService.getPackingById(packingDO.getId());
		packingVO = BeanMapper.map(packingDTO, PackingVO.class);
		
		log.info("updatePacking PackingController update result[{}]", packingVO);
		return packingVO ;
	}	


}