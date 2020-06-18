package cn.yunovo.iov.factory.biz.statistics.area.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import cn.yunovo.iov.boot.autoconfigure.mybatis.GeneralMapper;
import cn.yunovo.iov.boot.autoconfigure.request.select.Condition;
import cn.yunovo.iov.factory.biz.statistics.area.model.StatisticsAreaDO;
import cn.yunovo.iov.factory.biz.statistics.area.model.StatisticsAreaQuery;


/**
 * mapper 包为dao包，
 * 注意：方法入参规则
 * 
 * @author huangzz
 *
 */
@Mapper
public interface StatisticsAreaMapper extends GeneralMapper<StatisticsAreaDO> {

	StatisticsAreaDO getStatisticsAreaById(Integer id);
	
	List<StatisticsAreaDO> selectStatisticsArea(@Param("query")StatisticsAreaQuery statisticsAreaQuery, @Param("condition")Map<String, Condition> conditionMap);
	
	StatisticsAreaDO queryStatisticsArea(@Param("query")StatisticsAreaQuery statisticsAreaQuery);
	
	Integer insertStatisticsArea(StatisticsAreaDO statisticsAreaDO);

	Integer updateStatisticsArea(StatisticsAreaDO statisticsAreaDO);

	Integer deleteStatisticsAreaById(Integer id);
	
	Integer deleteStatisticsArea(@Param("item")StatisticsAreaDO statisticsAreaDO);
}
