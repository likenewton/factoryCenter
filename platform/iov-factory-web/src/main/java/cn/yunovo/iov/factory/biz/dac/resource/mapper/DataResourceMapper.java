package cn.yunovo.iov.factory.biz.dac.resource.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import cn.yunovo.iov.boot.autoconfigure.mybatis.GeneralMapper;
import cn.yunovo.iov.boot.autoconfigure.request.select.Condition;
import cn.yunovo.iov.factory.biz.dac.resource.model.DataResourceDO;
import cn.yunovo.iov.factory.biz.dac.resource.model.DataResourceQuery;


/**
 * mapper 包为dao包，
 * 注意：方法入参规则
 * 
 * @author huangzz
 *
 */
@Mapper
public interface DataResourceMapper extends GeneralMapper<DataResourceDO> {

	DataResourceDO getDataResourceById(Integer id);
	
	List<DataResourceDO> selectDataResource(@Param("query")DataResourceQuery dataResourceQuery, @Param("condition")Map<String, Condition> conditionMap);
	
	DataResourceDO queryDataResource(@Param("query")DataResourceQuery dataResourceQuery);
	
	Integer insertDataResource(DataResourceDO dataResourceDO);

	Integer updateDataResource(DataResourceDO dataResourceDO);

	Integer deleteDataResourceById(Integer id);
	
	Integer deleteDataResource(@Param("item")DataResourceDO dataResourceDO);
}
