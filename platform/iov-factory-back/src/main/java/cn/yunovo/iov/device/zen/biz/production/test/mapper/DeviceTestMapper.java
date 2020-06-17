package cn.yunovo.iov.device.zen.biz.production.test.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import cn.yunovo.iov.device.zen.biz.production.test.model.DeviceTestDO;
import cn.yunovo.iov.device.zen.biz.production.test.model.DeviceTestQuery;
import cn.yunovo.iov.boot.autoconfigure.mybatis.GeneralMapper;
import cn.yunovo.iov.boot.autoconfigure.request.select.Condition;

/**
 * mapper 包为dao包， 注意：方法入参规则
 * 
 * @author huangzz
 *
 */
@Mapper
public interface DeviceTestMapper extends GeneralMapper<DeviceTestDO> {

	DeviceTestDO getDeviceTestById(Integer id);

	List<DeviceTestDO> selectDeviceTest(@Param("query") DeviceTestQuery deviceTestQuery, @Param("condition") Map<String, Condition> conditionMap);

	Integer insertDeviceTest(DeviceTestDO deviceTestDO);

	Integer sumStatisticsDeviceTest(DeviceTestQuery deviceTestQuery);
	
	Integer sumErrorStatisticsDeviceTest(DeviceTestQuery deviceTestQuery);

	Integer updateDeviceTest(DeviceTestDO deviceTestDO);

	Integer deleteDeviceTestById(Integer id);
}
