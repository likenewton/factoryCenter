package cn.yunovo.iov.device.zen.biz.shipping.shipping.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import cn.yunovo.iov.device.zen.biz.shipping.shipping.model.ShippingDeviceDO;
import cn.yunovo.iov.device.zen.biz.shipping.shipping.model.ShippingDeviceQuery;
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
public interface ShippingDeviceMapper extends GeneralMapper<ShippingDeviceDO> {

	ShippingDeviceDO getShippingDeviceById(Integer id);
	
	List<ShippingDeviceDO> selectShippingDevice(@Param("query")ShippingDeviceQuery shippingDeviceQuery, @Param("condition")Map<String, Condition> conditionMap);
	Integer insertShippingDevice(ShippingDeviceDO shippingDeviceDO);

	Integer updateShippingDevice(ShippingDeviceDO shippingDeviceDO);

	Integer deleteShippingDeviceById(Integer id);
	
	 Integer batchInsertDevice(List<ShippingDeviceDO> list);
	 
	 Integer deleteShippingDeviceByShippingId(Integer shippingId);
}
