package cn.yunovo.iov.device.zen.biz.statistics.type.service;

import java.util.Map;

import cn.yunovo.iov.boot.autoconfigure.request.select.Condition;
import cn.yunovo.iov.device.zen.biz.statistics.type.model.StatisticsTypeDO;
import cn.yunovo.iov.device.zen.biz.statistics.type.model.StatisticsTypeDTO;
import cn.yunovo.iov.device.zen.biz.statistics.type.model.StatisticsTypeQuery;

/**
 * Service层：相对具体的业务逻辑服务层。对多个Manager的组合复用 。
 * 
 * @author huangzz
 *
 */
public interface StatisticsTypeService {

	StatisticsTypeDTO getStatisticsTypeById(Integer id);
	
	StatisticsTypeDTO queryStatisticsType(StatisticsTypeQuery statisticsTypeQuery);

	Object selectStatisticsType(StatisticsTypeQuery statisticsTypeQuery, Map<String, Condition> conditionMap);
	
	Integer insertStatisticsType(StatisticsTypeDO statisticsTypeDO);

	Integer deleteStatisticsTypeById(Integer id);

	Integer updateStatisticsType(StatisticsTypeDO statisticsTypeDO);
	
    Integer deleteStatisticsType(StatisticsTypeDO statisticsTypeDO);

}
