package cn.yunovo.iov.device.zen.biz.statistics.shipping.service.impl;

import java.util.List;
import java.util.Map;

import cn.yunovo.iov.boot.autoconfigure.request.SearchCondition;
import cn.yunovo.iov.framework.commons.beanutils.bean.BeanMapper;
import cn.yunovo.iov.framework.web.PageInfo;
import cn.yunovo.iov.boot.autoconfigure.request.select.Condition;
import cn.yunovo.iov.device.zen.biz.statistics.shipping.manager.StatisticsShippingListManager;
import cn.yunovo.iov.device.zen.biz.statistics.shipping.model.StatisticsShippingListVO;
import cn.yunovo.iov.device.zen.biz.statistics.shipping.model.StatisticsShippingListDO;
import cn.yunovo.iov.device.zen.biz.statistics.shipping.model.StatisticsShippingListDTO;
import cn.yunovo.iov.device.zen.biz.statistics.shipping.model.StatisticsShippingListQuery;
import cn.yunovo.iov.device.zen.biz.statistics.shipping.service.StatisticsShippingListService;
import cn.yunovo.iov.device.zen.framework.LoginInfoUtil;
import cn.yunovo.iov.device.zen.framework.dac.DacHelper;
import cn.yunovo.iov.device.zen.framework.dac.bean.LoginUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;

@Service
public class StatisticsShippingListServiceImpl implements StatisticsShippingListService {

	@Autowired
	private StatisticsShippingListManager statisticsShippingListManager;

	@Autowired
	private LoginInfoUtil loginInfoUtil;

	@Override
	public StatisticsShippingListDTO getStatisticsShippingListById(Integer id) {
		return statisticsShippingListManager.getStatisticsShippingListById(id);
	}

	@Override
	public StatisticsShippingListDTO queryStatisticsShippingList(StatisticsShippingListQuery statisticsShippingListQuery) {
		return statisticsShippingListManager.queryStatisticsShippingList(statisticsShippingListQuery);
	}

	@Override
	// @OpLog( opType=OpTypeEnum.QUERY, opName = "根据条件查询[统计发货管理]信息")
	public Object selectStatisticsShippingList(StatisticsShippingListQuery statisticsShippingListQuery, Map<String, Condition> conditionMap, Boolean isDac) {
		Page<Object> page = null;

		if(isDac) {
			DacHelper.setUser(LoginUser.create().userId(loginInfoUtil.getLoginBaseInfo().getLoginName()).userType(loginInfoUtil.getLoginBaseInfo().getUserType()));
		}

		// [分页查询]
		page = SearchCondition.conditionByPages(conditionMap);

		// [排序查询,不能使用OrderByHelper工具类,存在SQL中 limit 和 order by 顺序错误]
		// SearchCondition.conditionByOrder(conditionMap);

		// [分组查询],[偏移量查询]暂不实现支持查询

		// [执行数据库查询]
		List<StatisticsShippingListDTO> statisticsShippingListBOList = statisticsShippingListManager.selectStatisticsShippingList(statisticsShippingListQuery, conditionMap);
		List<StatisticsShippingListVO> statisticsShippingListVOList = BeanMapper.mapList(statisticsShippingListBOList, StatisticsShippingListVO.class);

		// [limit 查询]
		if (SearchCondition.conditionBylimit(conditionMap)) {
			return statisticsShippingListVOList;
		}

		// 返回分页
		if (null != page) {
			PageInfo<Page<Object>> pInfo = SearchCondition.returnPage(page);
			page.addAll(statisticsShippingListVOList);
			return pInfo;
		}

		// 没符合条件查询
		return statisticsShippingListVOList;
	}

	@Override
	public Integer insertStatisticsShippingList(StatisticsShippingListDO statisticsShippingListDO) {
		return statisticsShippingListManager.insertStatisticsShippingList(statisticsShippingListDO);
	}

	@Override
	public Integer deleteStatisticsShippingListById(Integer id) {
		return statisticsShippingListManager.deleteStatisticsShippingListById(id);
	}

	@Override
	public Integer updateStatisticsShippingList(StatisticsShippingListDO statisticsShippingListDO) {
		return statisticsShippingListManager.updateStatisticsShippingList(statisticsShippingListDO);
	}

	@Override
	public Integer statisticsCurrentDay(StatisticsShippingListQuery statisticsShippingListQuery) {
		return statisticsShippingListManager.statisticsCurrentDay(statisticsShippingListQuery);
	}

	@Override
	public Object selectStatisticsShippingList(StatisticsShippingListQuery statisticsShippingListQuery, Map<String, Condition> conditionMap) {
		return selectStatisticsShippingList(statisticsShippingListQuery, conditionMap, true);
	}
}
