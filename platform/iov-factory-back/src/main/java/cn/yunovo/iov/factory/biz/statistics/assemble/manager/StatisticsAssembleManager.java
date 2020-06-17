package cn.yunovo.iov.factory.biz.statistics.assemble.manager;

import java.util.List;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.yunovo.iov.framework.commons.beanutils.bean.BeanMapper;
import cn.yunovo.iov.boot.autoconfigure.request.select.Condition;
import cn.yunovo.iov.factory.biz.statistics.assemble.mapper.StatisticsAssembleMapper;
import cn.yunovo.iov.factory.biz.statistics.assemble.model.StatisticsAssembleDO;
import cn.yunovo.iov.factory.biz.statistics.assemble.model.StatisticsAssembleDTO;
import cn.yunovo.iov.factory.biz.statistics.assemble.model.StatisticsAssembleQuery;

/**
 * Manager层：通用业务处理层
 * @author huangzz
 *
 */
@Component
public class StatisticsAssembleManager {

	@Autowired
	private StatisticsAssembleMapper statisticsAssembleMapper;

	public StatisticsAssembleDTO getStatisticsAssembleById(Integer id) {
		StatisticsAssembleDO statisticsAssembleDO = statisticsAssembleMapper.getStatisticsAssembleById(id);
		StatisticsAssembleDTO StatisticsAssembleDTO = BeanMapper.map(statisticsAssembleDO, StatisticsAssembleDTO.class);
		return StatisticsAssembleDTO;
	}

	public List<StatisticsAssembleDTO> selectStatisticsAssemble(StatisticsAssembleQuery statisticsAssembleQuery, Map<String, Condition> conditionMap) {
		List<StatisticsAssembleDO> list = statisticsAssembleMapper.selectStatisticsAssemble(statisticsAssembleQuery, conditionMap);
		return BeanMapper.mapList(list, StatisticsAssembleDTO.class);
	}
	public Integer insertStatisticsAssemble(StatisticsAssembleDO statisticsAssembleDO) {
		statisticsAssembleDO.setCreateTime(new Date());
		statisticsAssembleDO.setUpdateTime(new Date());
		return statisticsAssembleMapper.insertStatisticsAssemble(statisticsAssembleDO);
	}

	public Integer deleteStatisticsAssembleById(Integer id) {
		return statisticsAssembleMapper.deleteStatisticsAssembleById(id);
	}

	public Integer updateStatisticsAssemble(StatisticsAssembleDO statisticsAssembleDO) {
		statisticsAssembleDO.setUpdateTime(new Date());
		return statisticsAssembleMapper.updateStatisticsAssemble(statisticsAssembleDO);
	}
	
	public Integer statisticsCurrentDay(StatisticsAssembleQuery statisticsAssembleQuery) {
		return statisticsAssembleMapper.statisticsCurrentDay(statisticsAssembleQuery);
	}
}