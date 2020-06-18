package cn.yunovo.iov.factory.biz.statistics.paster.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;

import cn.yunovo.iov.boot.autoconfigure.request.SearchCondition;
import cn.yunovo.iov.boot.autoconfigure.request.select.Condition;
import cn.yunovo.iov.factory.biz.statistics.paster.manager.StatisticsPasterManager;
import cn.yunovo.iov.factory.biz.statistics.paster.model.StatisticsPasterDO;
import cn.yunovo.iov.factory.biz.statistics.paster.model.StatisticsPasterDTO;
import cn.yunovo.iov.factory.biz.statistics.paster.model.StatisticsPasterQuery;
import cn.yunovo.iov.factory.biz.statistics.paster.model.StatisticsPasterVO;
import cn.yunovo.iov.factory.biz.statistics.paster.service.StatisticsPasterService;
import cn.yunovo.iov.factory.framework.dac.DacHelper;
import cn.yunovo.iov.framework.commons.beanutils.bean.BeanMapper;
import cn.yunovo.iov.framework.web.PageInfo;

@Service
public class StatisticsPasterServiceImpl implements StatisticsPasterService {

	@Autowired
	private StatisticsPasterManager statisticsPasterManager;
	
	@Override
	public StatisticsPasterDTO getStatisticsPasterById(Integer id) {
		return statisticsPasterManager.getStatisticsPasterById(id);
	}
	
	@Override
	public Object selectStatisticsPaster(StatisticsPasterQuery statisticsPasterQuery, Map<String, Condition> conditionMap, Boolean isDac) {
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
		List<StatisticsPasterDTO> statisticsPasterBOList = statisticsPasterManager.selectStatisticsPaster(statisticsPasterQuery, conditionMap);
		List<StatisticsPasterVO> statisticsPasterVOList = BeanMapper.mapList(statisticsPasterBOList, StatisticsPasterVO.class);
		
		// [limit 查询]
		if (SearchCondition.conditionBylimit(conditionMap)) {
			return statisticsPasterVOList;
		}

		// 返回分页
		if (null != page) {
			PageInfo<Page<Object>> pInfo = SearchCondition.returnPage(page);
			page.addAll(statisticsPasterVOList);
			return pInfo;
		}

		// 没符合条件查询
		return statisticsPasterVOList;
	}
	@Override
	public Integer insertStatisticsPaster(StatisticsPasterDO statisticsPasterDO) {
		return statisticsPasterManager.insertStatisticsPaster(statisticsPasterDO);
	}

	@Override
	public Integer deleteStatisticsPasterById(Integer id) {
		return statisticsPasterManager.deleteStatisticsPasterById(id);
	}

	@Override
	public Integer updateStatisticsPaster(StatisticsPasterDO statisticsPasterDO) {
		return statisticsPasterManager.updateStatisticsPaster(statisticsPasterDO);
	}

	@Override
	public Integer statisticsCurrentDay(StatisticsPasterQuery statisticsPasterQuery) {
		return statisticsPasterManager.statisticsCurrentDay(statisticsPasterQuery);
	}

	@Override
	public Object selectStatisticsPaster(StatisticsPasterQuery statisticsPasterQuery, Map<String, Condition> conditionMap) {
		return selectStatisticsPaster(statisticsPasterQuery, conditionMap, true);
	}
}
