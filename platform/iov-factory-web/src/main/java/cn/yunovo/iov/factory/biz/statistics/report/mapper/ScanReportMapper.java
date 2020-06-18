package cn.yunovo.iov.factory.biz.statistics.report.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import cn.yunovo.iov.boot.autoconfigure.mybatis.GeneralMapper;
import cn.yunovo.iov.boot.autoconfigure.request.select.Condition;
import cn.yunovo.iov.factory.biz.statistics.report.model.ScanReportDO;
import cn.yunovo.iov.factory.biz.statistics.report.model.ScanReportQuery;


/**
 * mapper 包为dao包，
 * 注意：方法入参规则
 * 
 * @author huangzz
 *
 */
@Mapper
public interface ScanReportMapper extends GeneralMapper<ScanReportDO> {

	ScanReportDO getScanReportById(Integer id);
	
	List<ScanReportDO> selectScanReport(@Param("query")ScanReportQuery scanReportQuery, @Param("condition")Map<String, Condition> conditionMap);
	Integer insertScanReport(ScanReportDO scanReportDO);

	Integer updateScanReport(ScanReportDO scanReportDO);

	Integer deleteScanReportById(Integer id);
}
