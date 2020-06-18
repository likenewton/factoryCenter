package cn.yunovo.iov.factory.biz.shipping.shipping.service;

import java.util.List;
import java.util.Map;

import cn.yunovo.iov.boot.autoconfigure.request.select.Condition;
import cn.yunovo.iov.factory.biz.shipping.shipping.model.ShippingDeviceDO;
import cn.yunovo.iov.factory.biz.shipping.shipping.model.ShippingDeviceDTO;
import cn.yunovo.iov.factory.biz.shipping.shipping.model.ShippingDeviceQuery;

/**
 * Service层：相对具体的业务逻辑服务层。对多个Manager的组合复用 。
 * 
 * @author huangzz
 *
 */
public interface ShippingDeviceService {

	ShippingDeviceDTO getShippingDeviceById(Integer id);

	Object selectShippingDevice(ShippingDeviceQuery shippingDeviceQuery, Map<String, Condition> conditionMap);
	
	Integer insertShippingDevice(ShippingDeviceDO shippingDeviceDO);

	Integer deleteShippingDeviceById(Integer id);
	
	Integer deleteShippingDeviceByShippingId(Integer shippingId);

	Integer updateShippingDevice(ShippingDeviceDO shippingDeviceDO);
	
	Integer batchInsertDevice(List<ShippingDeviceDO> list);

}
