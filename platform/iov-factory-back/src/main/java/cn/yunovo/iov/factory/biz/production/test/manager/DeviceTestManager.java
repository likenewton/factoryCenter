package cn.yunovo.iov.factory.biz.production.test.manager;

import java.util.List;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.yunovo.iov.framework.commons.beanutils.bean.BeanMapper;
import cn.yunovo.iov.boot.autoconfigure.request.select.Condition;
import cn.yunovo.iov.factory.biz.production.test.mapper.DeviceTestMapper;
import cn.yunovo.iov.factory.biz.production.test.model.DeviceTestDO;
import cn.yunovo.iov.factory.biz.production.test.model.DeviceTestDTO;
import cn.yunovo.iov.factory.biz.production.test.model.DeviceTestQuery;

/**
 * Manager层：通用业务处理层
 * @author huangzz
 *
 */
@Component
public class DeviceTestManager {

	@Autowired
	private DeviceTestMapper deviceTestMapper;

	public DeviceTestDTO getDeviceTestById(Integer id) {
		DeviceTestDO deviceTestDO = deviceTestMapper.getDeviceTestById(id);
		DeviceTestDTO DeviceTestDTO = BeanMapper.map(deviceTestDO, DeviceTestDTO.class);
		return DeviceTestDTO;
	}

	public List<DeviceTestDTO> selectDeviceTest(DeviceTestQuery deviceTestQuery, Map<String, Condition> conditionMap) {
		List<DeviceTestDO> list = deviceTestMapper.selectDeviceTest(deviceTestQuery, conditionMap);
		return BeanMapper.mapList(list, DeviceTestDTO.class);
	}
	public Integer insertDeviceTest(DeviceTestDO deviceTestDO) {
		deviceTestDO.setCreateTime(new Date());
		deviceTestDO.setUpdateTime(new Date());
		return deviceTestMapper.insertDeviceTest(deviceTestDO);
	}

	public Integer deleteDeviceTestById(Integer id) {
		return deviceTestMapper.deleteDeviceTestById(id);
	}

	public Integer updateDeviceTest(DeviceTestDO deviceTestDO) {
		deviceTestDO.setUpdateTime(new Date());
		return deviceTestMapper.updateDeviceTest(deviceTestDO);
	}
	
	public Integer sumStatisticsDeviceTest(DeviceTestQuery deviceTestQuery) {
		return deviceTestMapper.sumStatisticsDeviceTest(deviceTestQuery);
	}
	
	public Integer sumErrorStatisticsDeviceTest(DeviceTestQuery deviceTestQuery) {
		return deviceTestMapper.sumErrorStatisticsDeviceTest(deviceTestQuery);
	}
}