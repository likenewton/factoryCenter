package cn.yunovo.iov.device.zen.biz.statistics.report.manager;

import java.util.List;
import java.util.Date;
import java.util.Map;

import cn.yunovo.iov.device.zen.biz.statistics.report.mapper.ScanReportMapper;
import cn.yunovo.iov.device.zen.biz.statistics.report.model.ScanReportDO;
import cn.yunovo.iov.device.zen.biz.statistics.report.model.ScanReportDTO;
import cn.yunovo.iov.device.zen.biz.statistics.report.model.ScanReportQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.yunovo.iov.framework.commons.beanutils.bean.BeanMapper;
import cn.yunovo.iov.boot.autoconfigure.request.select.Condition;

/**
 * Manager层：通用业务处理层
 * @author huangzz
 *
 */
@Component
public class ScanReportManager {

	@Autowired
	private ScanReportMapper scanReportMapper;

	public ScanReportDTO getScanReportById(Integer id) {
		ScanReportDO scanReportDO = scanReportMapper.getScanReportById(id);
		ScanReportDTO ScanReportDTO = BeanMapper.map(scanReportDO, ScanReportDTO.class);
		return ScanReportDTO;
	}

	public List<ScanReportDTO> selectScanReport(ScanReportQuery scanReportQuery, Map<String, Condition> conditionMap) {
		List<ScanReportDO> list = scanReportMapper.selectScanReport(scanReportQuery, conditionMap);
		return BeanMapper.mapList(list, ScanReportDTO.class);
	}
	public Integer insertScanReport(ScanReportDO scanReportDO) {
		scanReportDO.setCreateTime(new Date());
		scanReportDO.setUpdateTime(new Date());
		return scanReportMapper.insertScanReport(scanReportDO);
	}

	public Integer deleteScanReportById(Integer id) {
		return scanReportMapper.deleteScanReportById(id);
	}

	public Integer updateScanReport(ScanReportDO scanReportDO) {
		scanReportDO.setUpdateTime(new Date());
		return scanReportMapper.updateScanReport(scanReportDO);
	}
}