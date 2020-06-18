package cn.yunovo.iov.factory.biz.report.report.controller;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import cn.yunovo.iov.boot.autoconfigure.request.select.Export;
import cn.yunovo.iov.boot.autoconfigure.request.select.Group;
import cn.yunovo.iov.boot.autoconfigure.request.select.Limit;
import cn.yunovo.iov.boot.autoconfigure.request.select.Offset;
import cn.yunovo.iov.boot.autoconfigure.request.select.Order;
import cn.yunovo.iov.boot.autoconfigure.request.select.Pages;
import cn.yunovo.iov.factory.biz.report.report.model.DeviceReportDO;
import cn.yunovo.iov.factory.biz.report.report.model.DeviceReportDTO;
import cn.yunovo.iov.factory.biz.report.report.model.DeviceReportQuery;
import cn.yunovo.iov.factory.biz.report.report.model.DeviceReportVO;
import cn.yunovo.iov.factory.biz.report.report.service.DeviceReportService;
import cn.yunovo.iov.factory.framework.excel.ExcelUtil;
import cn.yunovo.iov.factory.framework.excel.FileDownloader;
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
@RequestMapping(value = "/report/device/reports")
@Api(value = "[设备生产阶段，上报设备信息到云端]相关 api")
class DeviceReportController {

	@Autowired
	private DeviceReportService deviceReportService;

	private void export(HttpServletRequest request, HttpServletResponse response, List<DeviceReportVO> list) {

		response.setHeader("Set-Cookie", "fileDownload=true; path=/");
		response.reset();
		long beginTime = System.currentTimeMillis();
		FileDownloader fileDownloader = null;
		// 基于流导出
		// OutputStream out = new BufferedOutputStream(response.getOutputStream());
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ExcelUtil.getInstance().exportObj2Excel(out, list, DeviceReportVO.class);
		fileDownloader = new FileDownloader(out.toByteArray(), "上报设备信息.xlsx");
		fileDownloader.download(request, response);

		long endTime = System.currentTimeMillis();
		log.info("导出数据耗时：【" + (endTime - beginTime) / 1000 + "】秒");
	}


	/*
	 * 分页查询访问方式：GET http://ip:port/report/device/reports?page=1&page_size=2 排除查询访问方式：GET http://ip:port/report/device/reports?name=黄&age=18 条件查询访问方式：GET http://ip:port/report/device/reports?sort=age,desc(按年龄倒叙) 条件查询访问方式：GET
	 * http://ip:port/report/device/reports?descs/ascs=age(按年龄倒叙) 指定返回记录的数量：GET http://ip:port/report/device/reports?limit=20
	 */
	@SuppressWarnings("unchecked")
	@ApiOperation(notes = "根据条件获取[推送通道]信息", value = "根据条件获取[推送通道]信息", extensions = @Extension(name = "auditLog", properties = @ExtensionProperty(name = "ignore", value = "true")))
	@RequestMapping(method = RequestMethod.GET)
	public ResultEntity<Object> deviceReports(HttpServletRequest request,HttpServletResponse response, DeviceReportQuery deviceReportQuery, Pages pages, Limit limit, Order order, Group group, Offset offset, Export export) {
		ResultEntity<Object> result = new ResultEntity<Object>();
		Map<String, Condition> conditionMap = new HashMap<String, Condition>();
		conditionMap.put(Condition.PAGES, pages);
		conditionMap.put(Condition.LIMIT, limit);
		conditionMap.put(Condition.ORDER, order);
		conditionMap.put(Condition.OFFSET, offset);
		conditionMap.put(Condition.GROUP, group);
		
		
		if (null != export.getExport()) {
			conditionMap.put(Condition.PAGES, null);
		}
		
		Object obj = deviceReportService.selectDeviceReport(deviceReportQuery, conditionMap);
		result.setData(obj);
	
		if (null != export.getExport()) {
			export(request, response, (List<DeviceReportVO>) obj);
			return null;
		}
		
		return result;
	}

	// 获取一个对象：GET http://ip:port/report/device/reports/{id}
	@ApiOperation(notes = "根据ID获取[设备生产阶段，上报设备信息到云端]信息", value = "根据ID获取[设备生产阶段，上报设备信息到云端]信息", extensions = { @Extension(name = "auditLog", properties = { @ExtensionProperty(name = "opType", value = "QUERY") }) })
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public DeviceReportVO getDeviceReportById(@PathVariable("id") Integer id) {
		DeviceReportDTO deviceReportDTO = deviceReportService.getDeviceReportById(id);
		DeviceReportVO deviceReportVO = BeanMapper.map(deviceReportDTO, DeviceReportVO.class);
		return deviceReportVO;
	}

	// 删除一个对象：DELETE http://ip:port/report/device/reports/{id}
	@ApiOperation(notes = "根据ID删除[设备生产阶段，上报设备信息到云端]信息", value = "根据ID删除[设备生产阶段，上报设备信息到云端]信息", extensions = { @Extension(name = "auditLog", properties = { @ExtensionProperty(name = "opType", value = "DELETE") }) })
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResultEntity<Integer> deleteDeviceReportById(@PathVariable("id") Integer id) {
		ResultEntity<Integer> result = new ResultEntity<>();
		Integer del = deviceReportService.deleteDeviceReportById(id);
		log.info("delete DeviceReportController delete result[{}]", del);
		return result;
	}

	// 保存一个对象：POST http://ip:port/report/device/reports
	@ApiOperation(notes = "保存[设备生产阶段，上报设备信息到云端]信息", value = "保存[设备生产阶段，上报设备信息到云端]信息", extensions = { @Extension(name = "auditLog", properties = { @ExtensionProperty(name = "opType", value = "INSERT") }) })
	@RequestMapping(method = RequestMethod.POST)
	public DeviceReportVO insertDeviceReport(@Validated @RequestBody DeviceReportVO deviceReportVO) {
		DeviceReportDO deviceReportDO = BeanMapper.map(deviceReportVO, DeviceReportDO.class);
		deviceReportService.insertDeviceReport(deviceReportDO);

		DeviceReportDTO deviceReportDTO = deviceReportService.getDeviceReportById(deviceReportDO.getId());
		deviceReportVO = BeanMapper.map(deviceReportDTO, DeviceReportVO.class);

		log.info("insertDeviceReport DeviceReportController insert result[{}]", deviceReportVO);
		return deviceReportVO;
	}

	// 用于更新某个资源较完整的内容：PUT http://ip:port/report/device/reports
	@ApiOperation(notes = "更新[设备生产阶段，上报设备信息到云端]信息", value = "更新[设备生产阶段，上报设备信息到云端]信息", extensions = { @Extension(name = "auditLog", properties = { @ExtensionProperty(name = "opType", value = "UPDATE") }) })
	@RequestMapping(method = RequestMethod.PUT)
	public DeviceReportVO editDeviceReport(@Validated @RequestBody DeviceReportVO deviceReportVO) {
		DeviceReportDO deviceReportDO = BeanMapper.map(deviceReportVO, DeviceReportDO.class);
		deviceReportService.updateDeviceReport(deviceReportDO);

		DeviceReportDTO deviceReportDTO = deviceReportService.getDeviceReportById(deviceReportDO.getId());
		deviceReportVO = BeanMapper.map(deviceReportDTO, DeviceReportVO.class);

		log.info("edit$DeviceReport DeviceReportController edit result[{}]", deviceReportVO);
		return deviceReportVO;
	}

	// 用于资源的部分内容的更新：PUT http://ip:port/report/device/reports
	@ApiOperation(notes = "部分内容的更新[设备生产阶段，上报设备信息到云端]信息", value = "部分内容的更新[设备生产阶段，上报设备信息到云端]信息", extensions = { @Extension(name = "auditLog", properties = { @ExtensionProperty(name = "opType", value = "UPDATE") }) })
	@RequestMapping(method = RequestMethod.PATCH)
	public DeviceReportVO updateDeviceReport(@RequestBody DeviceReportVO deviceReportVO) {
		DeviceReportDO deviceReportDO = BeanMapper.map(deviceReportVO, DeviceReportDO.class);
		deviceReportService.updateDeviceReport(deviceReportDO);

		DeviceReportDTO deviceReportDTO = deviceReportService.getDeviceReportById(deviceReportDO.getId());
		deviceReportVO = BeanMapper.map(deviceReportDTO, DeviceReportVO.class);

		log.info("updateDeviceReport DeviceReportController update result[{}]", deviceReportVO);
		return deviceReportVO;
	}

}