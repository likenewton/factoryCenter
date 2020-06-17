package cn.yunovo.iov.factory.biz.statistics.area.manager;

import java.util.List;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.yunovo.iov.framework.commons.beanutils.bean.BeanMapper;
import cn.yunovo.iov.boot.autoconfigure.request.select.Condition;
import cn.yunovo.iov.factory.biz.statistics.area.mapper.StatisticsAreaMapper;
import cn.yunovo.iov.factory.biz.statistics.area.model.StatisticsAreaDO;
import cn.yunovo.iov.factory.biz.statistics.area.model.StatisticsAreaDTO;
import cn.yunovo.iov.factory.biz.statistics.area.model.StatisticsAreaQuery;

/**
 * Manager层：通用业务处理层
 * @author huangzz
 *
 */
@Component
public class StatisticsAreaManager {

	@Autowired
	private StatisticsAreaMapper statisticsAreaMapper;

	public StatisticsAreaDTO getStatisticsAreaById(Integer id) {
		StatisticsAreaDO statisticsAreaDO = statisticsAreaMapper.getStatisticsAreaById(id);
		StatisticsAreaDTO statisticsAreaDTO = BeanMapper.map(statisticsAreaDO, StatisticsAreaDTO.class);
		return statisticsAreaDTO;
	}
	
	public StatisticsAreaDTO queryStatisticsArea(StatisticsAreaQuery statisticsAreaQuery) {
		StatisticsAreaDO statisticsAreaDO = statisticsAreaMapper.queryStatisticsArea(statisticsAreaQuery);
		StatisticsAreaDTO statisticsAreaDTO = BeanMapper.map(statisticsAreaDO, StatisticsAreaDTO.class);
		return statisticsAreaDTO;
	}

	public List<StatisticsAreaDTO> selectStatisticsArea(StatisticsAreaQuery statisticsAreaQuery, Map<String, Condition> conditionMap) {
		List<StatisticsAreaDO> list = statisticsAreaMapper.selectStatisticsArea(statisticsAreaQuery, conditionMap);
		return BeanMapper.mapList(list, StatisticsAreaDTO.class);
	}
	public Integer insertStatisticsArea(StatisticsAreaDO statisticsAreaDO) {
		statisticsAreaDO.setCreateTime(new Date());
		statisticsAreaDO.setUpdateTime(new Date());
		return statisticsAreaMapper.insertStatisticsArea(statisticsAreaDO);
	}

	public Integer deleteStatisticsAreaById(Integer id) {
		return statisticsAreaMapper.deleteStatisticsAreaById(id);
	}

	public Integer updateStatisticsArea(StatisticsAreaDO statisticsAreaDO) {
		statisticsAreaDO.setUpdateTime(new Date());
		return statisticsAreaMapper.updateStatisticsArea(statisticsAreaDO);
	}
}