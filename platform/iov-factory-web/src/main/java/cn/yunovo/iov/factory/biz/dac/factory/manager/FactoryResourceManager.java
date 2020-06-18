package cn.yunovo.iov.factory.biz.dac.factory.manager;

import java.util.List;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.yunovo.iov.framework.commons.beanutils.bean.BeanMapper;
import cn.yunovo.iov.boot.autoconfigure.request.select.Condition;
import cn.yunovo.iov.factory.biz.dac.factory.mapper.FactoryResourceMapper;
import cn.yunovo.iov.factory.biz.dac.factory.model.FactoryResourceDO;
import cn.yunovo.iov.factory.biz.dac.factory.model.FactoryResourceDTO;
import cn.yunovo.iov.factory.biz.dac.factory.model.FactoryResourceQuery;

/**
 * Manager层：通用业务处理层
 * @author huangzz
 *
 */
@Component
public class FactoryResourceManager {

	@Autowired
	private FactoryResourceMapper factoryResourceMapper;

	public FactoryResourceDTO getFactoryResourceById(Integer id) {
		FactoryResourceDO factoryResourceDO = factoryResourceMapper.getFactoryResourceById(id);
		FactoryResourceDTO factoryResourceDTO = BeanMapper.map(factoryResourceDO, FactoryResourceDTO.class);
		return factoryResourceDTO;
	}
	
	public FactoryResourceDTO queryFactoryResource(FactoryResourceQuery factoryResourceQuery) {
		FactoryResourceDO factoryResourceDO = factoryResourceMapper.queryFactoryResource(factoryResourceQuery);
		FactoryResourceDTO factoryResourceDTO = BeanMapper.map(factoryResourceDO, FactoryResourceDTO.class);
		return factoryResourceDTO;
	}

	public List<FactoryResourceDTO> selectFactoryResource(FactoryResourceQuery factoryResourceQuery, Map<String, Condition> conditionMap) {
		List<FactoryResourceDO> list = factoryResourceMapper.selectFactoryResource(factoryResourceQuery, conditionMap);
		return BeanMapper.mapList(list, FactoryResourceDTO.class);
	}
	public Integer insertFactoryResource(FactoryResourceDO factoryResourceDO) {
		factoryResourceDO.setCreateTime(new Date());
		factoryResourceDO.setUpdateTime(new Date());
		return factoryResourceMapper.insertFactoryResource(factoryResourceDO);
	}

	public Integer deleteFactoryResourceById(Integer id) {
		return factoryResourceMapper.deleteFactoryResourceById(id);
	}

	public Integer updateFactoryResource(FactoryResourceDO factoryResourceDO) {
		factoryResourceDO.setUpdateTime(new Date());
		return factoryResourceMapper.updateFactoryResource(factoryResourceDO);
	}
	
	public Integer deleteFactoryResource(FactoryResourceDO factoryResourceDO){
		return factoryResourceMapper.deleteFactoryResource(factoryResourceDO);
	}

	
}