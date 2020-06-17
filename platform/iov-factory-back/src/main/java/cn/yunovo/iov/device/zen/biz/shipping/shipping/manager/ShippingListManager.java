package cn.yunovo.iov.device.zen.biz.shipping.shipping.manager;

import java.util.List;
import java.util.Date;
import java.util.Map;

import cn.yunovo.iov.device.zen.biz.shipping.shipping.mapper.ShippingListMapper;
import cn.yunovo.iov.device.zen.biz.shipping.shipping.model.ShippingListDO;
import cn.yunovo.iov.device.zen.biz.shipping.shipping.model.ShippingListDTO;
import cn.yunovo.iov.device.zen.biz.shipping.shipping.model.ShippingListQuery;
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
public class ShippingListManager {

	@Autowired
	private ShippingListMapper shippingListMapper;

	public ShippingListDTO getShippingListById(Integer id) {
		ShippingListDO shippingListDO = shippingListMapper.getShippingListById(id);
		ShippingListDTO ShippingListDTO = BeanMapper.map(shippingListDO, ShippingListDTO.class);
		return ShippingListDTO;
	}

	public List<ShippingListDTO> selectShippingList(ShippingListQuery shippingListQuery, Map<String, Condition> conditionMap) {
		List<ShippingListDO> list = shippingListMapper.selectShippingList(shippingListQuery, conditionMap);
		return BeanMapper.mapList(list, ShippingListDTO.class);
	}
	public Integer insertShippingList(ShippingListDO shippingListDO) {
		shippingListDO.setCreateTime(new Date());
		shippingListDO.setUpdateTime(new Date());
		return shippingListMapper.insertShippingList(shippingListDO);
	}

	public Integer deleteShippingListById(Integer id) {
		return shippingListMapper.deleteShippingListById(id);
	}

	public Integer updateShippingList(ShippingListDO shippingListDO) {
		shippingListDO.setUpdateTime(new Date());
		return shippingListMapper.updateShippingList(shippingListDO);
	}
	
	public Integer statisticsCurrentDay(ShippingListQuery shippingListQuery) {
		return shippingListMapper.statisticsCurrentDay(shippingListQuery);
	}
}