package cn.yunovo.iov.device.zen.biz.statistics.shipping.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;

import cn.yunovo.iov.boot.autoconfigure.auditlog.annotation.OpLog;
import cn.yunovo.iov.boot.autoconfigure.auditlog.enums.OpTypeEnum;
import cn.yunovo.iov.boot.autoconfigure.request.SearchCondition;
import cn.yunovo.iov.boot.autoconfigure.request.select.Condition;
import cn.yunovo.iov.device.zen.biz.statistics.shipping.manager.StatisticsShippingManager;
import cn.yunovo.iov.device.zen.biz.statistics.shipping.model.StatisticsShippingDO;
import cn.yunovo.iov.device.zen.biz.statistics.shipping.model.StatisticsShippingDTO;
import cn.yunovo.iov.device.zen.biz.statistics.shipping.model.StatisticsShippingQuery;
import cn.yunovo.iov.device.zen.biz.statistics.shipping.model.StatisticsShippingVO;
import cn.yunovo.iov.device.zen.biz.statistics.shipping.service.StatisticsShippingService;
import cn.yunovo.iov.device.zen.framework.LoginInfoUtil;
import cn.yunovo.iov.device.zen.framework.dac.DacHelper;
import cn.yunovo.iov.device.zen.framework.dac.bean.LoginUser;
import cn.yunovo.iov.framework.commons.beanutils.bean.BeanMapper;
import cn.yunovo.iov.framework.web.PageInfo;

@Service
public class StatisticsShippingServiceImpl implements StatisticsShippingService {

	@Autowired
	private StatisticsShippingManager statisticsShippingManager;
	
	@Autowired
	private LoginInfoUtil loginInfoUtil;

	@Override
	public StatisticsShippingDTO getStatisticsShippingById(Integer id) {
		return statisticsShippingManager.getStatisticsShippingById(id);
	}
	
	@Override
	@OpLog( opType=OpTypeEnum.QUERY, opName = "根据条件查询[统计发货管理]信息")
	public Object selectStatisticsShipping(StatisticsShippingQuery statisticsShippingQuery, Map<String, Condition> conditionMap) {
		Page<Object> page = null;
		
		DacHelper.setUser(LoginUser.create().userId(loginInfoUtil.getLoginBaseInfo().getLoginName()).userType(loginInfoUtil.getLoginBaseInfo().getUserType()));
		
		// [分页查询]
		page = SearchCondition.conditionByPages(conditionMap);

		// [排序查询,不能使用OrderByHelper工具类,存在SQL中 limit 和 order by 顺序错误]
		// SearchCondition.conditionByOrder(conditionMap);

		// [分组查询],[偏移量查询]暂不实现支持查询

		// [执行数据库查询]
		List<StatisticsShippingDTO> statisticsShippingBOList = statisticsShippingManager.selectStatisticsShipping(statisticsShippingQuery, conditionMap);
		List<StatisticsShippingVO> statisticsShippingVOList = BeanMapper.mapList(statisticsShippingBOList, StatisticsShippingVO.class);
		
		// [limit 查询]
		if (SearchCondition.conditionBylimit(conditionMap)) {
			return statisticsShippingVOList;
		}

		// 返回分页
		if (null != page) {
			PageInfo<Page<Object>> pInfo = SearchCondition.returnPage(page);
			page.addAll(statisticsShippingVOList);
			return pInfo;
		}

		// 没符合条件查询
		return statisticsShippingVOList;
	}
	@Override
	public Integer insertStatisticsShipping(StatisticsShippingDO statisticsShippingDO) {
		DacHelper.setUser(LoginUser.create().userId(loginInfoUtil.getLoginBaseInfo().getLoginName()).userType(loginInfoUtil.getLoginBaseInfo().getUserType()));
		return statisticsShippingManager.insertStatisticsShipping(statisticsShippingDO);
	}

	@Override
	public Integer deleteStatisticsShippingById(Integer id) {
		return statisticsShippingManager.deleteStatisticsShippingById(id);
	}

	@Override
	public Integer updateStatisticsShipping(StatisticsShippingDO statisticsShippingDO) {
		return statisticsShippingManager.updateStatisticsShipping(statisticsShippingDO);
	}

	@Override
	public StatisticsShippingDTO statisticsShipping(StatisticsShippingQuery statisticsShippingQuery) {
		return statisticsShippingManager.statisticsShipping(statisticsShippingQuery);
	}
}
