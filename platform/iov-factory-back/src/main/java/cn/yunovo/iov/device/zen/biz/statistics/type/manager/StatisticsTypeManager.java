package cn.yunovo.iov.device.zen.biz.statistics.type.manager;

import java.util.List;
import java.util.Date;
import java.util.Map;

import cn.yunovo.iov.device.zen.biz.statistics.type.mapper.StatisticsTypeMapper;
import cn.yunovo.iov.device.zen.biz.statistics.type.model.StatisticsTypeDO;
import cn.yunovo.iov.device.zen.biz.statistics.type.model.StatisticsTypeDTO;
import cn.yunovo.iov.device.zen.biz.statistics.type.model.StatisticsTypeQuery;
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
public class StatisticsTypeManager {

	@Autowired
	private StatisticsTypeMapper statisticsTypeMapper;

	public StatisticsTypeDTO getStatisticsTypeById(Integer id) {
		StatisticsTypeDO statisticsTypeDO = statisticsTypeMapper.getStatisticsTypeById(id);
		StatisticsTypeDTO statisticsTypeDTO = BeanMapper.map(statisticsTypeDO, StatisticsTypeDTO.class);
		return statisticsTypeDTO;
	}
	
	public StatisticsTypeDTO queryStatisticsType(StatisticsTypeQuery statisticsTypeQuery) {
		StatisticsTypeDO statisticsTypeDO = statisticsTypeMapper.queryStatisticsType(statisticsTypeQuery);
		StatisticsTypeDTO statisticsTypeDTO = BeanMapper.map(statisticsTypeDO, StatisticsTypeDTO.class);
		return statisticsTypeDTO;
	}

	public List<StatisticsTypeDTO> selectStatisticsType(StatisticsTypeQuery statisticsTypeQuery, Map<String, Condition> conditionMap) {
		List<StatisticsTypeDO> list = statisticsTypeMapper.selectStatisticsType(statisticsTypeQuery, conditionMap);
		return BeanMapper.mapList(list, StatisticsTypeDTO.class);
	}
	public Integer insertStatisticsType(StatisticsTypeDO statisticsTypeDO) {
		statisticsTypeDO.setCreateTime(new Date());
		return statisticsTypeMapper.insertStatisticsType(statisticsTypeDO);
	}

	public Integer deleteStatisticsTypeById(Integer id) {
		return statisticsTypeMapper.deleteStatisticsTypeById(id);
	}

	public Integer updateStatisticsType(StatisticsTypeDO statisticsTypeDO) {
		return statisticsTypeMapper.updateStatisticsType(statisticsTypeDO);
	}
	
	public Integer deleteStatisticsType(StatisticsTypeDO statisticsTypeDO){
		return statisticsTypeMapper.deleteStatisticsType(statisticsTypeDO);
	}

	
}