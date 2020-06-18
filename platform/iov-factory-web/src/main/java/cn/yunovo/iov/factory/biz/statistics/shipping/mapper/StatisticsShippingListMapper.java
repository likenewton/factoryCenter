package cn.yunovo.iov.factory.biz.statistics.shipping.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import cn.yunovo.iov.boot.autoconfigure.mybatis.GeneralMapper;
import cn.yunovo.iov.boot.autoconfigure.request.select.Condition;
import cn.yunovo.iov.factory.biz.statistics.shipping.model.StatisticsShippingListDO;
import cn.yunovo.iov.factory.biz.statistics.shipping.model.StatisticsShippingListQuery;


/**
 * mapper 包为dao包，
 * 注意：方法入参规则
 * 
 * @author huangzz
 *
 */
@Mapper
public interface StatisticsShippingListMapper extends GeneralMapper<StatisticsShippingListDO> {

	StatisticsShippingListDO getStatisticsShippingListById(Integer id);
	
	List<StatisticsShippingListDO> selectStatisticsShippingList(@Param("query")StatisticsShippingListQuery statisticsShippingListQuery, @Param("condition")Map<String, Condition> conditionMap);
	
	StatisticsShippingListDO queryStatisticsShippingList(@Param("query")StatisticsShippingListQuery statisticsShippingListQuery);
	
	Integer insertStatisticsShippingList(StatisticsShippingListDO statisticsShippingListDO);

	Integer updateStatisticsShippingList(StatisticsShippingListDO statisticsShippingListDO);

	Integer deleteStatisticsShippingListById(Integer id);
	
	Integer statisticsCurrentDay(StatisticsShippingListQuery statisticsShippingListQuery);
}
