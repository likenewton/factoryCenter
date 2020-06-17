package cn.yunovo.iov.zen.biz.device.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.ore.framework.web.api.ResultEntity;
import com.google.gson.Gson;

import cn.yunovo.iov.framework.commons.beanutils.bean.BeanMapper;
import cn.yunovo.iov.zen.biz.VendorService;
import cn.yunovo.iov.zen.biz.device.manager.DeviceReportManager;
import cn.yunovo.iov.zen.biz.device.model.DeviceCard;
import cn.yunovo.iov.zen.biz.device.model.DeviceReport;
import cn.yunovo.iov.zen.biz.device.model.DeviceReportDO;
import cn.yunovo.iov.zen.biz.device.model.DeviceReportDTO;
import cn.yunovo.iov.zen.biz.device.model.DeviceReportQuery;
import cn.yunovo.iov.zen.biz.device.service.DeviceReportService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DeviceReportServiceImpl implements DeviceReportService {

	private static final String IMEI_NULL = "000000000000000";

	@Autowired
	private DeviceReportManager deviceReportManager;

	private Integer saveOrInsertDeviceReport(DeviceReportDO deviceReportDO) {
		Date crurrentDateTime = new Date();
		deviceReportDO.setCreateDatetime(crurrentDateTime);
		if ("true".equals(deviceReportDO.getCustomSN()) || "1".equals(deviceReportDO.getCustomSN())) {
			deviceReportDO.setCustomSN("1");
		} else {
			deviceReportDO.setCustomSN("0");
		}
		return deviceReportManager.saveOrInsertDeviceReport(deviceReportDO);
	}

	private void saveDeviceCard(DeviceReport deviceInfo) {

		DeviceCard deviceCard = new DeviceCard();
		if (StringUtils.isNotEmpty(deviceInfo.getImei())) {
			deviceCard.setIccid(deviceInfo.getIccid());
			deviceCard.setImei(deviceInfo.getImei());
			deviceReportManager.updateDeviceCard(deviceCard);
		}

		if (StringUtils.isNotEmpty(deviceInfo.getSn())) {
			deviceCard = new DeviceCard();
			deviceCard.setIccid(deviceInfo.getIccid());
			deviceCard.setSn(deviceInfo.getSn());
			deviceReportManager.updateDeviceCard(deviceCard);
		}

		deviceCard = new DeviceCard();
		deviceCard.setIccid(deviceInfo.getIccid());
		deviceCard.setImei(deviceInfo.getImei());
		deviceCard.setSn(deviceInfo.getSn());
		deviceReportManager.saveDeviceCard(deviceCard);
	}

	@SuppressWarnings("unchecked")
	private void updateDeviceInfo(DeviceReport deviceReport, String customSN) {
		if (IMEI_NULL.equals(deviceReport.getImei())) {
			deviceReport.setImei(null);
		}

		String reslut = null;
		String latestSn = deviceReport.getSn();
		String latestImei = deviceReport.getImei();

		DeviceCard deviceCard = new DeviceCard();
		deviceCard.setIccid(deviceReport.getIccid());
		deviceCard = deviceReportManager.getDeviceCard(deviceCard);
		String sn = null;
		String imei = null;

		if (null != deviceCard) {
			sn = deviceCard.getSn();
			imei = deviceCard.getImei();
		}

		// 判断最新SN
		if (StringUtils.isEmpty(latestSn) && StringUtils.isNotEmpty(sn)) {
			latestSn = sn;
		}

		if (StringUtils.isEmpty(sn) && StringUtils.isNotEmpty(latestSn)) {
			sn = latestSn;
		}

		if (StringUtils.isEmpty(sn) && StringUtils.isEmpty(latestSn)) {
			sn = null;
			latestSn = null;
		}

		if (StringUtils.isEmpty(latestImei) && StringUtils.isNotEmpty(imei)) {
			latestImei = imei;
		}

		// 设备和卡关系创建
		saveDeviceCard(deviceReport);

		if (latestSn.equals(sn)) {
			if (StringUtils.isNotEmpty(latestSn)) {
				reslut = VendorService.infoUpdate(latestSn, deviceReport.getIccid(), latestImei);
				Gson gson = new Gson();
				ResultEntity<String> resultEntity = gson.fromJson(reslut, ResultEntity.class);
				log.info("修改ICCID,{}", resultEntity);
				deviceReportManager.saveServiceLog(deviceReport.getIccid(), 2, deviceReport.getReporter(), reslut);
			}
		} else {
			if (StringUtils.isNotEmpty(latestSn)) {

				// sn 记录SN 变更
				deviceReportManager.saveSNChange(sn, latestSn, deviceReport.getIccid());

				// 根据ICCID,修改设备SN
				reslut = VendorService.snUpdate(latestSn, deviceReport.getIccid(), customSN);
				Gson gson = new Gson();
				ResultEntity<String> resultEntity = gson.fromJson(reslut, ResultEntity.class);
				log.info("修改SN,{}", resultEntity);
				deviceReportManager.saveServiceLog(deviceReport.getIccid(), 0, deviceReport.getReporter(), reslut);
			}
		}
	}

	@Override
	public ResultEntity<DeviceReport> reportDevice(DeviceReport deviceReport) {

		ResultEntity<DeviceReport> re = new ResultEntity<DeviceReport>();
		re.setData(deviceReport);

		if (StringUtils.isBlank(deviceReport.getIccid()) 
				&& StringUtils.isBlank(deviceReport.getSn()) 
				&& StringUtils.isBlank(deviceReport.getImei()) 
				&& StringUtils.isBlank(deviceReport.getWifiAddr()) 
				&& StringUtils.isBlank(deviceReport.getBtAddr())) {
			re.setMsg("SN/IMEI/ICCID/btAddr/wifiAddr: 不能全部字段为空!");
			return re;
		}

		DeviceReportDO deviceReportDO = BeanMapper.map(deviceReport, DeviceReportDO.class);
		Gson gson = new Gson();
		try {
			if (null != deviceReport.getAttached()) {
				deviceReportDO.setAttached(gson.toJson(deviceReport.getAttached()));
			}
		} catch (Exception e) {
			log.error("json error", e);
		}

		// 保存上报记录
		saveOrInsertDeviceReport(deviceReportDO);

		if (StringUtils.isNotEmpty(deviceReport.getIccid())) {
			// 修改设备信息
			updateDeviceInfo(deviceReport, deviceReportDO.getCustomSN());
		} else {
			re.setMsg("ICCID: 不能为空!");
		}

		return re;
	}

	@Override
	public List<DeviceReportDTO> listDeviceReport(DeviceReportQuery deviceReportQuery) {
		return deviceReportManager.listDeviceReport(deviceReportQuery);
	}

	@Override
	public List<DeviceReportDTO> listDevice(DeviceReportQuery deviceReportQuery) {
		return deviceReportManager.listDevice(deviceReportQuery);
	}

}
