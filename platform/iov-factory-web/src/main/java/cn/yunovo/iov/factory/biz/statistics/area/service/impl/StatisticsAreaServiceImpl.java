package cn.yunovo.iov.factory.biz.statistics.area.service.impl;

import java.util.List;
import java.util.Map;

import cn.yunovo.iov.boot.autoconfigure.auditlog.annotation.OpLog;
import cn.yunovo.iov.boot.autoconfigure.auditlog.enums.OpTypeEnum;
import cn.yunovo.iov.boot.autoconfigure.request.SearchCondition;
import cn.yunovo.iov.framework.commons.beanutils.bean.BeanMapper;
import cn.yunovo.iov.framework.web.PageInfo;
import cn.yunovo.iov.boot.autoconfigure.request.select.Condition;
import cn.yunovo.iov.factory.biz.statistics.area.manager.StatisticsAreaManager;
import cn.yunovo.iov.factory.biz.statistics.area.model.StatisticsAreaDO;
import cn.yunovo.iov.factory.biz.statistics.area.model.StatisticsAreaDTO;
import cn.yunovo.iov.factory.biz.statistics.area.model.StatisticsAreaQuery;
import cn.yunovo.iov.factory.biz.statistics.area.model.StatisticsAreaVO;
import cn.yunovo.iov.factory.biz.statistics.area.service.StatisticsAreaService;
import cn.yunovo.iov.factory.framework.LoginInfoUtil;
import cn.yunovo.iov.factory.framework.dac.DacHelper;
import cn.yunovo.iov.factory.framework.dac.bean.LoginUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;

@Service
public class StatisticsAreaServiceImpl implements StatisticsAreaService {

	@Autowired
	private StatisticsAreaManager statisticsAreaManager;
	
	@Autowired
	private LoginInfoUtil loginInfoUtil;

	@Override
	public StatisticsAreaDTO getStatisticsAreaById(Integer id) {
		return statisticsAreaManager.getStatisticsAreaById(id);
	}
	
	@Override
	public StatisticsAreaDTO queryStatisticsArea(StatisticsAreaQuery statisticsAreaQuery) {
		return statisticsAreaManager.queryStatisticsArea(statisticsAreaQuery);
	}
	
	@Override
	@OpLog( opType=OpTypeEnum.QUERY, opName = "根据条件查询[统计发货管理]信息")
	public Object selectStatisticsArea(StatisticsAreaQuery statisticsAreaQuery, Map<String, Condition> conditionMap, Boolean isDac) {
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
		List<StatisticsAreaDTO> statisticsAreaBOList = statisticsAreaManager.selectStatisticsArea(statisticsAreaQuery, conditionMap);
		List<StatisticsAreaVO> statisticsAreaVOList = BeanMapper.mapList(statisticsAreaBOList, StatisticsAreaVO.class);

		// [limit 查询]
		if (SearchCondition.conditionBylimit(conditionMap)) {
			return statisticsAreaVOList;
		}

		// 返回分页
		if (null != page) {
			PageInfo<Page<Object>> pInfo = SearchCondition.returnPage(page);
			page.addAll(statisticsAreaVOList);
			return pInfo;
		}

		// 没符合条件查询
		return statisticsAreaVOList;
	}
	@Override
	public Integer insertStatisticsArea(StatisticsAreaDO statisticsAreaDO) {
		return statisticsAreaManager.insertStatisticsArea(statisticsAreaDO);
	}

	@Override
	public Integer deleteStatisticsAreaById(Integer id) {
		return statisticsAreaManager.deleteStatisticsAreaById(id);
	}

	@Override
	public Integer updateStatisticsArea(StatisticsAreaDO statisticsAreaDO) {
		return statisticsAreaManager.updateStatisticsArea(statisticsAreaDO);
	}

	@Override
	public Object selectStatisticsArea(StatisticsAreaQuery statisticsAreaQuery, Map<String, Condition> conditionMap) {
		return selectStatisticsArea(statisticsAreaQuery, conditionMap, true);
	}
}
