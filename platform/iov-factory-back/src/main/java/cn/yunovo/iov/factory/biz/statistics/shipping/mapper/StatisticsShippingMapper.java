package cn.yunovo.iov.factory.biz.statistics.shipping.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import cn.yunovo.iov.boot.autoconfigure.mybatis.GeneralMapper;
import cn.yunovo.iov.boot.autoconfigure.request.select.Condition;
import cn.yunovo.iov.factory.biz.statistics.shipping.model.StatisticsShippingDO;
import cn.yunovo.iov.factory.biz.statistics.shipping.model.StatisticsShippingQuery;


/**
 * mapper 包为dao包，
 * 注意：方法入参规则
 * 
 * @author huangzz
 *
 */
@Mapper
public interface StatisticsShippingMapper extends GeneralMapper<StatisticsShippingDO> {

	StatisticsShippingDO getStatisticsShippingById(Integer id);
	
	StatisticsShippingDO statisticsShipping(StatisticsShippingQuery statisticsShippingQuery);
	
	List<StatisticsShippingDO> selectStatisticsShipping(@Param("query")StatisticsShippingQuery statisticsShippingQuery, @Param("condition")Map<String, Condition> conditionMap);
	
	Integer insertStatisticsShipping(StatisticsShippingDO statisticsShippingDO);

	Integer updateStatisticsShipping(StatisticsShippingDO statisticsShippingDO);

	Integer deleteStatisticsShippingById(Integer id);
}
