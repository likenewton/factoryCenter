package cn.yunovo.iov.device.zen.biz.statistics.repetition.service;

import java.util.List;
import java.util.Map;

import cn.yunovo.iov.boot.autoconfigure.request.select.Condition;
import cn.yunovo.iov.device.zen.biz.statistics.repetition.model.RepetitionReportDO;
import cn.yunovo.iov.device.zen.biz.statistics.repetition.model.RepetitionReportDTO;
import cn.yunovo.iov.device.zen.biz.statistics.repetition.model.RepetitionReportQuery;

/**
 * Service层：相对具体的业务逻辑服务层。对多个Manager的组合复用 。
 * 
 * @author huangzz
 *
 */
public interface RepetitionReportService {

	RepetitionReportDTO getRepetitionReportById(Integer id);

	Object selectRepetitionReport(RepetitionReportQuery repetitionReportQuery, Map<String, Condition> conditionMap);

	Integer insertRepetitionReport(RepetitionReportDO repetitionReportDO);

	Integer deleteRepetitionReportById(Integer id);

	Integer updateRepetitionReport(RepetitionReportDO repetitionReportDO);

	void stateRepetitionReport(List<RepetitionReportDTO> iccidList, List<RepetitionReportDTO> snList, List<RepetitionReportDTO> imeiList);

}
