package cn.yunovo.iov.factory.biz.statistics.assemble.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import cn.yunovo.iov.boot.autoconfigure.mybatis.GeneralMapper;
import cn.yunovo.iov.boot.autoconfigure.request.select.Condition;
import cn.yunovo.iov.factory.biz.statistics.assemble.model.StatisticsAssembleDO;
import cn.yunovo.iov.factory.biz.statistics.assemble.model.StatisticsAssembleQuery;


/**
 * mapper 包为dao包，
 * 注意：方法入参规则
 * 
 * @author huangzz
 *
 */
@Mapper
public interface StatisticsAssembleMapper extends GeneralMapper<StatisticsAssembleDO> {

	StatisticsAssembleDO getStatisticsAssembleById(Integer id);
	
	List<StatisticsAssembleDO> selectStatisticsAssemble(@Param("query")StatisticsAssembleQuery statisticsAssembleQuery, @Param("condition")Map<String, Condition> conditionMap);
	Integer insertStatisticsAssemble(StatisticsAssembleDO statisticsAssembleDO);

	Integer updateStatisticsAssemble(StatisticsAssembleDO statisticsAssembleDO);

	Integer deleteStatisticsAssembleById(Integer id);
	
	Integer statisticsCurrentDay(StatisticsAssembleQuery statisticsAssembleQuery);
}
