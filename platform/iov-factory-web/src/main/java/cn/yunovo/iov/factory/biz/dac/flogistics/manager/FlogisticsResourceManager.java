package cn.yunovo.iov.factory.biz.dac.flogistics.manager;

import java.util.List;
import java.util.Date;
import java.util.Map;

import cn.yunovo.iov.factory.biz.dac.flogistics.mapper.FlogisticsResourceMapper;
import cn.yunovo.iov.factory.biz.dac.flogistics.model.FlogisticsResourceDO;
import cn.yunovo.iov.factory.biz.dac.flogistics.model.FlogisticsResourceDTO;
import cn.yunovo.iov.factory.biz.dac.flogistics.model.FlogisticsResourceQuery;
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
public class FlogisticsResourceManager {

	@Autowired
	private FlogisticsResourceMapper flogisticsResourceMapper;

	public FlogisticsResourceDTO getFlogisticsResourceById(Integer id) {
		FlogisticsResourceDO flogisticsResourceDO = flogisticsResourceMapper.getFlogisticsResourceById(id);
		FlogisticsResourceDTO flogisticsResourceDTO = BeanMapper.map(flogisticsResourceDO, FlogisticsResourceDTO.class);
		return flogisticsResourceDTO;
	}
	
	public FlogisticsResourceDTO queryFlogisticsResource(FlogisticsResourceQuery flogisticsResourceQuery) {
		FlogisticsResourceDO flogisticsResourceDO = flogisticsResourceMapper.queryFlogisticsResource(flogisticsResourceQuery);
		FlogisticsResourceDTO flogisticsResourceDTO = BeanMapper.map(flogisticsResourceDO, FlogisticsResourceDTO.class);
		return flogisticsResourceDTO;
	}

	public List<FlogisticsResourceDTO> selectFlogisticsResource(FlogisticsResourceQuery flogisticsResourceQuery, Map<String, Condition> conditionMap) {
		List<FlogisticsResourceDO> list = flogisticsResourceMapper.selectFlogisticsResource(flogisticsResourceQuery, conditionMap);
		return BeanMapper.mapList(list, FlogisticsResourceDTO.class);
	}
	public Integer insertFlogisticsResource(FlogisticsResourceDO flogisticsResourceDO) {
		flogisticsResourceDO.setCreateTime(new Date());
		flogisticsResourceDO.setUpdateTime(new Date());
		return flogisticsResourceMapper.insertFlogisticsResource(flogisticsResourceDO);
	}

	public Integer deleteFlogisticsResourceById(Integer id) {
		return flogisticsResourceMapper.deleteFlogisticsResourceById(id);
	}

	public Integer updateFlogisticsResource(FlogisticsResourceDO flogisticsResourceDO) {
		flogisticsResourceDO.setUpdateTime(new Date());
		return flogisticsResourceMapper.updateFlogisticsResource(flogisticsResourceDO);
	}
	
	public Integer deleteFlogisticsResource(FlogisticsResourceDO flogisticsResourceDO){
		return flogisticsResourceMapper.deleteFlogisticsResource(flogisticsResourceDO);
	}

	
}