package cn.yunovo.iov.factory.biz.dac.resource.service.impl;

import java.util.List;
import java.util.Map;

import cn.yunovo.iov.boot.autoconfigure.auditlog.annotation.OpLog;
import cn.yunovo.iov.boot.autoconfigure.auditlog.enums.OpTypeEnum;
import cn.yunovo.iov.boot.autoconfigure.request.SearchCondition;
import cn.yunovo.iov.framework.commons.beanutils.bean.BeanMapper;
import cn.yunovo.iov.framework.web.PageInfo;
import cn.yunovo.iov.boot.autoconfigure.request.select.Condition;
import cn.yunovo.iov.factory.biz.dac.resource.manager.DataResourceManager;
import cn.yunovo.iov.factory.biz.dac.resource.model.DataResourceDO;
import cn.yunovo.iov.factory.biz.dac.resource.model.DataResourceDTO;
import cn.yunovo.iov.factory.biz.dac.resource.model.DataResourceQuery;
import cn.yunovo.iov.factory.biz.dac.resource.model.DataResourceVO;
import cn.yunovo.iov.factory.biz.dac.resource.service.DataResourceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;

@Service
public class DataResourceServiceImpl implements DataResourceService {

	@Autowired
	private DataResourceManager dataResourceManager;

	@Override
	public DataResourceDTO getDataResourceById(Integer id) {
		return dataResourceManager.getDataResourceById(id);
	}
	
	@Override
	public DataResourceDTO queryDataResource(DataResourceQuery dataResourceQuery) {
		return dataResourceManager.queryDataResource(dataResourceQuery);
	}
	
	@Override
	@OpLog( opType=OpTypeEnum.QUERY, opName = "根据条件查询[数据权限：资源，主体，规则关系表]信息")
	public Object selectDataResource(DataResourceQuery dataResourceQuery, Map<String, Condition> conditionMap) {
		Page<Object> page = null;

		// [分页查询]
		page = SearchCondition.conditionByPages(conditionMap);

		// [排序查询,不能使用OrderByHelper工具类,存在SQL中 limit 和 order by 顺序错误,Mybatis的分页拦截器和排序拦截器冲突]
		// SearchCondition.conditionByOrder(conditionMap);

		// [分组查询],[偏移量查询]暂不实现支持查询

		// [执行数据库查询]
		List<DataResourceDTO> dataResourceBOList = dataResourceManager.selectDataResource(dataResourceQuery, conditionMap);
		List<DataResourceVO> dataResourceVOList = BeanMapper.mapList(dataResourceBOList, DataResourceVO.class);

		// [limit 查询]
		if (SearchCondition.conditionBylimit(conditionMap)) {
			return dataResourceVOList;
		}

		// 返回分页
		if (null != page) {
			PageInfo<Page<Object>> pInfo = SearchCondition.returnPage(page);
			page.addAll(dataResourceVOList);
			return pInfo;
		}

		// 没符合条件查询
		return dataResourceVOList;
	}
	@Override
	public Integer insertDataResource(DataResourceDO dataResourceDO) {
		return dataResourceManager.insertDataResource(dataResourceDO);
	}

	@Override
	public Integer deleteDataResourceById(Integer id) {
		return dataResourceManager.deleteDataResourceById(id);
	}

	@Override
	public Integer updateDataResource(DataResourceDO dataResourceDO) {
		return dataResourceManager.updateDataResource(dataResourceDO);
	}
	
	@Override
	public Integer deleteDataResource(DataResourceDO dataResourceDO){
		return dataResourceManager.deleteDataResource(dataResourceDO);
	}
}
