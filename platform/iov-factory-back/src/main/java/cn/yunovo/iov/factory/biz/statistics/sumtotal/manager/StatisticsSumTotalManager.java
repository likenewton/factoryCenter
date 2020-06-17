package cn.yunovo.iov.factory.biz.statistics.sumtotal.manager;

import java.util.List;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.yunovo.iov.framework.commons.beanutils.bean.BeanMapper;
import cn.yunovo.iov.boot.autoconfigure.request.select.Condition;
import cn.yunovo.iov.factory.biz.statistics.sumtotal.mapper.StatisticsSumTotalMapper;
import cn.yunovo.iov.factory.biz.statistics.sumtotal.model.StatisticsSumTotalDO;
import cn.yunovo.iov.factory.biz.statistics.sumtotal.model.StatisticsSumTotalDTO;
import cn.yunovo.iov.factory.biz.statistics.sumtotal.model.StatisticsSumTotalQuery;

/**
 * Manager层：通用业务处理层
 * @author huangzz
 *
 */
@Component
public class StatisticsSumTotalManager {

	@Autowired
	private StatisticsSumTotalMapper statisticsSumTotalMapper;

	public StatisticsSumTotalDTO getStatisticsSumTotalById(Integer id) {
		StatisticsSumTotalDO statisticsSumTotalDO = statisticsSumTotalMapper.getStatisticsSumTotalById(id);
		StatisticsSumTotalDTO statisticsSumTotalDTO = BeanMapper.map(statisticsSumTotalDO, StatisticsSumTotalDTO.class);
		return statisticsSumTotalDTO;
	}
	
	public StatisticsSumTotalDTO queryStatisticsSumTotal(StatisticsSumTotalQuery statisticsSumTotalQuery) {
		StatisticsSumTotalDO statisticsSumTotalDO = statisticsSumTotalMapper.queryStatisticsSumTotal(statisticsSumTotalQuery);
		StatisticsSumTotalDTO statisticsSumTotalDTO = BeanMapper.map(statisticsSumTotalDO, StatisticsSumTotalDTO.class);
		return statisticsSumTotalDTO;
	}

	public List<StatisticsSumTotalDTO> selectStatisticsSumTotal(StatisticsSumTotalQuery statisticsSumTotalQuery, Map<String, Condition> conditionMap) {
		List<StatisticsSumTotalDO> list = statisticsSumTotalMapper.selectStatisticsSumTotal(statisticsSumTotalQuery, conditionMap);
		return BeanMapper.mapList(list, StatisticsSumTotalDTO.class);
	}
	public Integer insertStatisticsSumTotal(StatisticsSumTotalDO statisticsSumTotalDO) {
		statisticsSumTotalDO.setCreateTime(new Date());
		statisticsSumTotalDO.setUpdateTime(new Date());
		return statisticsSumTotalMapper.insertStatisticsSumTotal(statisticsSumTotalDO);
	}

	public Integer deleteStatisticsSumTotalById(Integer id) {
		return statisticsSumTotalMapper.deleteStatisticsSumTotalById(id);
	}

	public Integer updateStatisticsSumTotal(StatisticsSumTotalDO statisticsSumTotalDO) {
		statisticsSumTotalDO.setUpdateTime(new Date());
		return statisticsSumTotalMapper.updateStatisticsSumTotal(statisticsSumTotalDO);
	}
}