package cn.yunovo.iov.factory.biz.other.data.service.impl;

import java.util.List;
import java.util.Map;

import cn.yunovo.iov.boot.autoconfigure.auditlog.annotation.OpLog;
import cn.yunovo.iov.boot.autoconfigure.auditlog.enums.OpTypeEnum;
import cn.yunovo.iov.boot.autoconfigure.request.SearchCondition;
import cn.yunovo.iov.framework.commons.beanutils.bean.BeanMapper;
import cn.yunovo.iov.framework.web.PageInfo;
import cn.yunovo.iov.boot.autoconfigure.request.select.Condition;
import cn.yunovo.iov.factory.biz.other.data.manager.DictionaryManager;
import cn.yunovo.iov.factory.biz.other.data.model.DictionaryDO;
import cn.yunovo.iov.factory.biz.other.data.model.DictionaryDTO;
import cn.yunovo.iov.factory.biz.other.data.model.DictionaryQuery;
import cn.yunovo.iov.factory.biz.other.data.model.DictionaryVO;
import cn.yunovo.iov.factory.biz.other.data.service.DictionaryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;

@Service
public class DictionaryServiceImpl implements DictionaryService {

	@Autowired
	private DictionaryManager dictionaryManager;

	@Override
	public DictionaryDTO getDictionaryById(Integer id) {
		return dictionaryManager.getDictionaryById(id);
	}
	
	@Override
	public DictionaryDTO queryDictionary(DictionaryQuery dictionaryQuery) {
		return dictionaryManager.queryDictionary(dictionaryQuery);
	}
	
	@Override
	@OpLog( opType=OpTypeEnum.QUERY, opName = "根据条件查询[]信息")
	public Object selectDictionary(DictionaryQuery dictionaryQuery, Map<String, Condition> conditionMap) {
		Page<Object> page = null;

		// [分页查询]
		page = SearchCondition.conditionByPages(conditionMap);

		// [排序查询,不能使用OrderByHelper工具类,存在SQL中 limit 和 order by 顺序错误]
		// SearchCondition.conditionByOrder(conditionMap);

		// [分组查询],[偏移量查询]暂不实现支持查询

		// [执行数据库查询]
		List<DictionaryDTO> dictionaryBOList = dictionaryManager.selectDictionary(dictionaryQuery, conditionMap);
		List<DictionaryVO> dictionaryVOList = BeanMapper.mapList(dictionaryBOList, DictionaryVO.class);

		// [limit 查询]
		if (SearchCondition.conditionBylimit(conditionMap)) {
			return dictionaryVOList;
		}

		// 返回分页
		if (null != page) {
			PageInfo<Page<Object>> pInfo = SearchCondition.returnPage(page);
			page.addAll(dictionaryVOList);
			return pInfo;
		}

		// 没符合条件查询
		return dictionaryVOList;
	}
	@Override
	public Integer insertDictionary(DictionaryDO dictionaryDO) {
		return dictionaryManager.insertDictionary(dictionaryDO);
	}

	@Override
	public Integer deleteDictionaryById(Integer id) {
		return dictionaryManager.deleteDictionaryById(id);
	}

	@Override
	public Integer updateDictionary(DictionaryDO dictionaryDO) {
		return dictionaryManager.updateDictionary(dictionaryDO);
	}
}
