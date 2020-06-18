package cn.yunovo.iov.factory.biz.statistics.paster.manager;

import java.util.List;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.yunovo.iov.framework.commons.beanutils.bean.BeanMapper;
import cn.yunovo.iov.boot.autoconfigure.request.select.Condition;
import cn.yunovo.iov.factory.biz.statistics.paster.mapper.StatisticsPasterMapper;
import cn.yunovo.iov.factory.biz.statistics.paster.model.StatisticsPasterDO;
import cn.yunovo.iov.factory.biz.statistics.paster.model.StatisticsPasterDTO;
import cn.yunovo.iov.factory.biz.statistics.paster.model.StatisticsPasterQuery;

/**
 * Manager层：通用业务处理层
 * @author huangzz
 *
 */
@Component
public class StatisticsPasterManager {

	@Autowired
	private StatisticsPasterMapper statisticsPasterMapper;

	public StatisticsPasterDTO getStatisticsPasterById(Integer id) {
		StatisticsPasterDO statisticsPasterDO = statisticsPasterMapper.getStatisticsPasterById(id);
		StatisticsPasterDTO StatisticsPasterDTO = BeanMapper.map(statisticsPasterDO, StatisticsPasterDTO.class);
		return StatisticsPasterDTO;
	}

	public List<StatisticsPasterDTO> selectStatisticsPaster(StatisticsPasterQuery statisticsPasterQuery, Map<String, Condition> conditionMap) {
		List<StatisticsPasterDO> list = statisticsPasterMapper.selectStatisticsPaster(statisticsPasterQuery, conditionMap);
		return BeanMapper.mapList(list, StatisticsPasterDTO.class);
	}
	public Integer insertStatisticsPaster(StatisticsPasterDO statisticsPasterDO) {
		statisticsPasterDO.setCreateTime(new Date());
		statisticsPasterDO.setUpdateTime(new Date());
		return statisticsPasterMapper.insertStatisticsPaster(statisticsPasterDO);
	}

	public Integer deleteStatisticsPasterById(Integer id) {
		return statisticsPasterMapper.deleteStatisticsPasterById(id);
	}

	public Integer updateStatisticsPaster(StatisticsPasterDO statisticsPasterDO) {
		statisticsPasterDO.setUpdateTime(new Date());
		return statisticsPasterMapper.updateStatisticsPaster(statisticsPasterDO);
	}
	
	public Integer statisticsCurrentDay(StatisticsPasterQuery statisticsPasterQuery) {
		return statisticsPasterMapper.statisticsCurrentDay(statisticsPasterQuery);
	}
}