package cn.yunovo.iov.factory.biz.statistics.manager;

import java.util.List;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.yunovo.iov.framework.commons.beanutils.bean.BeanMapper;
import cn.yunovo.iov.boot.autoconfigure.request.select.Condition;
import cn.yunovo.iov.factory.biz.statistics.mapper.StatisticsTypeMapper;
import cn.yunovo.iov.factory.biz.statistics.model.StatisticsTypeDO;
import cn.yunovo.iov.factory.biz.statistics.model.StatisticsTypeDTO;
import cn.yunovo.iov.factory.biz.statistics.model.StatisticsTypeQuery;

/**
 * Manager层：通用业务处理层
 * @author huangzz
 *
 */
@Component
public class StatisticsTypeManager {

	@Autowired
	private StatisticsTypeMapper statisticsTypeMapper;

	public StatisticsTypeDTO getStatisticsTypeById(Integer id) {
		StatisticsTypeDO statisticsTypeDO = statisticsTypeMapper.getStatisticsTypeById(id);
		StatisticsTypeDTO StatisticsTypeDTO = BeanMapper.map(statisticsTypeDO, StatisticsTypeDTO.class);
		return StatisticsTypeDTO;
	}

	public List<StatisticsTypeDTO> selectStatisticsType(StatisticsTypeQuery statisticsTypeQuery, Map<String, Condition> conditionMap) {
		List<StatisticsTypeDO> list = statisticsTypeMapper.selectStatisticsType(statisticsTypeQuery, conditionMap);
		return BeanMapper.mapList(list, StatisticsTypeDTO.class);
	}
	public Integer insertStatisticsType(StatisticsTypeDO statisticsTypeDO) {
		statisticsTypeDO.setCreateTime(new Date());
		return statisticsTypeMapper.insertStatisticsType(statisticsTypeDO);
	}

	public Integer deleteStatisticsTypeById(Integer id) {
		return statisticsTypeMapper.deleteStatisticsTypeById(id);
	}

	public Integer updateStatisticsType(StatisticsTypeDO statisticsTypeDO) {
		return statisticsTypeMapper.updateStatisticsType(statisticsTypeDO);
	}
}