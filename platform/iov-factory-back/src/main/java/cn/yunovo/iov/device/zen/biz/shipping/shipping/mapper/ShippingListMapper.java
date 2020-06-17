package cn.yunovo.iov.device.zen.biz.shipping.shipping.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import cn.yunovo.iov.device.zen.biz.shipping.shipping.model.ShippingListDO;
import cn.yunovo.iov.device.zen.biz.shipping.shipping.model.ShippingListQuery;
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
public interface ShippingListMapper extends GeneralMapper<ShippingListDO> {

	ShippingListDO getShippingListById(Integer id);
	
	List<ShippingListDO> selectShippingList(@Param("query")ShippingListQuery shippingListQuery, @Param("condition")Map<String, Condition> conditionMap);
	Integer insertShippingList(ShippingListDO shippingListDO);

	Integer updateShippingList(ShippingListDO shippingListDO);

	Integer deleteShippingListById(Integer id);
	
	Integer statisticsCurrentDay(ShippingListQuery shippingListQuery);
}
