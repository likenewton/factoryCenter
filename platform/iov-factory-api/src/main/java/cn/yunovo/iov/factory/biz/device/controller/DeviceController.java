package cn.yunovo.iov.factory.biz.device.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.ore.framework.web.api.ResultEntity;

import cn.yunovo.iov.factory.biz.Contants;
import cn.yunovo.iov.factory.biz.VendorService;
import cn.yunovo.iov.factory.biz.device.model.DeviceReport;
import cn.yunovo.iov.factory.biz.device.model.DeviceReportDTO;
import cn.yunovo.iov.factory.biz.device.model.DeviceReportQuery;
import cn.yunovo.iov.factory.biz.device.model.SelectDevice;
import cn.yunovo.iov.factory.biz.device.model.SelectDeviceReport;
import cn.yunovo.iov.factory.biz.device.service.DeviceReportService;
import cn.yunovo.iov.factory.biz.software.model.SoftwareInfoDTO;
import cn.yunovo.iov.factory.biz.software.model.SoftwareInfoQuery;
import cn.yunovo.iov.factory.biz.software.service.SoftwareInfoService;
import cn.yunovo.iov.framework.commons.beanutils.bean.BeanMapper;
import cn.yunovo.iov.framework.commons.lang.date.DateFormatConstants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@SuppressWarnings("unused")
@Api(value = "DeviceController 相关 api")
@RestController
@RequestMapping("/report")
public class DeviceController {

	@Autowired
	private DeviceReportService deviceReportService;

	/**
	 * 贴片写号的时候，上报三码信息
	 * 
	 * @param deviceInfo
	 * @param request
	 * @return
	 */
	@ApiOperation("上报三码信息")
	@RequestMapping(value = "/devices", method = RequestMethod.POST)
	public ResultEntity<DeviceReport> devices(@RequestBody DeviceReport deviceInfo, HttpServletRequest request) {
		try {
			return deviceReportService.reportDevice(deviceInfo);
		} catch (Exception e) {
			VendorService.sendDing("上报三码信息发生异常", "输入参数：" + deviceInfo.toString() + "异常信息：" + e.getMessage());
		}
		return null;

	}

	@ApiOperation("根据设备的SN/IMEI，或者卡ICCID查询设备上报信息")
	@RequestMapping(value = "/device/reports", method = RequestMethod.GET)
	public List<SelectDeviceReport> reports(String sn, String imei,String btAddr,String wifiAddr) {

		// 获取数据
		DeviceReportQuery deviceReportQuery = new DeviceReportQuery();
		deviceReportQuery.setSn(sn);
		deviceReportQuery.setImei(imei);
		deviceReportQuery.setBtAddr(btAddr);
		deviceReportQuery.setWifiAddr(wifiAddr);
		List<DeviceReportDTO> deviceReportBOList = deviceReportService.listDeviceReport(deviceReportQuery);
		List<SelectDeviceReport> deviceReportVOList = BeanMapper.mapList(deviceReportBOList, SelectDeviceReport.class);
		return deviceReportVOList;
	}

	@ApiOperation("根据设备的SN/IMEI，或者卡ICCID查询设备上报信息")
	@RequestMapping(value = "/devices", method = RequestMethod.GET)
	public List<SelectDevice> devices(String sn, String imei) {

		// 获取数据
		DeviceReportQuery deviceReportQuery = new DeviceReportQuery();
		deviceReportQuery.setSn(sn);
		deviceReportQuery.setImei(imei);
		List<DeviceReportDTO> deviceReportBOList = deviceReportService.listDevice(deviceReportQuery);
		List<SelectDevice> deviceReportVOList = BeanMapper.mapList(deviceReportBOList, SelectDevice.class);
		return deviceReportVOList;
	}
}
