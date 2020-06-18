package cn.yunovo.iov.factory.biz.statistics.sumtotal.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import cn.yunovo.iov.boot.autoconfigure.mybatis.GeneralMapper;
import cn.yunovo.iov.boot.autoconfigure.request.select.Condition;
import cn.yunovo.iov.factory.biz.statistics.sumtotal.model.StatisticsSumTotalDO;
import cn.yunovo.iov.factory.biz.statistics.sumtotal.model.StatisticsSumTotalQuery;


/**
 * mapper 包为dao包，
 * 注意：方法入参规则
 * 
 * @author huangzz
 *
 */
@Mapper
public interface StatisticsSumTotalMapper extends GeneralMapper<StatisticsSumTotalDO> {

	StatisticsSumTotalDO getStatisticsSumTotalById(Integer id);
	
	List<StatisticsSumTotalDO> selectStatisticsSumTotal(@Param("query")StatisticsSumTotalQuery statisticsSumTotalQuery, @Param("condition")Map<String, Condition> conditionMap);
	
	StatisticsSumTotalDO queryStatisticsSumTotal(@Param("query")StatisticsSumTotalQuery statisticsSumTotalQuery);
	
	Integer insertStatisticsSumTotal(StatisticsSumTotalDO statisticsSumTotalDO);

	Integer updateStatisticsSumTotal(StatisticsSumTotalDO statisticsSumTotalDO);

	Integer deleteStatisticsSumTotalById(Integer id);
}
