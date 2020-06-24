package cn.yunovo.iov.factory.biz.statistics.assemble.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;

import cn.yunovo.iov.boot.autoconfigure.dac.DacHelper;
import cn.yunovo.iov.boot.autoconfigure.request.SearchCondition;
import cn.yunovo.iov.boot.autoconfigure.request.select.Condition;
import cn.yunovo.iov.factory.biz.statistics.assemble.manager.StatisticsAssembleManager;
import cn.yunovo.iov.factory.biz.statistics.assemble.model.StatisticsAssembleDO;
import cn.yunovo.iov.factory.biz.statistics.assemble.model.StatisticsAssembleDTO;
import cn.yunovo.iov.factory.biz.statistics.assemble.model.StatisticsAssembleQuery;
import cn.yunovo.iov.factory.biz.statistics.assemble.model.StatisticsAssembleVO;
import cn.yunovo.iov.factory.biz.statistics.assemble.service.StatisticsAssembleService;
import cn.yunovo.iov.framework.commons.beanutils.bean.BeanMapper;
import cn.yunovo.iov.framework.web.PageInfo;

@Service
public class StatisticsAssembleServiceImpl implements StatisticsAssembleService {

	@Autowired
	private StatisticsAssembleManager statisticsAssembleManager;
	
	
	@Override
	public StatisticsAssembleDTO getStatisticsAssembleById(Integer id) {
		return statisticsAssembleManager.getStatisticsAssembleById(id);
	}
	
	@Override
	public Object selectStatisticsAssemble(StatisticsAssembleQuery statisticsAssembleQuery, Map<String, Condition> conditionMap) {
		return selectStatisticsAssemble(statisticsAssembleQuery, conditionMap,true);
	}
	
	@Override
	public Object selectStatisticsAssemble(StatisticsAssembleQuery statisticsAssembleQuery, Map<String, Condition> conditionMap, Boolean isDac) {
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
		List<StatisticsAssembleDTO> statisticsAssembleBOList = statisticsAssembleManager.selectStatisticsAssemble(statisticsAssembleQuery, conditionMap);
		List<StatisticsAssembleVO> statisticsAssembleVOList = BeanMapper.mapList(statisticsAssembleBOList, StatisticsAssembleVO.class);
		
		// [limit 查询]
		if (SearchCondition.conditionBylimit(conditionMap)) {
			return statisticsAssembleVOList;
		}

		// 返回分页
		if (null != page) {
			PageInfo<Page<Object>> pInfo = SearchCondition.returnPage(page);
			page.addAll(statisticsAssembleVOList);
			return pInfo;
		}

		// 没符合条件查询
		return statisticsAssembleVOList;
	}
	@Override
	public Integer insertStatisticsAssemble(StatisticsAssembleDO statisticsAssembleDO) {
		return statisticsAssembleManager.insertStatisticsAssemble(statisticsAssembleDO);
	}

	@Override
	public Integer deleteStatisticsAssembleById(Integer id) {
		return statisticsAssembleManager.deleteStatisticsAssembleById(id);
	}

	@Override
	public Integer updateStatisticsAssemble(StatisticsAssembleDO statisticsAssembleDO) {
		return statisticsAssembleManager.updateStatisticsAssemble(statisticsAssembleDO);
	}

	@Override
	public Integer statisticsCurrentDay(StatisticsAssembleQuery statisticsAssembleQuery) {
		return statisticsAssembleManager.statisticsCurrentDay(statisticsAssembleQuery);
	}
	
}
