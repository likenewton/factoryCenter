package cn.yunovo.iov.factory.biz.other.data.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import cn.yunovo.iov.boot.autoconfigure.mybatis.GeneralMapper;
import cn.yunovo.iov.boot.autoconfigure.request.select.Condition;
import cn.yunovo.iov.factory.biz.other.data.model.DictionaryDO;
import cn.yunovo.iov.factory.biz.other.data.model.DictionaryQuery;


/**
 * mapper 包为dao包，
 * 注意：方法入参规则
 * 
 * @author huangzz
 *
 */
@Mapper
public interface DictionaryMapper extends GeneralMapper<DictionaryDO> {

	DictionaryDO getDictionaryById(Integer id);
	
	List<DictionaryDO> selectDictionary(@Param("query")DictionaryQuery dictionaryQuery, @Param("condition")Map<String, Condition> conditionMap);
	
	DictionaryDO queryDictionary(@Param("query")DictionaryQuery dictionaryQuery);
	
	Integer insertDictionary(DictionaryDO dictionaryDO);

	Integer updateDictionary(DictionaryDO dictionaryDO);

	Integer deleteDictionaryById(Integer id);
}
