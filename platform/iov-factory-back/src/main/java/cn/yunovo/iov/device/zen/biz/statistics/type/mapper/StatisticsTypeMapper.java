package cn.yunovo.iov.device.zen.biz.statistics.type.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import cn.yunovo.iov.device.zen.biz.statistics.type.model.StatisticsTypeDO;
import cn.yunovo.iov.device.zen.biz.statistics.type.model.StatisticsTypeQuery;
import cn.yunovo.iov.boot.autoconfigure.mybatis.GeneralMapper;
import cn.yunovo.iov.boot.autoconfigure.request.select.Condition;


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
	
	StatisticsTypeDO queryStatisticsType(@Param("query")StatisticsTypeQuery statisticsTypeQuery);
	
	Integer insertStatisticsType(StatisticsTypeDO statisticsTypeDO);

	Integer updateStatisticsType(StatisticsTypeDO statisticsTypeDO);

	Integer deleteStatisticsTypeById(Integer id);
	
	Integer deleteStatisticsType(@Param("item")StatisticsTypeDO statisticsTypeDO);
}
