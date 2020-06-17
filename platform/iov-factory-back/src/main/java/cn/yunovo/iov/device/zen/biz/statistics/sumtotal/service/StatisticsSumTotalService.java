package cn.yunovo.iov.device.zen.biz.statistics.sumtotal.service;

import java.util.Map;

import cn.yunovo.iov.boot.autoconfigure.request.select.Condition;
import cn.yunovo.iov.device.zen.biz.statistics.sumtotal.model.StatisticsSumTotalDO;
import cn.yunovo.iov.device.zen.biz.statistics.sumtotal.model.StatisticsSumTotalDTO;
import cn.yunovo.iov.device.zen.biz.statistics.sumtotal.model.StatisticsSumTotalQuery;

/**
 * Service层：相对具体的业务逻辑服务层。对多个Manager的组合复用 。
 * 
 * @author huangzz
 *
 */
public interface StatisticsSumTotalService {

	StatisticsSumTotalDTO getStatisticsSumTotalById(Integer id);
	
	StatisticsSumTotalDTO queryStatisticsSumTotal(StatisticsSumTotalQuery statisticsSumTotalQuery);

	Object selectStatisticsSumTotal(StatisticsSumTotalQuery statisticsSumTotalQuery, Map<String, Condition> conditionMap);
	
	Integer insertStatisticsSumTotal(StatisticsSumTotalDO statisticsSumTotalDO);

	Integer deleteStatisticsSumTotalById(Integer id);

	Integer updateStatisticsSumTotal(StatisticsSumTotalDO statisticsSumTotalDO);

}
