package cn.yunovo.iov.factory.biz.device.service;

import java.util.List;

import com.github.ore.framework.web.api.ResultEntity;

import cn.yunovo.iov.factory.biz.device.model.DeviceReport;
import cn.yunovo.iov.factory.biz.device.model.DeviceReportDTO;
import cn.yunovo.iov.factory.biz.device.model.DeviceReportQuery;

/**
 * Service层：相对具体的业务逻辑服务层。对多个Manager的组合复用 。
 * 
 * @author huangzz
 *
 */
public interface DeviceReportService {

	List<DeviceReportDTO> listDeviceReport(DeviceReportQuery deviceReportQuery);
	
	List<DeviceReportDTO> listDevice(DeviceReportQuery deviceReportQuery);

	ResultEntity<DeviceReport> reportDevice(DeviceReport deviceReport);
	
}
