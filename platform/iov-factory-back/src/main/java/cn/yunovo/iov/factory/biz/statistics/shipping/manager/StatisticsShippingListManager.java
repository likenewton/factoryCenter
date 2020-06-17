package cn.yunovo.iov.factory.biz.statistics.shipping.manager;

import java.util.List;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.yunovo.iov.framework.commons.beanutils.bean.BeanMapper;
import cn.yunovo.iov.boot.autoconfigure.request.select.Condition;
import cn.yunovo.iov.factory.biz.statistics.shipping.mapper.StatisticsShippingListMapper;
import cn.yunovo.iov.factory.biz.statistics.shipping.model.StatisticsShippingListDO;
import cn.yunovo.iov.factory.biz.statistics.shipping.model.StatisticsShippingListDTO;
import cn.yunovo.iov.factory.biz.statistics.shipping.model.StatisticsShippingListQuery;

/**
 * Manager层：通用业务处理层
 * @author huangzz
 *
 */
@Component
public class StatisticsShippingListManager {

	@Autowired
	private StatisticsShippingListMapper statisticsShippingListMapper;

	public StatisticsShippingListDTO getStatisticsShippingListById(Integer id) {
		StatisticsShippingListDO statisticsShippingListDO = statisticsShippingListMapper.getStatisticsShippingListById(id);
		StatisticsShippingListDTO statisticsShippingListDTO = BeanMapper.map(statisticsShippingListDO, StatisticsShippingListDTO.class);
		return statisticsShippingListDTO;
	}
	
	public StatisticsShippingListDTO queryStatisticsShippingList(StatisticsShippingListQuery statisticsShippingListQuery) {
		StatisticsShippingListDO statisticsShippingListDO = statisticsShippingListMapper.queryStatisticsShippingList(statisticsShippingListQuery);
		StatisticsShippingListDTO statisticsShippingListDTO = BeanMapper.map(statisticsShippingListDO, StatisticsShippingListDTO.class);
		return statisticsShippingListDTO;
	}

	public List<StatisticsShippingListDTO> selectStatisticsShippingList(StatisticsShippingListQuery statisticsShippingListQuery, Map<String, Condition> conditionMap) {
		List<StatisticsShippingListDO> list = statisticsShippingListMapper.selectStatisticsShippingList(statisticsShippingListQuery, conditionMap);
		return BeanMapper.mapList(list, StatisticsShippingListDTO.class);
	}
	public Integer insertStatisticsShippingList(StatisticsShippingListDO statisticsShippingListDO) {
		statisticsShippingListDO.setCreateTime(new Date());
		statisticsShippingListDO.setUpdateTime(new Date());
		return statisticsShippingListMapper.insertStatisticsShippingList(statisticsShippingListDO);
	}

	public Integer deleteStatisticsShippingListById(Integer id) {
		return statisticsShippingListMapper.deleteStatisticsShippingListById(id);
	}

	public Integer updateStatisticsShippingList(StatisticsShippingListDO statisticsShippingListDO) {
		statisticsShippingListDO.setUpdateTime(new Date());
		return statisticsShippingListMapper.updateStatisticsShippingList(statisticsShippingListDO);
	}
	
	public Integer statisticsCurrentDay(StatisticsShippingListQuery statisticsShippingListQuery) {
		return statisticsShippingListMapper.statisticsCurrentDay(statisticsShippingListQuery);
	}
}