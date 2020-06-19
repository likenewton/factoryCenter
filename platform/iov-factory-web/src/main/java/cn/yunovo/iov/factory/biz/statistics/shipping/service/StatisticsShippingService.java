package cn.yunovo.iov.factory.biz.statistics.shipping.service;

import java.util.Map;

import cn.yunovo.iov.boot.autoconfigure.request.select.Condition;
import cn.yunovo.iov.factory.biz.statistics.shipping.model.StatisticsShippingDO;
import cn.yunovo.iov.factory.biz.statistics.shipping.model.StatisticsShippingDTO;
import cn.yunovo.iov.factory.biz.statistics.shipping.model.StatisticsShippingListQuery;
import cn.yunovo.iov.factory.biz.statistics.shipping.model.StatisticsShippingQuery;

/**
 * Service层：相对具体的业务逻辑服务层。对多个Manager的组合复用 。
 * 
 * @author huangzz
 *
 */
public interface StatisticsShippingService {

	StatisticsShippingDTO getStatisticsShippingById(Integer id);

	Object selectStatisticsShipping(StatisticsShippingQuery statisticsShippingQuery, Map<String, Condition> conditionMap);
	
	StatisticsShippingDTO queryStatisticsShipping(StatisticsShippingQuery statisticsShippingQuery);

	Integer insertStatisticsShipping(StatisticsShippingDO statisticsShippingDO);

	Integer deleteStatisticsShippingById(Integer id);

	Integer updateStatisticsShipping(StatisticsShippingDO statisticsShippingDO);
	
	StatisticsShippingDTO statisticsShipping(StatisticsShippingQuery statisticsShippingQuery);

	Integer statisticsCurrentDay(StatisticsShippingQuery statisticsShippingQuery);
}
