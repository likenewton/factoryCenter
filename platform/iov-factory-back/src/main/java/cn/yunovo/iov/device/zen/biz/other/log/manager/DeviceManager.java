package cn.yunovo.iov.device.zen.biz.other.log.manager;

import java.util.List;
import java.util.Map;

import cn.yunovo.iov.device.zen.biz.other.log.mapper.DeviceMapper;
import cn.yunovo.iov.device.zen.biz.other.log.model.DeviceDO;
import cn.yunovo.iov.device.zen.biz.other.log.model.DeviceDTO;
import cn.yunovo.iov.device.zen.biz.other.log.model.DeviceQuery;
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
public class DeviceManager {

	@Autowired
	private DeviceMapper deviceMapper;

	public DeviceDTO getDeviceById(Integer id) {
		DeviceDO deviceDO = deviceMapper.getDeviceById(id);
		DeviceDTO DeviceDTO = BeanMapper.map(deviceDO, DeviceDTO.class);
		return DeviceDTO;
	}

	public List<DeviceDTO> selectDevice(DeviceQuery deviceQuery, Map<String, Condition> conditionMap) {
		List<DeviceDO> list = deviceMapper.selectDevice(deviceQuery, conditionMap);
		return BeanMapper.mapList(list, DeviceDTO.class);
	}
	public Integer insertDevice(DeviceDO deviceDO) {
		return deviceMapper.insertDevice(deviceDO);
	}

	public Integer deleteDeviceById(Integer id) {
		return deviceMapper.deleteDeviceById(id);
	}

	public Integer updateDevice(DeviceDO deviceDO) {
		return deviceMapper.updateDevice(deviceDO);
	}
}