package cn.yunovo.iov.device.zen.biz.other.data.manager;

import java.util.List;
import java.util.Map;

import cn.yunovo.iov.device.zen.biz.other.data.mapper.DictionaryMapper;
import cn.yunovo.iov.device.zen.biz.other.data.model.DictionaryDO;
import cn.yunovo.iov.device.zen.biz.other.data.model.DictionaryDTO;
import cn.yunovo.iov.device.zen.biz.other.data.model.DictionaryQuery;
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
public class DictionaryManager {

	@Autowired
	private DictionaryMapper dictionaryMapper;

	public DictionaryDTO getDictionaryById(Integer id) {
		DictionaryDO dictionaryDO = dictionaryMapper.getDictionaryById(id);
		DictionaryDTO dictionaryDTO = BeanMapper.map(dictionaryDO, DictionaryDTO.class);
		return dictionaryDTO;
	}
	
	public DictionaryDTO queryDictionary(DictionaryQuery dictionaryQuery) {
		DictionaryDO dictionaryDO = dictionaryMapper.queryDictionary(dictionaryQuery);
		DictionaryDTO dictionaryDTO = BeanMapper.map(dictionaryDO, DictionaryDTO.class);
		return dictionaryDTO;
	}

	public List<DictionaryDTO> selectDictionary(DictionaryQuery dictionaryQuery, Map<String, Condition> conditionMap) {
		List<DictionaryDO> list = dictionaryMapper.selectDictionary(dictionaryQuery, conditionMap);
		return BeanMapper.mapList(list, DictionaryDTO.class);
	}
	public Integer insertDictionary(DictionaryDO dictionaryDO) {
		return dictionaryMapper.insertDictionary(dictionaryDO);
	}

	public Integer deleteDictionaryById(Integer id) {
		return dictionaryMapper.deleteDictionaryById(id);
	}

	public Integer updateDictionary(DictionaryDO dictionaryDO) {
		return dictionaryMapper.updateDictionary(dictionaryDO);
	}
}