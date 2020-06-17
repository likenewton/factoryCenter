package cn.yunovo.iov.device.zen.biz.shipping.shipping.manager;

import java.util.List;
import java.util.Date;
import java.util.Map;

import cn.yunovo.iov.device.zen.biz.shipping.shipping.mapper.ShippingDeviceMapper;
import cn.yunovo.iov.device.zen.biz.shipping.shipping.model.ShippingDeviceDO;
import cn.yunovo.iov.device.zen.biz.shipping.shipping.model.ShippingDeviceDTO;
import cn.yunovo.iov.device.zen.biz.shipping.shipping.model.ShippingDeviceQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.yunovo.iov.framework.commons.beanutils.bean.BeanMapper;
import cn.yunovo.iov.boot.autoconfigure.request.select.Condition;

/**
 * Manager层：通用业务处理层
 * @author huangzz
 *
 */
@Component
public class ShippingDeviceManager {

	@Autowired
	private ShippingDeviceMapper shippingDeviceMapper;

	public ShippingDeviceDTO getShippingDeviceById(Integer id) {
		ShippingDeviceDO shippingDeviceDO = shippingDeviceMapper.getShippingDeviceById(id);
		ShippingDeviceDTO ShippingDeviceDTO = BeanMapper.map(shippingDeviceDO, ShippingDeviceDTO.class);
		return ShippingDeviceDTO;
	}

	public List<ShippingDeviceDTO> selectShippingDevice(ShippingDeviceQuery shippingDeviceQuery, Map<String, Condition> conditionMap) {
		List<ShippingDeviceDO> list = shippingDeviceMapper.selectShippingDevice(shippingDeviceQuery, conditionMap);
		return BeanMapper.mapList(list, ShippingDeviceDTO.class);
	}
	public Integer insertShippingDevice(ShippingDeviceDO shippingDeviceDO) {
		shippingDeviceDO.setCreateTime(new Date());
		return shippingDeviceMapper.insertShippingDevice(shippingDeviceDO);
	}

	public Integer deleteShippingDeviceById(Integer id) {
		return shippingDeviceMapper.deleteShippingDeviceById(id);
	}

	public Integer updateShippingDevice(ShippingDeviceDO shippingDeviceDO) {
		return shippingDeviceMapper.updateShippingDevice(shippingDeviceDO);
	}
	
	public Integer batchInsertDevice(List<ShippingDeviceDO> list) {
		return shippingDeviceMapper.batchInsertDevice(list);
	}
	
	public Integer deleteShippingDeviceByShippingId(Integer shippingId) {
		return shippingDeviceMapper.deleteShippingDeviceByShippingId(shippingId);
	}
}