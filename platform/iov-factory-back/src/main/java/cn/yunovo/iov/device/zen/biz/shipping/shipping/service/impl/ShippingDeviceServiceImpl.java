package cn.yunovo.iov.device.zen.biz.shipping.shipping.service.impl;

import java.util.List;
import java.util.Map;

import cn.yunovo.iov.boot.autoconfigure.auditlog.annotation.OpLog;
import cn.yunovo.iov.boot.autoconfigure.auditlog.enums.OpTypeEnum;
import cn.yunovo.iov.boot.autoconfigure.request.SearchCondition;
import cn.yunovo.iov.framework.commons.beanutils.bean.BeanMapper;
import cn.yunovo.iov.framework.web.PageInfo;
import cn.yunovo.iov.boot.autoconfigure.request.select.Condition;
import cn.yunovo.iov.device.zen.biz.shipping.shipping.manager.ShippingDeviceManager;
import cn.yunovo.iov.device.zen.biz.shipping.shipping.model.ShippingDeviceVO;
import cn.yunovo.iov.device.zen.biz.shipping.shipping.model.ShippingDeviceDO;
import cn.yunovo.iov.device.zen.biz.shipping.shipping.model.ShippingDeviceDTO;
import cn.yunovo.iov.device.zen.biz.shipping.shipping.model.ShippingDeviceQuery;
import cn.yunovo.iov.device.zen.biz.shipping.shipping.service.ShippingDeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;

@Service
public class ShippingDeviceServiceImpl implements ShippingDeviceService {

	@Autowired
	private ShippingDeviceManager shippingDeviceManager;

	@Override
	public ShippingDeviceDTO getShippingDeviceById(Integer id) {
		return shippingDeviceManager.getShippingDeviceById(id);
	}
	
	@Override
	@OpLog( opType=OpTypeEnum.QUERY, opName = "根据条件查询[发货设备IMEI清单]信息")
	public Object selectShippingDevice(ShippingDeviceQuery shippingDeviceQuery, Map<String, Condition> conditionMap) {
		Page<Object> page = null;

		// [分页查询]
		page = SearchCondition.conditionByPages(conditionMap);

		// [排序查询,不能使用OrderByHelper工具类,存在SQL中 limit 和 order by 顺序错误]
		// SearchCondition.conditionByOrder(conditionMap);

		// [分组查询],[偏移量查询]暂不实现支持查询

		// [执行数据库查询]
		List<ShippingDeviceDTO> shippingDeviceBOList = shippingDeviceManager.selectShippingDevice(shippingDeviceQuery, conditionMap);
		List<ShippingDeviceVO> shippingDeviceVOList = BeanMapper.mapList(shippingDeviceBOList, ShippingDeviceVO.class);

		// [limit 查询]
		if (SearchCondition.conditionBylimit(conditionMap)) {
			return shippingDeviceVOList;
		}

		// 返回分页
		if (null != page) {
			PageInfo<Page<Object>> pInfo = SearchCondition.returnPage(page);
			page.addAll(shippingDeviceVOList);
			return pInfo;
		}

		// 没符合条件查询
		return shippingDeviceVOList;
	}
	@Override
	public Integer insertShippingDevice(ShippingDeviceDO shippingDeviceDO) {
		return shippingDeviceManager.insertShippingDevice(shippingDeviceDO);
	}

	@Override
	public Integer deleteShippingDeviceById(Integer id) {
		return shippingDeviceManager.deleteShippingDeviceById(id);
	}

	@Override
	public Integer updateShippingDevice(ShippingDeviceDO shippingDeviceDO) {
		return shippingDeviceManager.updateShippingDevice(shippingDeviceDO);
	}

	@Override
	public Integer batchInsertDevice(List<ShippingDeviceDO> list) {
		return shippingDeviceManager.batchInsertDevice(list);
	}

	@Override
	public Integer deleteShippingDeviceByShippingId(Integer shippingId) {
		return shippingDeviceManager.deleteShippingDeviceByShippingId(shippingId);
	}
}
