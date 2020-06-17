package cn.yunovo.iov.factory.biz.other.data.service;

import java.util.Map;

import cn.yunovo.iov.boot.autoconfigure.request.select.Condition;
import cn.yunovo.iov.factory.biz.other.data.model.DictionaryDO;
import cn.yunovo.iov.factory.biz.other.data.model.DictionaryDTO;
import cn.yunovo.iov.factory.biz.other.data.model.DictionaryQuery;

/**
 * Service层：相对具体的业务逻辑服务层。对多个Manager的组合复用 。
 * 
 * @author huangzz
 *
 */
public interface DictionaryService {

	DictionaryDTO getDictionaryById(Integer id);
	
	DictionaryDTO queryDictionary(DictionaryQuery dictionaryQuery);

	Object selectDictionary(DictionaryQuery dictionaryQuery, Map<String, Condition> conditionMap);
	
	Integer insertDictionary(DictionaryDO dictionaryDO);

	Integer deleteDictionaryById(Integer id);

	Integer updateDictionary(DictionaryDO dictionaryDO);

}
