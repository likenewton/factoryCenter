package cn.yunovo.iov.device.zen.biz.statistics.paster.service;

import java.util.Map;

import cn.yunovo.iov.boot.autoconfigure.request.select.Condition;
import cn.yunovo.iov.device.zen.biz.statistics.paster.model.StatisticsPasterDO;
import cn.yunovo.iov.device.zen.biz.statistics.paster.model.StatisticsPasterDTO;
import cn.yunovo.iov.device.zen.biz.statistics.paster.model.StatisticsPasterQuery;

/**
 * Service层：相对具体的业务逻辑服务层。对多个Manager的组合复用 。
 * 
 * @author huangzz
 *
 */
public interface StatisticsPasterService {

	StatisticsPasterDTO getStatisticsPasterById(Integer id);

	Object selectStatisticsPaster(StatisticsPasterQuery statisticsPasterQuery, Map<String, Condition> conditionMap);
	
	Object selectStatisticsPaster(StatisticsPasterQuery statisticsPasterQuery, Map<String, Condition> conditionMap, Boolean isDac);

	Integer insertStatisticsPaster(StatisticsPasterDO statisticsPasterDO);

	Integer deleteStatisticsPasterById(Integer id);

	Integer updateStatisticsPaster(StatisticsPasterDO statisticsPasterDO);
	
	Integer statisticsCurrentDay(StatisticsPasterQuery statisticsPasterQuery);

}
