package cn.yunovo.iov.factory.biz.report.report.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import cn.yunovo.iov.boot.autoconfigure.mybatis.GeneralMapper;
import cn.yunovo.iov.boot.autoconfigure.request.select.Condition;
import cn.yunovo.iov.factory.biz.report.report.model.DeviceReportDO;
import cn.yunovo.iov.factory.biz.report.report.model.DeviceReportQuery;
import cn.yunovo.iov.factory.biz.statistics.repetition.model.RepetitionReportDO;


/**
 * mapper 包为dao包，
 * 注意：方法入参规则
 * 
 * @author huangzz
 *
 */
@Mapper
public interface DeviceReportMapper extends GeneralMapper<DeviceReportDO> {

	DeviceReportDO getDeviceReportById(Integer id);
	
	List<DeviceReportDO> selectDeviceReport(@Param("query")DeviceReportQuery deviceReportQuery, @Param("condition")Map<String, Condition> conditionMap);
	Integer insertDeviceReport(DeviceReportDO deviceReportDO);

	Integer updateDeviceReport(DeviceReportDO deviceReportDO);

	Integer deleteDeviceReportById(Integer id);
	
	List<RepetitionReportDO> selectIccidReportTimes();
	
	List<RepetitionReportDO> selectSnReportTimes();
	
	List<RepetitionReportDO> selectImeiReportTimes();
	
	Integer selectReportTimesByDay(@Param("today")String today);
}
