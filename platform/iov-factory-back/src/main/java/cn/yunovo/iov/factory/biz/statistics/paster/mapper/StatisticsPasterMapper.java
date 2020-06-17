package cn.yunovo.iov.factory.biz.statistics.paster.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import cn.yunovo.iov.boot.autoconfigure.mybatis.GeneralMapper;
import cn.yunovo.iov.boot.autoconfigure.request.select.Condition;
import cn.yunovo.iov.factory.biz.statistics.paster.model.StatisticsPasterDO;
import cn.yunovo.iov.factory.biz.statistics.paster.model.StatisticsPasterQuery;


/**
 * mapper 包为dao包，
 * 注意：方法入参规则
 * 
 * @author huangzz
 *
 */
@Mapper
public interface StatisticsPasterMapper extends GeneralMapper<StatisticsPasterDO> {

	StatisticsPasterDO getStatisticsPasterById(Integer id);
	
	List<StatisticsPasterDO> selectStatisticsPaster(@Param("query")StatisticsPasterQuery statisticsPasterQuery, @Param("condition")Map<String, Condition> conditionMap);
	Integer insertStatisticsPaster(StatisticsPasterDO statisticsPasterDO);

	Integer updateStatisticsPaster(StatisticsPasterDO statisticsPasterDO);

	Integer deleteStatisticsPasterById(Integer id);
	
	Integer statisticsCurrentDay(StatisticsPasterQuery statisticsPasterQuery);

}
