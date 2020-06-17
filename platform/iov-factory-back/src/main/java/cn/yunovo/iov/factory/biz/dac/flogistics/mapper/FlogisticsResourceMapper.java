package cn.yunovo.iov.factory.biz.dac.flogistics.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import cn.yunovo.iov.factory.biz.dac.flogistics.model.FlogisticsResourceDO;
import cn.yunovo.iov.factory.biz.dac.flogistics.model.FlogisticsResourceQuery;
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
public interface FlogisticsResourceMapper extends GeneralMapper<FlogisticsResourceDO> {

	FlogisticsResourceDO getFlogisticsResourceById(Integer id);
	
	List<FlogisticsResourceDO> selectFlogisticsResource(@Param("query")FlogisticsResourceQuery flogisticsResourceQuery, @Param("condition")Map<String, Condition> conditionMap);
	
	FlogisticsResourceDO queryFlogisticsResource(@Param("query")FlogisticsResourceQuery flogisticsResourceQuery);
	
	Integer insertFlogisticsResource(FlogisticsResourceDO flogisticsResourceDO);

	Integer updateFlogisticsResource(FlogisticsResourceDO flogisticsResourceDO);

	Integer deleteFlogisticsResourceById(Integer id);
	
	Integer deleteFlogisticsResource(@Param("item")FlogisticsResourceDO flogisticsResourceDO);
}
