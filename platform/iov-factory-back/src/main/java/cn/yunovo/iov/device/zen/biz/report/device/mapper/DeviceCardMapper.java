package cn.yunovo.iov.device.zen.biz.report.device.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import cn.yunovo.iov.device.zen.biz.report.device.model.DeviceCardDO;
import cn.yunovo.iov.device.zen.biz.report.device.model.DeviceCardQuery;
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
public interface DeviceCardMapper extends GeneralMapper<DeviceCardDO> {

	DeviceCardDO getDeviceCardById(Integer id);
	
	List<DeviceCardDO> selectDeviceCard(@Param("query")DeviceCardQuery deviceCardQuery, @Param("condition")Map<String, Condition> conditionMap);
	Integer insertDeviceCard(DeviceCardDO deviceCardDO);

	Integer updateDeviceCard(DeviceCardDO deviceCardDO);

	Integer deleteDeviceCardById(Integer id);
}
