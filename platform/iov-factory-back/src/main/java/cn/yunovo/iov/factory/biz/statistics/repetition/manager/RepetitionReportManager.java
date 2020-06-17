package cn.yunovo.iov.factory.biz.statistics.repetition.manager;

import java.util.List;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.yunovo.iov.framework.commons.beanutils.bean.BeanMapper;
import cn.yunovo.iov.boot.autoconfigure.request.select.Condition;
import cn.yunovo.iov.factory.biz.statistics.repetition.mapper.RepetitionReportMapper;
import cn.yunovo.iov.factory.biz.statistics.repetition.model.RepetitionReportDO;
import cn.yunovo.iov.factory.biz.statistics.repetition.model.RepetitionReportDTO;
import cn.yunovo.iov.factory.biz.statistics.repetition.model.RepetitionReportQuery;

/**
 * Manager层：通用业务处理层
 * 
 * @author huangzz
 *
 */
@Component
public class RepetitionReportManager {

	@Autowired
	private RepetitionReportMapper repetitionReportMapper;

	public RepetitionReportDTO getRepetitionReportById(Integer id) {
		RepetitionReportDO repetitionReportDO = repetitionReportMapper.getRepetitionReportById(id);
		RepetitionReportDTO RepetitionReportDTO = BeanMapper.map(repetitionReportDO, RepetitionReportDTO.class);
		return RepetitionReportDTO;
	}

	public List<RepetitionReportDTO> selectRepetitionReport(RepetitionReportQuery repetitionReportQuery, Map<String, Condition> conditionMap) {
		List<RepetitionReportDO> list = repetitionReportMapper.selectRepetitionReport(repetitionReportQuery, conditionMap);
		return BeanMapper.mapList(list, RepetitionReportDTO.class);
	}

	public Integer insertRepetitionReport(RepetitionReportDO repetitionReportDO) {
		repetitionReportDO.setCreateTime(new Date());
		repetitionReportDO.setUpdateTime(new Date());
		return repetitionReportMapper.insertRepetitionReport(repetitionReportDO);
	}

	public Integer deleteRepetitionReportById(Integer id) {
		return repetitionReportMapper.deleteRepetitionReportById(id);
	}

	public Integer updateRepetitionReport(RepetitionReportDO repetitionReportDO) {
		repetitionReportDO.setUpdateTime(new Date());
		return repetitionReportMapper.updateRepetitionReport(repetitionReportDO);
	}

	public Integer stateRepetitionReport(List<RepetitionReportDTO> list) {
		return repetitionReportMapper.stateRepetitionReport(list);
	}
}