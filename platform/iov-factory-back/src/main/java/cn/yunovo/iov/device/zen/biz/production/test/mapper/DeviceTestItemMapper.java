package cn.yunovo.iov.device.zen.biz.production.test.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import cn.yunovo.iov.device.zen.biz.production.test.model.DeviceTestItemDO;
import cn.yunovo.iov.device.zen.biz.production.test.model.DeviceTestItemQuery;
import cn.yunovo.iov.boot.autoconfigure.mybatis.GeneralMapper;
import cn.yunovo.iov.boot.autoconfigure.request.select.Condition;


/**
 * mapper 包为dao包，
 * 注意：方法入参规则
 * 
 * @author huangzz
 *
 */
@Mapper
public interface DeviceTestItemMapper extends GeneralMapper<DeviceTestItemDO> {

	DeviceTestItemDO getDeviceTestItemById(Integer id);
	
	List<DeviceTestItemDO> selectDeviceTestItem(@Param("query")DeviceTestItemQuery deviceTestItemQuery, @Param("condition")Map<String, Condition> conditionMap);
	Integer insertDeviceTestItem(DeviceTestItemDO deviceTestItemDO);

	Integer updateDeviceTestItem(DeviceTestItemDO deviceTestItemDO);

	Integer deleteDeviceTestItemById(Integer id);
}
