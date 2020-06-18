package cn.yunovo.iov.factory.biz.report.report.manager;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.yunovo.iov.framework.commons.beanutils.bean.BeanMapper;
import cn.yunovo.iov.boot.autoconfigure.request.select.Condition;
import cn.yunovo.iov.factory.biz.report.report.mapper.DeviceReportMapper;
import cn.yunovo.iov.factory.biz.report.report.model.DeviceReportDO;
import cn.yunovo.iov.factory.biz.report.report.model.DeviceReportDTO;
import cn.yunovo.iov.factory.biz.report.report.model.DeviceReportQuery;
import cn.yunovo.iov.factory.biz.statistics.repetition.model.RepetitionReportDO;
import cn.yunovo.iov.factory.biz.statistics.repetition.model.RepetitionReportDTO;

/**
 * Manager层：通用业务处理层
 * @author huangzz
 *
 */
@Component
public class DeviceReportManager {

	@Autowired
	private DeviceReportMapper deviceReportMapper;

	public DeviceReportDTO getDeviceReportById(Integer id) {
		DeviceReportDO deviceReportDO = deviceReportMapper.getDeviceReportById(id);
		DeviceReportDTO DeviceReportDTO = BeanMapper.map(deviceReportDO, DeviceReportDTO.class);
		return DeviceReportDTO;
	}

	public List<DeviceReportDTO> selectDeviceReport(DeviceReportQuery deviceReportQuery, Map<String, Condition> conditionMap) {
		List<DeviceReportDO> list = deviceReportMapper.selectDeviceReport(deviceReportQuery, conditionMap);
		return BeanMapper.mapList(list, DeviceReportDTO.class);
	}
	public Integer insertDeviceReport(DeviceReportDO deviceReportDO) {
		return deviceReportMapper.insertDeviceReport(deviceReportDO);
	}

	public Integer deleteDeviceReportById(Integer id) {
		return deviceReportMapper.deleteDeviceReportById(id);
	}

	public Integer updateDeviceReport(DeviceReportDO deviceReportDO) {
		return deviceReportMapper.updateDeviceReport(deviceReportDO);
	}
	
	public Integer selectReportTimesByDay(String today) {
		return deviceReportMapper.selectReportTimesByDay(today);
	}
	
	public List<RepetitionReportDTO> selectIccidReportTimes(){
		List<RepetitionReportDO> list = deviceReportMapper.selectIccidReportTimes();
		return BeanMapper.mapList(list, RepetitionReportDTO.class);
	}
	
	public List<RepetitionReportDTO> selectSnReportTimes(){
		List<RepetitionReportDO> list = deviceReportMapper.selectSnReportTimes();
		return BeanMapper.mapList(list, RepetitionReportDTO.class);
	}
	
	public List<RepetitionReportDTO> selectImeiReportTimes(){
		List<RepetitionReportDO> list = deviceReportMapper.selectImeiReportTimes();
		return BeanMapper.mapList(list, RepetitionReportDTO.class);
	}
}