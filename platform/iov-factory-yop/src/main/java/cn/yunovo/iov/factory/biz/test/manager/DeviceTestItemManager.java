package cn.yunovo.iov.factory.biz.test.manager;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.yunovo.iov.framework.commons.beanutils.bean.BeanMapper;
import cn.yunovo.iov.boot.autoconfigure.request.select.Condition;
import cn.yunovo.iov.factory.biz.test.mapper.DeviceTestItemMapper;
import cn.yunovo.iov.factory.biz.test.model.DeviceTestItemDO;
import cn.yunovo.iov.factory.biz.test.model.DeviceTestItemDTO;
import cn.yunovo.iov.factory.biz.test.model.DeviceTestItemQuery;

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
	
	public Integer batchInsertDeviceTestItem(List<DeviceTestItemDO> list) {
		return deviceTestItemMapper.batchInsertDeviceTestItem(list);
	}
}