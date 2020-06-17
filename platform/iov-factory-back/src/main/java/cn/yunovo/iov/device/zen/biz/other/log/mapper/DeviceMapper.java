package cn.yunovo.iov.device.zen.biz.other.log.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import cn.yunovo.iov.device.zen.biz.other.log.model.DeviceDO;
import cn.yunovo.iov.device.zen.biz.other.log.model.DeviceQuery;
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
public interface DeviceMapper extends GeneralMapper<DeviceDO> {

	DeviceDO getDeviceById(Integer id);
	
	List<DeviceDO> selectDevice(@Param("query")DeviceQuery deviceQuery, @Param("condition")Map<String, Condition> conditionMap);
	Integer insertDevice(DeviceDO deviceDO);

	Integer updateDevice(DeviceDO deviceDO);

	Integer deleteDeviceById(Integer id);
}
