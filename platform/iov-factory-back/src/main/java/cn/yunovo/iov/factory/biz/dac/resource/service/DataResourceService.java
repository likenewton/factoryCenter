package cn.yunovo.iov.factory.biz.dac.resource.service;

import java.util.Map;

import cn.yunovo.iov.boot.autoconfigure.request.select.Condition;
import cn.yunovo.iov.factory.biz.dac.resource.model.DataResourceDO;
import cn.yunovo.iov.factory.biz.dac.resource.model.DataResourceDTO;
import cn.yunovo.iov.factory.biz.dac.resource.model.DataResourceQuery;

/**
 * Service层：相对具体的业务逻辑服务层。对多个Manager的组合复用 。
 * 
 * @author huangzz
 *
 */
public interface DataResourceService {

	DataResourceDTO getDataResourceById(Integer id);
	
	DataResourceDTO queryDataResource(DataResourceQuery dataResourceQuery);

	Object selectDataResource(DataResourceQuery dataResourceQuery, Map<String, Condition> conditionMap);
	
	Integer insertDataResource(DataResourceDO dataResourceDO);

	Integer deleteDataResourceById(Integer id);

	Integer updateDataResource(DataResourceDO dataResourceDO);
	
    Integer deleteDataResource(DataResourceDO dataResourceDO);

}
