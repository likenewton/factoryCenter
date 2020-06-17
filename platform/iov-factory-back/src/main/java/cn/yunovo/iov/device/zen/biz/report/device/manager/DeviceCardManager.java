package cn.yunovo.iov.device.zen.biz.report.device.manager;

import java.util.List;
import java.util.Map;

import cn.yunovo.iov.device.zen.biz.report.device.mapper.DeviceCardMapper;
import cn.yunovo.iov.device.zen.biz.report.device.model.DeviceCardDO;
import cn.yunovo.iov.device.zen.biz.report.device.model.DeviceCardDTO;
import cn.yunovo.iov.device.zen.biz.report.device.model.DeviceCardQuery;
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
public class DeviceCardManager {

	@Autowired
	private DeviceCardMapper deviceCardMapper;

	public DeviceCardDTO getDeviceCardById(Integer id) {
		DeviceCardDO deviceCardDO = deviceCardMapper.getDeviceCardById(id);
		DeviceCardDTO DeviceCardDTO = BeanMapper.map(deviceCardDO, DeviceCardDTO.class);
		return DeviceCardDTO;
	}

	public List<DeviceCardDTO> selectDeviceCard(DeviceCardQuery deviceCardQuery, Map<String, Condition> conditionMap) {
		List<DeviceCardDO> list = deviceCardMapper.selectDeviceCard(deviceCardQuery, conditionMap);
		return BeanMapper.mapList(list, DeviceCardDTO.class);
	}
	public Integer insertDeviceCard(DeviceCardDO deviceCardDO) {
		return deviceCardMapper.insertDeviceCard(deviceCardDO);
	}

	public Integer deleteDeviceCardById(Integer id) {
		return deviceCardMapper.deleteDeviceCardById(id);
	}

	public Integer updateDeviceCard(DeviceCardDO deviceCardDO) {
		return deviceCardMapper.updateDeviceCard(deviceCardDO);
	}
}