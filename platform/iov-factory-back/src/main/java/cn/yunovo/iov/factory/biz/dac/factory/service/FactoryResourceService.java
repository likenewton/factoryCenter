package cn.yunovo.iov.factory.biz.dac.factory.service;

import java.util.Map;

import cn.yunovo.iov.boot.autoconfigure.request.select.Condition;
import cn.yunovo.iov.factory.biz.dac.factory.model.FactoryResourceDO;
import cn.yunovo.iov.factory.biz.dac.factory.model.FactoryResourceDTO;
import cn.yunovo.iov.factory.biz.dac.factory.model.FactoryResourceQuery;

/**
 * Service层：相对具体的业务逻辑服务层。对多个Manager的组合复用 。
 * 
 * @author huangzz
 *
 */
public interface FactoryResourceService {

	FactoryResourceDTO getFactoryResourceById(Integer id);
	
	FactoryResourceDTO queryFactoryResource(FactoryResourceQuery factoryResourceQuery);

	Object selectFactoryResource(FactoryResourceQuery factoryResourceQuery, Map<String, Condition> conditionMap);
	
	Integer insertFactoryResource(FactoryResourceDO factoryResourceDO);

	Integer deleteFactoryResourceById(Integer id);

	Integer updateFactoryResource(FactoryResourceDO factoryResourceDO);
	
    Integer deleteFactoryResource(FactoryResourceDO factoryResourceDO);

}
