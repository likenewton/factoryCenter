package cn.yunovo.iov.device.zen.biz.shipping.shipping.service;

import java.util.Map;

import cn.yunovo.iov.boot.autoconfigure.request.select.Condition;
import cn.yunovo.iov.device.zen.biz.shipping.shipping.model.ShippingListDO;
import cn.yunovo.iov.device.zen.biz.shipping.shipping.model.ShippingListDTO;
import cn.yunovo.iov.device.zen.biz.shipping.shipping.model.ShippingListQuery;

/**
 * Service层：相对具体的业务逻辑服务层。对多个Manager的组合复用 。
 * 
 * @author huangzz
 *
 */
public interface ShippingListService {

	ShippingListDTO getShippingListById(Integer id);

	Object selectShippingList(ShippingListQuery shippingListQuery, Map<String, Condition> conditionMap);

	Object selectShippingList(ShippingListQuery shippingListQuery, Map<String, Condition> conditionMap, Boolean isDac);

	Integer insertShippingList(ShippingListDO shippingListDO);

	Integer deleteShippingListById(Integer id);

	Integer updateShippingList(ShippingListDO shippingListDO);

	Integer statisticsCurrentDay(ShippingListQuery shippingListQuery);

}
