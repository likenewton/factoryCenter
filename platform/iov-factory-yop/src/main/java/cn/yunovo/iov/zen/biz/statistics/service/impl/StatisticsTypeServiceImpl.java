package cn.yunovo.iov.zen.biz.statistics.service.impl;

import java.util.List;
import java.util.Map;

import cn.yunovo.iov.boot.autoconfigure.auditlog.annotation.OpLog;
import cn.yunovo.iov.boot.autoconfigure.auditlog.enums.OpTypeEnum;
import cn.yunovo.iov.boot.autoconfigure.request.SearchCondition;
import cn.yunovo.iov.framework.commons.beanutils.bean.BeanMapper;
import cn.yunovo.iov.framework.web.PageInfo;
import cn.yunovo.iov.zen.biz.statistics.manager.StatisticsTypeManager;
import cn.yunovo.iov.zen.biz.statistics.model.StatisticsTypeDO;
import cn.yunovo.iov.zen.biz.statistics.model.StatisticsTypeDTO;
import cn.yunovo.iov.zen.biz.statistics.model.StatisticsTypeQuery;
import cn.yunovo.iov.zen.biz.statistics.model.StatisticsTypeVO;
import cn.yunovo.iov.zen.biz.statistics.service.StatisticsTypeService;
import cn.yunovo.iov.boot.autoconfigure.request.select.Condition;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;

@Service
public class StatisticsTypeServiceImpl implements StatisticsTypeService {

	@Autowired
	private StatisticsTypeManager statisticsTypeManager;

	@Override
	public StatisticsTypeDTO getStatisticsTypeById(Integer id) {
		return statisticsTypeManager.getStatisticsTypeById(id);
	}
	
	@Override
	@OpLog( opType=OpTypeEnum.QUERY, opName = "根据条件查询[]信息")
	public Object selectStatisticsType(StatisticsTypeQuery statisticsTypeQuery, Map<String, Condition> conditionMap) {
		Page<Object> page = null;

		// [分页查询]
		page = SearchCondition.conditionByPages(conditionMap);

		// [排序查询,不能使用OrderByHelper工具类,存在SQL中 limit 和 order by 顺序错误]
		// SearchCondition.conditionByOrder(conditionMap);

		// [分组查询],[偏移量查询]暂不实现支持查询

		// [执行数据库查询]
		List<StatisticsTypeDTO> statisticsTypeBOList = statisticsTypeManager.selectStatisticsType(statisticsTypeQuery, conditionMap);
		List<StatisticsTypeVO> statisticsTypeVOList = BeanMapper.mapList(statisticsTypeBOList, StatisticsTypeVO.class);

		// [limit 查询]
		if (SearchCondition.conditionBylimit(conditionMap)) {
			return statisticsTypeVOList;
		}

		// 返回分页
		if (null != page) {
			PageInfo<Page<Object>> pInfo = SearchCondition.returnPage(page);
			page.addAll(statisticsTypeVOList);
			return pInfo;
		}

		// 没符合条件查询
		return statisticsTypeVOList;
	}
	@Override
	public Integer insertStatisticsType(StatisticsTypeDO statisticsTypeDO) {
		return statisticsTypeManager.insertStatisticsType(statisticsTypeDO);
	}

	@Override
	public Integer deleteStatisticsTypeById(Integer id) {
		return statisticsTypeManager.deleteStatisticsTypeById(id);
	}

	@Override
	public Integer updateStatisticsType(StatisticsTypeDO statisticsTypeDO) {
		return statisticsTypeManager.updateStatisticsType(statisticsTypeDO);
	}
}
