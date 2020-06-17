package cn.yunovo.iov.device.zen.biz.report.device.service;

import java.util.Map;

import cn.yunovo.iov.boot.autoconfigure.request.select.Condition;
import cn.yunovo.iov.device.zen.biz.report.device.model.DeviceCardDO;
import cn.yunovo.iov.device.zen.biz.report.device.model.DeviceCardDTO;
import cn.yunovo.iov.device.zen.biz.report.device.model.DeviceCardQuery;

/**
 * Service层：相对具体的业务逻辑服务层。对多个Manager的组合复用 。
 * 
 * @author huangzz
 *
 */
public interface DeviceCardService {

	DeviceCardDTO getDeviceCardById(Integer id);

	Object selectDeviceCard(DeviceCardQuery deviceCardQuery, Map<String, Condition> conditionMap);
	
	Integer insertDeviceCard(DeviceCardDO deviceCardDO);

	Integer deleteDeviceCardById(Integer id);

	Integer updateDeviceCard(DeviceCardDO deviceCardDO);

}
