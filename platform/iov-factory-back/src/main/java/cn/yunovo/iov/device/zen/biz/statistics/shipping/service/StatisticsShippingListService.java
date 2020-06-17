package cn.yunovo.iov.device.zen.biz.statistics.shipping.service;

import java.util.Map;

import cn.yunovo.iov.boot.autoconfigure.request.select.Condition;
import cn.yunovo.iov.device.zen.biz.statistics.shipping.model.StatisticsShippingListDO;
import cn.yunovo.iov.device.zen.biz.statistics.shipping.model.StatisticsShippingListDTO;
import cn.yunovo.iov.device.zen.biz.statistics.shipping.model.StatisticsShippingListQuery;

/**
 * Service层：相对具体的业务逻辑服务层。对多个Manager的组合复用 。
 * 
 * @author huangzz
 *
 */
public interface StatisticsShippingListService {

	StatisticsShippingListDTO getStatisticsShippingListById(Integer id);
	
	StatisticsShippingListDTO queryStatisticsShippingList(StatisticsShippingListQuery statisticsShippingListQuery);

	Object selectStatisticsShippingList(StatisticsShippingListQuery statisticsShippingListQuery, Map<String, Condition> conditionMap, Boolean isDac);
	
	Object selectStatisticsShippingList(StatisticsShippingListQuery statisticsShippingListQuery, Map<String, Condition> conditionMap);
	
	Integer insertStatisticsShippingList(StatisticsShippingListDO statisticsShippingListDO);

	Integer deleteStatisticsShippingListById(Integer id);

	Integer updateStatisticsShippingList(StatisticsShippingListDO statisticsShippingListDO);
	
	Integer statisticsCurrentDay(StatisticsShippingListQuery statisticsShippingListQuery);

}
