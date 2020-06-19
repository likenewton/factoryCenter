package cn.yunovo.iov.factory.biz.statistics.shipping.manager;

import java.util.List;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.yunovo.iov.framework.commons.beanutils.bean.BeanMapper;
import cn.yunovo.iov.boot.autoconfigure.request.select.Condition;
import cn.yunovo.iov.factory.biz.statistics.shipping.mapper.StatisticsShippingMapper;
import cn.yunovo.iov.factory.biz.statistics.shipping.model.StatisticsShippingDO;
import cn.yunovo.iov.factory.biz.statistics.shipping.model.StatisticsShippingDTO;
import cn.yunovo.iov.factory.biz.statistics.shipping.model.StatisticsShippingQuery;

/**
 * Manager层：通用业务处理层
 * @author huangzz
 *
 */
@Component
public class StatisticsShippingManager {

	@Autowired
	private StatisticsShippingMapper statisticsShippingMapper;

	public StatisticsShippingDTO getStatisticsShippingById(Integer id) {
		StatisticsShippingDO statisticsShippingDO = statisticsShippingMapper.getStatisticsShippingById(id);
		StatisticsShippingDTO StatisticsShippingDTO = BeanMapper.map(statisticsShippingDO, StatisticsShippingDTO.class);
		return StatisticsShippingDTO;
	}
	
	public StatisticsShippingDTO queryStatisticsShipping(StatisticsShippingQuery statisticsShippingQuery) {
		StatisticsShippingDO statisticsShippingDO = statisticsShippingMapper.queryStatisticsShipping(statisticsShippingQuery);
		StatisticsShippingDTO statisticsShippingDTO = BeanMapper.map(statisticsShippingDO, StatisticsShippingDTO.class);
		return statisticsShippingDTO;
	}
	
	public StatisticsShippingDTO statisticsShipping(StatisticsShippingQuery statisticsShippingQuery) {
		StatisticsShippingDO statisticsShippingDO = statisticsShippingMapper.statisticsShipping(statisticsShippingQuery);
		StatisticsShippingDTO StatisticsShippingDTO = BeanMapper.map(statisticsShippingDO, StatisticsShippingDTO.class);
		return StatisticsShippingDTO;
	}
	
	public List<StatisticsShippingDTO> selectStatisticsShipping(StatisticsShippingQuery statisticsShippingQuery, Map<String, Condition> conditionMap) {
		List<StatisticsShippingDO> list = statisticsShippingMapper.selectStatisticsShipping(statisticsShippingQuery, conditionMap);
		return BeanMapper.mapList(list, StatisticsShippingDTO.class);
	}
	public Integer insertStatisticsShipping(StatisticsShippingDO statisticsShippingDO) {
		statisticsShippingDO.setCreateTime(new Date());
		statisticsShippingDO.setUpdateTime(new Date());
		return statisticsShippingMapper.insertStatisticsShipping(statisticsShippingDO);
	}

	public Integer deleteStatisticsShippingById(Integer id) {
		return statisticsShippingMapper.deleteStatisticsShippingById(id);
	}

	public Integer updateStatisticsShipping(StatisticsShippingDO statisticsShippingDO) {
		statisticsShippingDO.setUpdateTime(new Date());
		return statisticsShippingMapper.updateStatisticsShipping(statisticsShippingDO);
	}
	
	public Integer statisticsCurrentDay(StatisticsShippingQuery statisticsShippingQuery) {
		return statisticsShippingMapper.statisticsCurrentDay(statisticsShippingQuery);
	}
}