package cn.yunovo.iov.factory.biz.dac.brand.service.impl;

import java.util.List;
import java.util.Map;

import cn.yunovo.iov.boot.autoconfigure.auditlog.annotation.OpLog;
import cn.yunovo.iov.boot.autoconfigure.auditlog.enums.OpTypeEnum;
import cn.yunovo.iov.boot.autoconfigure.request.SearchCondition;
import cn.yunovo.iov.framework.commons.beanutils.bean.BeanMapper;
import cn.yunovo.iov.framework.web.PageInfo;
import cn.yunovo.iov.boot.autoconfigure.request.select.Condition;
import cn.yunovo.iov.factory.biz.dac.brand.manager.BrandResourceManager;
import cn.yunovo.iov.factory.biz.dac.brand.model.BrandResourceDO;
import cn.yunovo.iov.factory.biz.dac.brand.model.BrandResourceDTO;
import cn.yunovo.iov.factory.biz.dac.brand.model.BrandResourceQuery;
import cn.yunovo.iov.factory.biz.dac.brand.model.BrandResourceVO;
import cn.yunovo.iov.factory.biz.dac.brand.service.BrandResourceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;

@Service
public class BrandResourceServiceImpl implements BrandResourceService {

	@Autowired
	private BrandResourceManager brandResourceManager;

	@Override
	public BrandResourceDTO getBrandResourceById(Integer id) {
		return brandResourceManager.getBrandResourceById(id);
	}
	
	@Override
	public BrandResourceDTO queryBrandResource(BrandResourceQuery brandResourceQuery) {
		return brandResourceManager.queryBrandResource(brandResourceQuery);
	}
	
	@Override
	@OpLog( opType=OpTypeEnum.QUERY, opName = "根据条件查询[数据权限：资源，主体，规则关系表]信息")
	public Object selectBrandResource(BrandResourceQuery brandResourceQuery, Map<String, Condition> conditionMap) {
		Page<Object> page = null;

		// [分页查询]
		page = SearchCondition.conditionByPages(conditionMap);

		// [排序查询,不能使用OrderByHelper工具类,存在SQL中 limit 和 order by 顺序错误,Mybatis的分页拦截器和排序拦截器冲突]
		// SearchCondition.conditionByOrder(conditionMap);

		// [分组查询],[偏移量查询]暂不实现支持查询

		// [执行数据库查询]
		List<BrandResourceDTO> brandResourceBOList = brandResourceManager.selectBrandResource(brandResourceQuery, conditionMap);
		List<BrandResourceVO> brandResourceVOList = BeanMapper.mapList(brandResourceBOList, BrandResourceVO.class);

		// [limit 查询]
		if (SearchCondition.conditionBylimit(conditionMap)) {
			return brandResourceVOList;
		}

		// 返回分页
		if (null != page) {
			PageInfo<Page<Object>> pInfo = SearchCondition.returnPage(page);
			page.addAll(brandResourceVOList);
			return pInfo;
		}

		// 没符合条件查询
		return brandResourceVOList;
	}
	@Override
	public Integer insertBrandResource(BrandResourceDO brandResourceDO) {
		return brandResourceManager.insertBrandResource(brandResourceDO);
	}

	@Override
	public Integer deleteBrandResourceById(Integer id) {
		return brandResourceManager.deleteBrandResourceById(id);
	}

	@Override
	public Integer updateBrandResource(BrandResourceDO brandResourceDO) {
		return brandResourceManager.updateBrandResource(brandResourceDO);
	}
	
	@Override
	public Integer deleteBrandResource(BrandResourceDO brandResourceDO){
		return brandResourceManager.deleteBrandResource(brandResourceDO);
	}
}
