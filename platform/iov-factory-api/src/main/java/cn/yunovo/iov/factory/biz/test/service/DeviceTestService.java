package cn.yunovo.iov.factory.biz.test.service;

import java.util.Map;

import cn.yunovo.iov.boot.autoconfigure.request.select.Condition;
import cn.yunovo.iov.factory.biz.test.model.DeviceTestDO;
import cn.yunovo.iov.factory.biz.test.model.DeviceTestDTO;
import cn.yunovo.iov.factory.biz.test.model.DeviceTestQuery;
import cn.yunovo.iov.factory.biz.test.model.DeviceTestVO;

/**
 * Service层：相对具体的业务逻辑服务层。对多个Manager的组合复用 。
 * 
 * @author huangzz
 *
 */
public interface DeviceTestService {

	DeviceTestDTO getDeviceTestById(Integer id);

	Object selectDeviceTest(DeviceTestQuery deviceTestQuery, Map<String, Condition> conditionMap);
	
	Integer insertDeviceTest(DeviceTestDO deviceTestDO);

	Integer deleteDeviceTestById(Integer id);

	Integer updateDeviceTest(DeviceTestDO deviceTestDO);
	
	void saveDevice(DeviceTestVO deviceTestVO);

}
