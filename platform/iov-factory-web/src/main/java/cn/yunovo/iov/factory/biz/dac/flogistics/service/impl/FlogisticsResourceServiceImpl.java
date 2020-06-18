package cn.yunovo.iov.factory.biz.dac.flogistics.service.impl;

import java.util.List;
import java.util.Map;

import cn.yunovo.iov.boot.autoconfigure.auditlog.annotation.OpLog;
import cn.yunovo.iov.boot.autoconfigure.auditlog.enums.OpTypeEnum;
import cn.yunovo.iov.boot.autoconfigure.request.SearchCondition;
import cn.yunovo.iov.framework.commons.beanutils.bean.BeanMapper;
import cn.yunovo.iov.framework.web.PageInfo;
import cn.yunovo.iov.boot.autoconfigure.request.select.Condition;
import cn.yunovo.iov.factory.biz.dac.flogistics.manager.FlogisticsResourceManager;
import cn.yunovo.iov.factory.biz.dac.flogistics.model.FlogisticsResourceVO;
import cn.yunovo.iov.factory.biz.dac.flogistics.model.FlogisticsResourceDO;
import cn.yunovo.iov.factory.biz.dac.flogistics.model.FlogisticsResourceDTO;
import cn.yunovo.iov.factory.biz.dac.flogistics.model.FlogisticsResourceQuery;
import cn.yunovo.iov.factory.biz.dac.flogistics.service.FlogisticsResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;

@Service
public class FlogisticsResourceServiceImpl implements FlogisticsResourceService {

	@Autowired
	private FlogisticsResourceManager flogisticsResourceManager;

	@Override
	public FlogisticsResourceDTO getFlogisticsResourceById(Integer id) {
		return flogisticsResourceManager.getFlogisticsResourceById(id);
	}
	
	@Override
	public FlogisticsResourceDTO queryFlogisticsResource(FlogisticsResourceQuery flogisticsResourceQuery) {
		return flogisticsResourceManager.queryFlogisticsResource(flogisticsResourceQuery);
	}
	
	@Override
	@OpLog( opType=OpTypeEnum.QUERY, opName = "根据条件查询[物流用户数据权限：资源，主体，规则关系表]信息")
	public Object selectFlogisticsResource(FlogisticsResourceQuery flogisticsResourceQuery, Map<String, Condition> conditionMap) {
		Page<Object> page = null;

		// [分页查询]
		page = SearchCondition.conditionByPages(conditionMap);

		// [排序查询,不能使用OrderByHelper工具类,存在SQL中 limit 和 order by 顺序错误,Mybatis的分页拦截器和排序拦截器冲突]
		// SearchCondition.conditionByOrder(conditionMap);

		// [分组查询],[偏移量查询]暂不实现支持查询

		// [执行数据库查询]
		List<FlogisticsResourceDTO> flogisticsResourceBOList = flogisticsResourceManager.selectFlogisticsResource(flogisticsResourceQuery, conditionMap);
		List<FlogisticsResourceVO> flogisticsResourceVOList = BeanMapper.mapList(flogisticsResourceBOList, FlogisticsResourceVO.class);

		// [limit 查询]
		if (SearchCondition.conditionBylimit(conditionMap)) {
			return flogisticsResourceVOList;
		}

		// 返回分页
		if (null != page) {
			PageInfo<Page<Object>> pInfo = SearchCondition.returnPage(page);
			page.addAll(flogisticsResourceVOList);
			return pInfo;
		}

		// 没符合条件查询
		return flogisticsResourceVOList;
	}
	@Override
	public Integer insertFlogisticsResource(FlogisticsResourceDO flogisticsResourceDO) {
		return flogisticsResourceManager.insertFlogisticsResource(flogisticsResourceDO);
	}

	@Override
	public Integer deleteFlogisticsResourceById(Integer id) {
		return flogisticsResourceManager.deleteFlogisticsResourceById(id);
	}

	@Override
	public Integer updateFlogisticsResource(FlogisticsResourceDO flogisticsResourceDO) {
		return flogisticsResourceManager.updateFlogisticsResource(flogisticsResourceDO);
	}
	
	@Override
	public Integer deleteFlogisticsResource(FlogisticsResourceDO flogisticsResourceDO){
		return flogisticsResourceManager.deleteFlogisticsResource(flogisticsResourceDO);
	}
}
