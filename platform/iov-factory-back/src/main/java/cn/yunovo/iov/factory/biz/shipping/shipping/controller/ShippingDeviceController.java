package cn.yunovo.iov.factory.biz.shipping.shipping.controller;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import cn.yunovo.iov.boot.autoconfigure.request.select.Export;
import cn.yunovo.iov.boot.autoconfigure.request.select.Group;
import cn.yunovo.iov.boot.autoconfigure.request.select.Limit;
import cn.yunovo.iov.boot.autoconfigure.request.select.Offset;
import cn.yunovo.iov.boot.autoconfigure.request.select.Order;
import cn.yunovo.iov.boot.autoconfigure.request.select.Pages;
import cn.yunovo.iov.factory.biz.report.device.model.DeviceCardVO;
import cn.yunovo.iov.factory.biz.shipping.shipping.model.ShippingDeviceDO;
import cn.yunovo.iov.factory.biz.shipping.shipping.model.ShippingDeviceDTO;
import cn.yunovo.iov.factory.biz.shipping.shipping.model.ShippingDeviceQuery;
import cn.yunovo.iov.factory.biz.shipping.shipping.model.ShippingDeviceVO;
import cn.yunovo.iov.factory.biz.shipping.shipping.model.ShippingListQuery;
import cn.yunovo.iov.factory.biz.shipping.shipping.model.ShippingListVO;
import cn.yunovo.iov.factory.biz.shipping.shipping.service.ShippingDeviceService;
import cn.yunovo.iov.factory.biz.shipping.shipping.service.ShippingListService;
import cn.yunovo.iov.factory.framework.excel.ExcelUtil;
import cn.yunovo.iov.factory.framework.excel.FileDownloader;
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
@RequestMapping(value = "/shipping/devices")
@Api(value = "[发货设备IMEI清单]相关 api")
class ShippingDeviceController {

	@Autowired
	private ShippingDeviceService shippingDeviceService;
	
	@Autowired
	private ShippingListService shippingListService;

	private void export(HttpServletRequest request, HttpServletResponse response, List<ShippingDeviceVO> list) {

		response.setHeader("Set-Cookie", "fileDownload=true; path=/");
		response.reset();
		long beginTime = System.currentTimeMillis();
		FileDownloader fileDownloader = null;
		// 基于流导出
		// OutputStream out = new BufferedOutputStream(response.getOutputStream());
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ExcelUtil.getInstance().exportObj2Excel(out, list, ShippingDeviceVO.class);
		fileDownloader = new FileDownloader(out.toByteArray(), "发货imeis.xlsx");
		fileDownloader.download(request, response);

		long endTime = System.currentTimeMillis();
		log.info("导出数据耗时：【" + (endTime - beginTime) / 1000 + "】秒");
	}

	/*
	 * 分页查询访问方式：GET http://ip:port/shipping/devices?page=1&page_size=2
	 * 排除查询访问方式：GET http://ip:port/shipping/devices?name=黄&age=18
	 * 条件查询访问方式：GET http://ip:port/shipping/devices?sort=age,desc(按年龄倒叙)
	 * 条件查询访问方式：GET http://ip:port/shipping/devices?descs/ascs=age(按年龄倒叙)
	 * 指定返回记录的数量：GET http://ip:port/shipping/devices?limit=20
	*/
	@SuppressWarnings("unchecked")
	@ApiOperation(notes="根据条件获取[推送通道]信息",value = "根据条件获取[推送通道]信息", extensions = @Extension(name = "auditLog", properties = @ExtensionProperty(name = "ignore", value = "true")))
	@RequestMapping(method = RequestMethod.GET)
	public ResultEntity<Object> shippingDevices(HttpServletRequest request,HttpServletResponse response,ShippingDeviceQuery shippingDeviceQuery, ShippingListQuery shippingListQuery, Pages pages,
			Limit limit, Order order, Group group, Offset offset, Export export) {
		ResultEntity<Object> result = new ResultEntity<Object>();
		Map<String,Condition> conditionMap = new HashMap<String,Condition>();
		conditionMap.put(Condition.PAGES, pages);
		conditionMap.put(Condition.LIMIT, limit);
		conditionMap.put(Condition.ORDER, order);
		conditionMap.put(Condition.OFFSET, offset);
		conditionMap.put(Condition.GROUP, group);
		
		if (null != export.getExport()) {
			conditionMap.put(Condition.PAGES, null);
		}else {
			Object obj = shippingDeviceService.selectShippingDevice(shippingDeviceQuery, conditionMap);
			result.setData(obj);
		}
		
		
		if (null != export.getExport()) {
			List<ShippingDeviceVO> data = new ArrayList<ShippingDeviceVO>();
			Object obj = shippingListService.selectShippingList(shippingListQuery, conditionMap);
			List<ShippingListVO> list = (List<ShippingListVO>) obj;
			for(ShippingListVO vo : list) {
				ShippingDeviceQuery query = new ShippingDeviceQuery();
				query.setShippingId(vo.getId());
				Object imeiObj = shippingDeviceService.selectShippingDevice(query, null);
				List<ShippingDeviceVO> imes = (List<ShippingDeviceVO>) imeiObj;
				data.addAll(imes);
			}
			export(request, response, (List<ShippingDeviceVO>) data);
			return null;
		}
		
		return result;
	}
	
	// 获取一个对象：GET http://ip:port/shipping/devices/{id}
	@ApiOperation(notes="根据ID获取[发货设备IMEI清单]信息", value = "根据ID获取[发货设备IMEI清单]信息", extensions = {
			@Extension(name = "auditLog", properties = {
					@ExtensionProperty(name = "opType", value = "QUERY") }) })
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ShippingDeviceVO getShippingDeviceById(@PathVariable("id") Integer id) {
		ShippingDeviceDTO shippingDeviceDTO = shippingDeviceService.getShippingDeviceById(id);
		ShippingDeviceVO shippingDeviceVO = BeanMapper.map(shippingDeviceDTO, ShippingDeviceVO.class);
		return shippingDeviceVO;
	}

	// 删除一个对象：DELETE http://ip:port/shipping/devices/{id}
	@ApiOperation(notes="根据ID删除[发货设备IMEI清单]信息", value = "根据ID删除[发货设备IMEI清单]信息", extensions = {
			@Extension(name = "auditLog", properties = {
					@ExtensionProperty(name = "opType", value = "DELETE") }) })
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResultEntity<Integer> deleteShippingDeviceById(@PathVariable("id") Integer id) {
		ResultEntity<Integer> result = new ResultEntity<>();
		Integer del = shippingDeviceService.deleteShippingDeviceById(id);
		log.info("delete ShippingDeviceController delete result[{}]", del);
		return result;
	}

	// 保存一个对象：POST http://ip:port/shipping/devices
	@ApiOperation(notes="保存[发货设备IMEI清单]信息", value = "保存[发货设备IMEI清单]信息", extensions = {
			@Extension(name = "auditLog", properties = {
					@ExtensionProperty(name = "opType", value = "INSERT") }) })
	@RequestMapping(method = RequestMethod.POST)
	public ShippingDeviceVO insertShippingDevice(@Validated @RequestBody ShippingDeviceVO shippingDeviceVO)
	{
		ShippingDeviceDO shippingDeviceDO = BeanMapper.map(shippingDeviceVO, ShippingDeviceDO.class);
		shippingDeviceService.insertShippingDevice(shippingDeviceDO);
		
		ShippingDeviceDTO shippingDeviceDTO = shippingDeviceService.getShippingDeviceById(shippingDeviceDO.getId());
		shippingDeviceVO = BeanMapper.map(shippingDeviceDTO, ShippingDeviceVO.class);
		
		log.info("insertShippingDevice ShippingDeviceController insert result[{}]", shippingDeviceVO);
		return shippingDeviceVO;
	}

	// 用于更新某个资源较完整的内容：PUT http://ip:port/shipping/devices
	@ApiOperation(notes="更新[发货设备IMEI清单]信息", value = "更新[发货设备IMEI清单]信息", extensions = {
			@Extension(name = "auditLog", properties = {
					@ExtensionProperty(name = "opType", value = "UPDATE") }) })
	@RequestMapping(method = RequestMethod.PUT)
	public ShippingDeviceVO editShippingDevice(@Validated @RequestBody ShippingDeviceVO shippingDeviceVO)
	{
		ShippingDeviceDO shippingDeviceDO = BeanMapper.map(shippingDeviceVO, ShippingDeviceDO.class);
		shippingDeviceService.updateShippingDevice(shippingDeviceDO);
		
		ShippingDeviceDTO shippingDeviceDTO = shippingDeviceService.getShippingDeviceById(shippingDeviceDO.getId());
		shippingDeviceVO = BeanMapper.map(shippingDeviceDTO, ShippingDeviceVO.class);
		
		log.info("edit$ShippingDevice ShippingDeviceController edit result[{}]", shippingDeviceVO);
		return shippingDeviceVO ;
	}
	
	// 用于资源的部分内容的更新：PUT http://ip:port/shipping/devices
	@ApiOperation(notes="部分内容的更新[发货设备IMEI清单]信息", value = "部分内容的更新[发货设备IMEI清单]信息", extensions = {
			@Extension(name = "auditLog", properties = {
					@ExtensionProperty(name = "opType", value = "UPDATE") }) })
	@RequestMapping(method = RequestMethod.PATCH)
	public ShippingDeviceVO updateShippingDevice(@RequestBody ShippingDeviceVO shippingDeviceVO)
	{
		ShippingDeviceDO shippingDeviceDO = BeanMapper.map(shippingDeviceVO, ShippingDeviceDO.class);
		shippingDeviceService.updateShippingDevice(shippingDeviceDO);
		
		ShippingDeviceDTO shippingDeviceDTO = shippingDeviceService.getShippingDeviceById(shippingDeviceDO.getId());
		shippingDeviceVO = BeanMapper.map(shippingDeviceDTO, ShippingDeviceVO.class);
		
		log.info("updateShippingDevice ShippingDeviceController update result[{}]", shippingDeviceVO);
		return shippingDeviceVO ;
	}	


}