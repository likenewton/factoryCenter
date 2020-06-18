package cn.yunovo.iov.factory.biz.dac.resource.manager;

import java.util.List;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.yunovo.iov.framework.commons.beanutils.bean.BeanMapper;
import cn.yunovo.iov.boot.autoconfigure.request.select.Condition;
import cn.yunovo.iov.factory.biz.dac.resource.mapper.DataResourceMapper;
import cn.yunovo.iov.factory.biz.dac.resource.model.DataResourceDO;
import cn.yunovo.iov.factory.biz.dac.resource.model.DataResourceDTO;
import cn.yunovo.iov.factory.biz.dac.resource.model.DataResourceQuery;

/**
 * Manager层：通用业务处理层
 * @author huangzz
 *
 */
@Component
public class DataResourceManager {

	@Autowired
	private DataResourceMapper dataResourceMapper;

	public DataResourceDTO getDataResourceById(Integer id) {
		DataResourceDO dataResourceDO = dataResourceMapper.getDataResourceById(id);
		DataResourceDTO dataResourceDTO = BeanMapper.map(dataResourceDO, DataResourceDTO.class);
		return dataResourceDTO;
	}
	
	public DataResourceDTO queryDataResource(DataResourceQuery dataResourceQuery) {
		DataResourceDO dataResourceDO = dataResourceMapper.queryDataResource(dataResourceQuery);
		DataResourceDTO dataResourceDTO = BeanMapper.map(dataResourceDO, DataResourceDTO.class);
		return dataResourceDTO;
	}

	public List<DataResourceDTO> selectDataResource(DataResourceQuery dataResourceQuery, Map<String, Condition> conditionMap) {
		List<DataResourceDO> list = dataResourceMapper.selectDataResource(dataResourceQuery, conditionMap);
		return BeanMapper.mapList(list, DataResourceDTO.class);
	}
	public Integer insertDataResource(DataResourceDO dataResourceDO) {
		dataResourceDO.setCreateTime(new Date());
		dataResourceDO.setUpdateTime(new Date());
		return dataResourceMapper.insertDataResource(dataResourceDO);
	}

	public Integer deleteDataResourceById(Integer id) {
		return dataResourceMapper.deleteDataResourceById(id);
	}

	public Integer updateDataResource(DataResourceDO dataResourceDO) {
		dataResourceDO.setUpdateTime(new Date());
		return dataResourceMapper.updateDataResource(dataResourceDO);
	}
	
	public Integer deleteDataResource(DataResourceDO dataResourceDO){
		return dataResourceMapper.deleteDataResource(dataResourceDO);
	}

	
}