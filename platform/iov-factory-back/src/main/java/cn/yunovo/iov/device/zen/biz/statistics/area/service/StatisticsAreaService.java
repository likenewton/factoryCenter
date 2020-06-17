package cn.yunovo.iov.device.zen.biz.statistics.area.service;

import java.util.Map;

import cn.yunovo.iov.boot.autoconfigure.request.select.Condition;
import cn.yunovo.iov.device.zen.biz.statistics.area.model.StatisticsAreaDO;
import cn.yunovo.iov.device.zen.biz.statistics.area.model.StatisticsAreaDTO;
import cn.yunovo.iov.device.zen.biz.statistics.area.model.StatisticsAreaQuery;

/**
 * Service层：相对具体的业务逻辑服务层。对多个Manager的组合复用 。
 * 
 * @author huangzz
 *
 */
public interface StatisticsAreaService {

	StatisticsAreaDTO getStatisticsAreaById(Integer id);
	
	StatisticsAreaDTO queryStatisticsArea(StatisticsAreaQuery statisticsAreaQuery);

	Object selectStatisticsArea(StatisticsAreaQuery statisticsAreaQuery, Map<String, Condition> conditionMap);
	
	Object selectStatisticsArea(StatisticsAreaQuery statisticsAreaQuery, Map<String, Condition> conditionMap, Boolean isDac);
	
	Integer insertStatisticsArea(StatisticsAreaDO statisticsAreaDO);

	Integer deleteStatisticsAreaById(Integer id);

	Integer updateStatisticsArea(StatisticsAreaDO statisticsAreaDO);

}
