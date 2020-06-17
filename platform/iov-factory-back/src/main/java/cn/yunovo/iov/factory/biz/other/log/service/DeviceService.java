package cn.yunovo.iov.factory.biz.other.log.service;

import java.util.Map;

import cn.yunovo.iov.boot.autoconfigure.request.select.Condition;
import cn.yunovo.iov.factory.biz.other.log.model.DeviceDO;
import cn.yunovo.iov.factory.biz.other.log.model.DeviceDTO;
import cn.yunovo.iov.factory.biz.other.log.model.DeviceQuery;

/**
 * Service层：相对具体的业务逻辑服务层。对多个Manager的组合复用 。
 * 
 * @author huangzz
 *
 */
public interface DeviceService {

	DeviceDTO getDeviceById(Integer id);

	Object selectDevice(DeviceQuery deviceQuery, Map<String, Condition> conditionMap);
	
	Integer insertDevice(DeviceDO deviceDO);

	Integer deleteDeviceById(Integer id);

	Integer updateDevice(DeviceDO deviceDO);

}
