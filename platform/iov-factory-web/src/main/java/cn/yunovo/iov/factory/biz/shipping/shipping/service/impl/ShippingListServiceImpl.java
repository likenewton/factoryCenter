package cn.yunovo.iov.factory.biz.shipping.shipping.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;

import cn.yunovo.iov.boot.autoconfigure.request.SearchCondition;
import cn.yunovo.iov.boot.autoconfigure.request.select.Condition;
import cn.yunovo.iov.factory.biz.shipping.shipping.manager.ShippingListManager;
import cn.yunovo.iov.factory.biz.shipping.shipping.model.ShippingListDO;
import cn.yunovo.iov.factory.biz.shipping.shipping.model.ShippingListDTO;
import cn.yunovo.iov.factory.biz.shipping.shipping.model.ShippingListQuery;
import cn.yunovo.iov.factory.biz.shipping.shipping.model.ShippingListVO;
import cn.yunovo.iov.factory.biz.shipping.shipping.service.ShippingListService;
import cn.yunovo.iov.factory.framework.dac.DacHelper;
import cn.yunovo.iov.framework.commons.beanutils.bean.BeanMapper;
import cn.yunovo.iov.framework.web.PageInfo;

@Service
public class ShippingListServiceImpl implements ShippingListService {

	@Autowired
	private ShippingListManager shippingListManager;


	@Override
	public ShippingListDTO getShippingListById(Integer id) {
		return shippingListManager.getShippingListById(id);
	}
	
	@Override
	//@OpLog( opType=OpTypeEnum.QUERY, opName = "根据条件查询[发货导入清单]信息")
	public Object selectShippingList(ShippingListQuery shippingListQuery, Map<String, Condition> conditionMap, Boolean isDac) {
		Page<Object> page = null;
		
		if(!isDac) {
			DacHelper.skip();
		}
		
		// [分页查询]
		page = SearchCondition.conditionByPages(conditionMap);

		// [排序查询,不能使用OrderByHelper工具类,存在SQL中 limit 和 order by 顺序错误]
		// SearchCondition.conditionByOrder(conditionMap);

		// [分组查询],[偏移量查询]暂不实现支持查询

		// [执行数据库查询]
		List<ShippingListDTO> shippingListBOList = shippingListManager.selectShippingList(shippingListQuery, conditionMap);
		List<ShippingListVO> shippingListVOList = BeanMapper.mapList(shippingListBOList, ShippingListVO.class);
		
		// [limit 查询]
		if (SearchCondition.conditionBylimit(conditionMap)) {
			return shippingListVOList;
		}

		// 返回分页
		if (null != page) {
			PageInfo<Page<Object>> pInfo = SearchCondition.returnPage(page);
			page.addAll(shippingListVOList);
			return pInfo;
		}

		// 没符合条件查询
		return shippingListVOList;
	}
	@Override
	public Integer insertShippingList(ShippingListDO shippingListDO) {
		return shippingListManager.insertShippingList(shippingListDO);
	}

	@Override
	public Integer deleteShippingListById(Integer id) {
		return shippingListManager.deleteShippingListById(id);
	}

	@Override
	public Integer updateShippingList(ShippingListDO shippingListDO) {
		return shippingListManager.updateShippingList(shippingListDO);
	}

	@Override
	public Integer statisticsCurrentDay(ShippingListQuery shippingListQuery) {
		return shippingListManager.statisticsCurrentDay(shippingListQuery);
	}

	@Override
	public Object selectShippingList(ShippingListQuery shippingListQuery, Map<String, Condition> conditionMap) {
		return selectShippingList(shippingListQuery, conditionMap, true);
	}
}
