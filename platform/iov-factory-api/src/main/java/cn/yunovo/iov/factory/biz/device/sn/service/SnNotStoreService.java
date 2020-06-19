package cn.yunovo.iov.factory.biz.device.sn.service;

import java.util.Map;

import cn.yunovo.iov.boot.autoconfigure.request.select.Condition;
import cn.yunovo.iov.factory.biz.device.sn.model.SnNotStoreDO;
import cn.yunovo.iov.factory.biz.device.sn.model.SnNotStoreDTO;
import cn.yunovo.iov.factory.biz.device.sn.model.SnNotStoreQuery;

/**
 * Service层：相对具体的业务逻辑服务层。对多个Manager的组合复用 。
 * 
 * @author huangzz
 *
 */
public interface SnNotStoreService {

	SnNotStoreDTO getSnNotStoreById(Integer id);
	
	SnNotStoreDTO querySnNotStore(SnNotStoreQuery snNotStoreQuery);

	Object selectSnNotStore(SnNotStoreQuery snNotStoreQuery, Map<String, Condition> conditionMap);
	
	Integer insertSnNotStore(SnNotStoreDO snNotStoreDO);

	Integer deleteSnNotStoreById(Integer id);

	Integer updateSnNotStore(SnNotStoreDO snNotStoreDO);
	
    Integer deleteSnNotStore(SnNotStoreDO snNotStoreDO);

}
