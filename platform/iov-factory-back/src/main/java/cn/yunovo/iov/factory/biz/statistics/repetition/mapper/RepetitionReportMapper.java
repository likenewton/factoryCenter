package cn.yunovo.iov.factory.biz.statistics.repetition.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import cn.yunovo.iov.boot.autoconfigure.mybatis.GeneralMapper;
import cn.yunovo.iov.boot.autoconfigure.request.select.Condition;
import cn.yunovo.iov.factory.biz.statistics.repetition.model.RepetitionReportDO;
import cn.yunovo.iov.factory.biz.statistics.repetition.model.RepetitionReportDTO;
import cn.yunovo.iov.factory.biz.statistics.repetition.model.RepetitionReportQuery;


/**
 * mapper 包为dao包，
 * 注意：方法入参规则
 * 
 * @author huangzz
 *
 */
@Mapper
public interface RepetitionReportMapper extends GeneralMapper<RepetitionReportDO> {

	RepetitionReportDO getRepetitionReportById(Integer id);
	
	List<RepetitionReportDO> selectRepetitionReport(@Param("query")RepetitionReportQuery repetitionReportQuery, @Param("condition")Map<String, Condition> conditionMap);
	Integer insertRepetitionReport(RepetitionReportDO repetitionReportDO);

	Integer updateRepetitionReport(RepetitionReportDO repetitionReportDO);

	Integer deleteRepetitionReportById(Integer id);
	
	Integer stateRepetitionReport(List<RepetitionReportDTO> list);
}
