package cn.yunovo.iov.zen.biz.statistics.service;

import java.util.Map;

import cn.yunovo.iov.boot.autoconfigure.request.select.Condition;
import cn.yunovo.iov.zen.biz.statistics.model.StatisticsTypeDO;
import cn.yunovo.iov.zen.biz.statistics.model.StatisticsTypeDTO;
import cn.yunovo.iov.zen.biz.statistics.model.StatisticsTypeQuery;

/**
 * Service层：相对具体的业务逻辑服务层。对多个Manager的组合复用 。
 * 
 * @author huangzz
 *
 */
public interface StatisticsTypeService {

	StatisticsTypeDTO getStatisticsTypeById(Integer id);

	Object selectStatisticsType(StatisticsTypeQuery statisticsTypeQuery, Map<String, Condition> conditionMap);
	
	Integer insertStatisticsType(StatisticsTypeDO statisticsTypeDO);

	Integer deleteStatisticsTypeById(Integer id);

	Integer updateStatisticsType(StatisticsTypeDO statisticsTypeDO);

}
