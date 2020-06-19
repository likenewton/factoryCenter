package cn.yunovo.iov.factory.biz.device.sn.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import cn.yunovo.iov.factory.biz.device.sn.model.SnNotStoreDO;
import cn.yunovo.iov.factory.biz.device.sn.model.SnNotStoreQuery;
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
public interface SnNotStoreMapper extends GeneralMapper<SnNotStoreDO> {

	SnNotStoreDO getSnNotStoreById(Integer id);
	
	List<SnNotStoreDO> selectSnNotStore(@Param("query")SnNotStoreQuery snNotStoreQuery, @Param("condition")Map<String, Condition> conditionMap);
	
	SnNotStoreDO querySnNotStore(@Param("query")SnNotStoreQuery snNotStoreQuery);
	
	Integer insertSnNotStore(SnNotStoreDO snNotStoreDO);

	Integer updateSnNotStore(SnNotStoreDO snNotStoreDO);

	Integer deleteSnNotStoreById(Integer id);
	
	Integer deleteSnNotStore(@Param("item")SnNotStoreDO snNotStoreDO);
}
