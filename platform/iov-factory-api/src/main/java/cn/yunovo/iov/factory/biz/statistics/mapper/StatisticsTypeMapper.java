package cn.yunovo.iov.factory.biz.statistics.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import cn.yunovo.iov.boot.autoconfigure.mybatis.GeneralMapper;
import cn.yunovo.iov.boot.autoconfigure.request.select.Condition;
import cn.yunovo.iov.factory.biz.statistics.model.StatisticsTypeDO;
import cn.yunovo.iov.factory.biz.statistics.model.StatisticsTypeQuery;


/**
 * mapper 包为dao包，
 * 注意：方法入参规则
 * 
 * @author huangzz
 *
 */
@Mapper
public interface StatisticsTypeMapper extends GeneralMapper<StatisticsTypeDO> {

	StatisticsTypeDO getStatisticsTypeById(Integer id);
	
	List<StatisticsTypeDO> selectStatisticsType(@Param("query")StatisticsTypeQuery statisticsTypeQuery, @Param("condition")Map<String, Condition> conditionMap);
	Integer insertStatisticsType(StatisticsTypeDO statisticsTypeDO);

	Integer updateStatisticsType(StatisticsTypeDO statisticsTypeDO);

	Integer deleteStatisticsTypeById(Integer id);
}
