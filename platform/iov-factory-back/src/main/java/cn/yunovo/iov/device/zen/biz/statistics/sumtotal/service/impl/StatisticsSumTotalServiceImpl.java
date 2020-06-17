package cn.yunovo.iov.device.zen.biz.statistics.sumtotal.service.impl;

import java.util.List;
import java.util.Map;

import cn.yunovo.iov.boot.autoconfigure.auditlog.annotation.OpLog;
import cn.yunovo.iov.boot.autoconfigure.auditlog.enums.OpTypeEnum;
import cn.yunovo.iov.boot.autoconfigure.request.SearchCondition;
import cn.yunovo.iov.framework.commons.beanutils.bean.BeanMapper;
import cn.yunovo.iov.framework.web.PageInfo;
import cn.yunovo.iov.boot.autoconfigure.request.select.Condition;
import cn.yunovo.iov.device.zen.biz.statistics.sumtotal.manager.StatisticsSumTotalManager;
import cn.yunovo.iov.device.zen.biz.statistics.sumtotal.model.StatisticsSumTotalVO;
import cn.yunovo.iov.device.zen.biz.statistics.sumtotal.model.StatisticsSumTotalDO;
import cn.yunovo.iov.device.zen.biz.statistics.sumtotal.model.StatisticsSumTotalDTO;
import cn.yunovo.iov.device.zen.biz.statistics.sumtotal.model.StatisticsSumTotalQuery;
import cn.yunovo.iov.device.zen.biz.statistics.sumtotal.service.StatisticsSumTotalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;

@Service
public class StatisticsSumTotalServiceImpl implements StatisticsSumTotalService {

	@Autowired
	private StatisticsSumTotalManager statisticsSumTotalManager;

	@Override
	public StatisticsSumTotalDTO getStatisticsSumTotalById(Integer id) {
		return statisticsSumTotalManager.getStatisticsSumTotalById(id);
	}
	
	@Override
	public StatisticsSumTotalDTO queryStatisticsSumTotal(StatisticsSumTotalQuery statisticsSumTotalQuery) {
		return statisticsSumTotalManager.queryStatisticsSumTotal(statisticsSumTotalQuery);
	}
	
	@Override
	@OpLog( opType=OpTypeEnum.QUERY, opName = "根据条件查询[统计组织上报]信息")
	public Object selectStatisticsSumTotal(StatisticsSumTotalQuery statisticsSumTotalQuery, Map<String, Condition> conditionMap) {
		Page<Object> page = null;

		// [分页查询]
		page = SearchCondition.conditionByPages(conditionMap);

		// [排序查询,不能使用OrderByHelper工具类,存在SQL中 limit 和 order by 顺序错误]
		// SearchCondition.conditionByOrder(conditionMap);

		// [分组查询],[偏移量查询]暂不实现支持查询

		// [执行数据库查询]
		List<StatisticsSumTotalDTO> statisticsSumTotalBOList = statisticsSumTotalManager.selectStatisticsSumTotal(statisticsSumTotalQuery, conditionMap);
		List<StatisticsSumTotalVO> statisticsSumTotalVOList = BeanMapper.mapList(statisticsSumTotalBOList, StatisticsSumTotalVO.class);

		// [limit 查询]
		if (SearchCondition.conditionBylimit(conditionMap)) {
			return statisticsSumTotalVOList;
		}

		// 返回分页
		if (null != page) {
			PageInfo<Page<Object>> pInfo = SearchCondition.returnPage(page);
			page.addAll(statisticsSumTotalVOList);
			return pInfo;
		}

		// 没符合条件查询
		return statisticsSumTotalVOList;
	}
	@Override
	public Integer insertStatisticsSumTotal(StatisticsSumTotalDO statisticsSumTotalDO) {
		return statisticsSumTotalManager.insertStatisticsSumTotal(statisticsSumTotalDO);
	}

	@Override
	public Integer deleteStatisticsSumTotalById(Integer id) {
		return statisticsSumTotalManager.deleteStatisticsSumTotalById(id);
	}

	@Override
	public Integer updateStatisticsSumTotal(StatisticsSumTotalDO statisticsSumTotalDO) {
		return statisticsSumTotalManager.updateStatisticsSumTotal(statisticsSumTotalDO);
	}
}
