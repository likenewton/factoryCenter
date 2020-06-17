package cn.yunovo.iov.device.zen.biz.statistics.report.service.impl;

import java.util.List;
import java.util.Map;

import cn.yunovo.iov.boot.autoconfigure.auditlog.annotation.OpLog;
import cn.yunovo.iov.boot.autoconfigure.auditlog.enums.OpTypeEnum;
import cn.yunovo.iov.boot.autoconfigure.request.SearchCondition;
import cn.yunovo.iov.framework.commons.beanutils.bean.BeanMapper;
import cn.yunovo.iov.framework.web.PageInfo;
import cn.yunovo.iov.boot.autoconfigure.request.select.Condition;
import cn.yunovo.iov.device.zen.biz.statistics.report.manager.ScanReportManager;
import cn.yunovo.iov.device.zen.biz.statistics.report.model.ScanReportVO;
import cn.yunovo.iov.device.zen.biz.statistics.report.model.ScanReportDO;
import cn.yunovo.iov.device.zen.biz.statistics.report.model.ScanReportDTO;
import cn.yunovo.iov.device.zen.biz.statistics.report.model.ScanReportQuery;
import cn.yunovo.iov.device.zen.biz.statistics.report.service.ScanReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;

@Service
public class ScanReportServiceImpl implements ScanReportService {

	@Autowired
	private ScanReportManager scanReportManager;

	@Override
	public ScanReportDTO getScanReportById(Integer id) {
		return scanReportManager.getScanReportById(id);
	}
	
	@Override
	@OpLog( opType=OpTypeEnum.QUERY, opName = "根据条件查询[]信息")
	public Object selectScanReport(ScanReportQuery scanReportQuery, Map<String, Condition> conditionMap) {
		Page<Object> page = null;

		// [分页查询]
		page = SearchCondition.conditionByPages(conditionMap);

		// [排序查询,不能使用OrderByHelper工具类,存在SQL中 limit 和 order by 顺序错误]
		// SearchCondition.conditionByOrder(conditionMap);

		// [分组查询],[偏移量查询]暂不实现支持查询

		// [执行数据库查询]
		List<ScanReportDTO> scanReportBOList = scanReportManager.selectScanReport(scanReportQuery, conditionMap);
		List<ScanReportVO> scanReportVOList = BeanMapper.mapList(scanReportBOList, ScanReportVO.class);

		// [limit 查询]
		if (SearchCondition.conditionBylimit(conditionMap)) {
			return scanReportVOList;
		}

		// 返回分页
		if (null != page) {
			PageInfo<Page<Object>> pInfo = SearchCondition.returnPage(page);
			page.addAll(scanReportVOList);
			return pInfo;
		}

		// 没符合条件查询
		return scanReportVOList;
	}
	@Override
	public Integer insertScanReport(ScanReportDO scanReportDO) {
		return scanReportManager.insertScanReport(scanReportDO);
	}

	@Override
	public Integer deleteScanReportById(Integer id) {
		return scanReportManager.deleteScanReportById(id);
	}

	@Override
	public Integer updateScanReport(ScanReportDO scanReportDO) {
		return scanReportManager.updateScanReport(scanReportDO);
	}
}
