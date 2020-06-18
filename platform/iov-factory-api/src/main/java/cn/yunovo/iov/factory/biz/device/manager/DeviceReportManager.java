package cn.yunovo.iov.factory.biz.device.manager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.yunovo.iov.factory.biz.device.mapper.DeviceReportMapper;
import cn.yunovo.iov.factory.biz.device.model.DeviceCard;
import cn.yunovo.iov.factory.biz.device.model.DeviceReportDO;
import cn.yunovo.iov.factory.biz.device.model.DeviceReportDTO;
import cn.yunovo.iov.factory.biz.device.model.DeviceReportQuery;
import cn.yunovo.iov.framework.commons.beanutils.bean.BeanMapper;

/**
 * Manager层：通用业务处理层
 * 
 * @author huangzz
 *
 */
@Component
public class DeviceReportManager {

	@Autowired
	private DeviceReportMapper deviceReportMapper;

	public List<DeviceReportDTO> listDeviceReport(DeviceReportQuery deviceReportQuery) {
		List<DeviceReportDO> list = deviceReportMapper.listDeviceReport(deviceReportQuery);
		return BeanMapper.mapList(list, DeviceReportDTO.class);
	}
	
	public List<DeviceReportDTO> listDevice(DeviceReportQuery deviceReportQuery) {
		List<DeviceReportDO> list = deviceReportMapper.listDevice(deviceReportQuery);
		return BeanMapper.mapList(list, DeviceReportDTO.class);
	}

	public Integer saveOrInsertDeviceReport(DeviceReportDO deviceReportDO) {
		return deviceReportMapper.saveOrInsertDeviceReport(deviceReportDO);
	}

	public Integer saveServiceLog(String iccid, Integer optType, String reporter, String desc) {
		return deviceReportMapper.saveServiceLog(iccid, optType, reporter, desc);
	}

	public Integer saveDeviceCard(DeviceCard deviceCard) {
		return deviceReportMapper.saveDeviceCard(deviceCard);
	}
	
	public Integer updateDeviceCard(DeviceCard deviceCard) {
		return deviceReportMapper.updateDeviceCard(deviceCard);
	}

	public DeviceCard getDeviceCard(DeviceCard deviceCard) {
		return deviceReportMapper.getDeviceCard(deviceCard);
	}

	public Integer saveSNChange(String sn, String latestSn, String iccid) {
		return deviceReportMapper.saveSNChange(sn, latestSn, iccid);
	}

}
