package cn.yunovo.iov.zen.biz.test.service;

import java.util.List;
import java.util.Map;

import cn.yunovo.iov.boot.autoconfigure.request.select.Condition;
import cn.yunovo.iov.zen.biz.test.model.DeviceTestItemDO;
import cn.yunovo.iov.zen.biz.test.model.DeviceTestItemDTO;
import cn.yunovo.iov.zen.biz.test.model.DeviceTestItemQuery;

/**
 * Service层：相对具体的业务逻辑服务层。对多个Manager的组合复用 。
 * 
 * @author huangzz
 *
 */
public interface DeviceTestItemService {

	DeviceTestItemDTO getDeviceTestItemById(Integer id);

	Object selectDeviceTestItem(DeviceTestItemQuery deviceTestItemQuery, Map<String, Condition> conditionMap);
	
	Integer insertDeviceTestItem(DeviceTestItemDO deviceTestItemDO);

	Integer deleteDeviceTestItemById(Integer id);

	Integer updateDeviceTestItem(DeviceTestItemDO deviceTestItemDO);
	
	Integer batchInsertDeviceTestItem(List<DeviceTestItemDO> list);

}
