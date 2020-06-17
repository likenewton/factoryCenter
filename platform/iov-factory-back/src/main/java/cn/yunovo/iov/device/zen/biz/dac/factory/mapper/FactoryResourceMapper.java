package cn.yunovo.iov.device.zen.biz.dac.factory.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import cn.yunovo.iov.device.zen.biz.dac.factory.model.FactoryResourceDO;
import cn.yunovo.iov.device.zen.biz.dac.factory.model.FactoryResourceQuery;
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
public interface FactoryResourceMapper extends GeneralMapper<FactoryResourceDO> {

	FactoryResourceDO getFactoryResourceById(Integer id);
	
	List<FactoryResourceDO> selectFactoryResource(@Param("query")FactoryResourceQuery factoryResourceQuery, @Param("condition")Map<String, Condition> conditionMap);
	
	FactoryResourceDO queryFactoryResource(@Param("query")FactoryResourceQuery factoryResourceQuery);
	
	Integer insertFactoryResource(FactoryResourceDO factoryResourceDO);

	Integer updateFactoryResource(FactoryResourceDO factoryResourceDO);

	Integer deleteFactoryResourceById(Integer id);
	
	Integer deleteFactoryResource(@Param("item")FactoryResourceDO factoryResourceDO);
}
