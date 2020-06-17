package cn.yunovo.iov.device.zen.biz.production.test.manager;

import java.util.List;
import java.util.Map;

import cn.yunovo.iov.device.zen.biz.production.test.mapper.DeviceTestItemMapper;
import cn.yunovo.iov.device.zen.biz.production.test.model.DeviceTestItemDO;
import cn.yunovo.iov.device.zen.biz.production.test.model.DeviceTestItemDTO;
import cn.yunovo.iov.device.zen.biz.production.test.model.DeviceTestItemQuery;
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
public class DeviceTestItemManager {

	@Autowired
	private DeviceTestItemMapper deviceTestItemMapper;

	public DeviceTestItemDTO getDeviceTestItemById(Integer id) {
		DeviceTestItemDO deviceTestItemDO = deviceTestItemMapper.getDeviceTestItemById(id);
		DeviceTestItemDTO DeviceTestItemDTO = BeanMapper.map(deviceTestItemDO, DeviceTestItemDTO.class);
		return DeviceTestItemDTO;
	}

	public List<DeviceTestItemDTO> selectDeviceTestItem(DeviceTestItemQuery deviceTestItemQuery, Map<String, Condition> conditionMap) {
		List<DeviceTestItemDO> list = deviceTestItemMapper.selectDeviceTestItem(deviceTestItemQuery, conditionMap);
		return BeanMapper.mapList(list, DeviceTestItemDTO.class);
	}
	public Integer insertDeviceTestItem(DeviceTestItemDO deviceTestItemDO) {
		return deviceTestItemMapper.insertDeviceTestItem(deviceTestItemDO);
	}

	public Integer deleteDeviceTestItemById(Integer id) {
		return deviceTestItemMapper.deleteDeviceTestItemById(id);
	}

	public Integer updateDeviceTestItem(DeviceTestItemDO deviceTestItemDO) {
		return deviceTestItemMapper.updateDeviceTestItem(deviceTestItemDO);
	}
}