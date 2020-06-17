package cn.yunovo.iov.device.zen.biz.report.packing.service;

import java.util.Map;

import cn.yunovo.iov.boot.autoconfigure.request.select.Condition;
import cn.yunovo.iov.device.zen.biz.report.packing.model.PackingDO;
import cn.yunovo.iov.device.zen.biz.report.packing.model.PackingDTO;
import cn.yunovo.iov.device.zen.biz.report.packing.model.PackingQuery;

/**
 * Service层：相对具体的业务逻辑服务层。对多个Manager的组合复用 。
 * 
 * @author huangzz
 *
 */
public interface PackingService {

	PackingDTO getPackingById(Integer id);

	Object selectPacking(PackingQuery packingQuery, Map<String, Condition> conditionMap);
	
	Integer insertPacking(PackingDO packingDO);

	Integer deletePackingById(Integer id);

	Integer updatePacking(PackingDO packingDO);

}
