package cn.yunovo.iov.device.zen.biz.statistics.report.service;

import java.util.Map;

import cn.yunovo.iov.boot.autoconfigure.request.select.Condition;
import cn.yunovo.iov.device.zen.biz.statistics.report.model.ScanReportDO;
import cn.yunovo.iov.device.zen.biz.statistics.report.model.ScanReportDTO;
import cn.yunovo.iov.device.zen.biz.statistics.report.model.ScanReportQuery;

/**
 * Service层：相对具体的业务逻辑服务层。对多个Manager的组合复用 。
 * 
 * @author huangzz
 *
 */
public interface ScanReportService {

	ScanReportDTO getScanReportById(Integer id);

	Object selectScanReport(ScanReportQuery scanReportQuery, Map<String, Condition> conditionMap);
	
	Integer insertScanReport(ScanReportDO scanReportDO);

	Integer deleteScanReportById(Integer id);

	Integer updateScanReport(ScanReportDO scanReportDO);

}
