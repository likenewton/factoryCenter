package cn.yunovo.iov.device.zen.biz.report.report.service;

import java.util.Map;

import cn.yunovo.iov.boot.autoconfigure.request.select.Condition;
import cn.yunovo.iov.device.zen.biz.report.report.model.DeviceReportDO;
import cn.yunovo.iov.device.zen.biz.report.report.model.DeviceReportDTO;
import cn.yunovo.iov.device.zen.biz.report.report.model.DeviceReportQuery;

/**
 * Service层：相对具体的业务逻辑服务层。对多个Manager的组合复用 。
 * 
 * @author huangzz
 *
 */
public interface DeviceReportService {

	DeviceReportDTO getDeviceReportById(Integer id);

	Object selectDeviceReport(DeviceReportQuery deviceReportQuery, Map<String, Condition> conditionMap);
	
	Integer insertDeviceReport(DeviceReportDO deviceReportDO);

	Integer deleteDeviceReportById(Integer id);

	Integer updateDeviceReport(DeviceReportDO deviceReportDO);

}
