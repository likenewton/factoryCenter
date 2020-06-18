package cn.yunovo.iov.factory.biz.dac.factory.service.impl;

import java.util.List;
import java.util.Map;

import cn.yunovo.iov.boot.autoconfigure.auditlog.annotation.OpLog;
import cn.yunovo.iov.boot.autoconfigure.auditlog.enums.OpTypeEnum;
import cn.yunovo.iov.boot.autoconfigure.request.SearchCondition;
import cn.yunovo.iov.framework.commons.beanutils.bean.BeanMapper;
import cn.yunovo.iov.framework.web.PageInfo;
import cn.yunovo.iov.boot.autoconfigure.request.select.Condition;
import cn.yunovo.iov.factory.biz.dac.factory.manager.FactoryResourceManager;
import cn.yunovo.iov.factory.biz.dac.factory.model.FactoryResourceDO;
import cn.yunovo.iov.factory.biz.dac.factory.model.FactoryResourceDTO;
import cn.yunovo.iov.factory.biz.dac.factory.model.FactoryResourceQuery;
import cn.yunovo.iov.factory.biz.dac.factory.model.FactoryResourceVO;
import cn.yunovo.iov.factory.biz.dac.factory.service.FactoryResourceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;

@Service
public class FactoryResourceServiceImpl implements FactoryResourceService {

	@Autowired
	private FactoryResourceManager factoryResourceManager;

	@Override
	public FactoryResourceDTO getFactoryResourceById(Integer id) {
		return factoryResourceManager.getFactoryResourceById(id);
	}
	
	@Override
	public FactoryResourceDTO queryFactoryResource(FactoryResourceQuery factoryResourceQuery) {
		return factoryResourceManager.queryFactoryResource(factoryResourceQuery);
	}
	
	@Override
	@OpLog( opType=OpTypeEnum.QUERY, opName = "根据条件查询[数据权限：资源，主体，规则关系表]信息")
	public Object selectFactoryResource(FactoryResourceQuery factoryResourceQuery, Map<String, Condition> conditionMap) {
		Page<Object> page = null;

		// [分页查询]
		page = SearchCondition.conditionByPages(conditionMap);

		// [排序查询,不能使用OrderByHelper工具类,存在SQL中 limit 和 order by 顺序错误,Mybatis的分页拦截器和排序拦截器冲突]
		// SearchCondition.conditionByOrder(conditionMap);

		// [分组查询],[偏移量查询]暂不实现支持查询

		// [执行数据库查询]
		List<FactoryResourceDTO> factoryResourceBOList = factoryResourceManager.selectFactoryResource(factoryResourceQuery, conditionMap);
		List<FactoryResourceVO> factoryResourceVOList = BeanMapper.mapList(factoryResourceBOList, FactoryResourceVO.class);

		// [limit 查询]
		if (SearchCondition.conditionBylimit(conditionMap)) {
			return factoryResourceVOList;
		}

		// 返回分页
		if (null != page) {
			PageInfo<Page<Object>> pInfo = SearchCondition.returnPage(page);
			page.addAll(factoryResourceVOList);
			return pInfo;
		}

		// 没符合条件查询
		return factoryResourceVOList;
	}
	@Override
	public Integer insertFactoryResource(FactoryResourceDO factoryResourceDO) {
		return factoryResourceManager.insertFactoryResource(factoryResourceDO);
	}

	@Override
	public Integer deleteFactoryResourceById(Integer id) {
		return factoryResourceManager.deleteFactoryResourceById(id);
	}

	@Override
	public Integer updateFactoryResource(FactoryResourceDO factoryResourceDO) {
		return factoryResourceManager.updateFactoryResource(factoryResourceDO);
	}
	
	@Override
	public Integer deleteFactoryResource(FactoryResourceDO factoryResourceDO){
		return factoryResourceManager.deleteFactoryResource(factoryResourceDO);
	}
}
