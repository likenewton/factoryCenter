package cn.yunovo.iov.factory.biz.dac.flogistics.service;

import java.util.Map;

import cn.yunovo.iov.boot.autoconfigure.request.select.Condition;
import cn.yunovo.iov.factory.biz.dac.flogistics.model.FlogisticsResourceDO;
import cn.yunovo.iov.factory.biz.dac.flogistics.model.FlogisticsResourceDTO;
import cn.yunovo.iov.factory.biz.dac.flogistics.model.FlogisticsResourceQuery;

/**
 * Service层：相对具体的业务逻辑服务层。对多个Manager的组合复用 。
 * 
 * @author huangzz
 *
 */
public interface FlogisticsResourceService {

	FlogisticsResourceDTO getFlogisticsResourceById(Integer id);
	
	FlogisticsResourceDTO queryFlogisticsResource(FlogisticsResourceQuery flogisticsResourceQuery);

	Object selectFlogisticsResource(FlogisticsResourceQuery flogisticsResourceQuery, Map<String, Condition> conditionMap);
	
	Integer insertFlogisticsResource(FlogisticsResourceDO flogisticsResourceDO);

	Integer deleteFlogisticsResourceById(Integer id);

	Integer updateFlogisticsResource(FlogisticsResourceDO flogisticsResourceDO);
	
    Integer deleteFlogisticsResource(FlogisticsResourceDO flogisticsResourceDO);

}
