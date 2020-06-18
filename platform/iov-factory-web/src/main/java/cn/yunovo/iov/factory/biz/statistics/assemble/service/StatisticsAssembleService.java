package cn.yunovo.iov.factory.biz.statistics.assemble.service;

import java.util.Map;

import cn.yunovo.iov.boot.autoconfigure.request.select.Condition;
import cn.yunovo.iov.factory.biz.statistics.assemble.model.StatisticsAssembleDO;
import cn.yunovo.iov.factory.biz.statistics.assemble.model.StatisticsAssembleDTO;
import cn.yunovo.iov.factory.biz.statistics.assemble.model.StatisticsAssembleQuery;

/**
 * Service层：相对具体的业务逻辑服务层。对多个Manager的组合复用 。
 * 
 * @author huangzz
 *
 */
public interface StatisticsAssembleService {

	StatisticsAssembleDTO getStatisticsAssembleById(Integer id);

	Object selectStatisticsAssemble(StatisticsAssembleQuery statisticsAssembleQuery, Map<String, Condition> conditionMap);
	
	Object selectStatisticsAssemble(StatisticsAssembleQuery statisticsAssembleQuery, Map<String, Condition> conditionMap, Boolean isDac);

	Integer insertStatisticsAssemble(StatisticsAssembleDO statisticsAssembleDO);

	Integer deleteStatisticsAssembleById(Integer id);

	Integer updateStatisticsAssemble(StatisticsAssembleDO statisticsAssembleDO);
	
	Integer statisticsCurrentDay(StatisticsAssembleQuery statisticsAssembleQuery);

}
