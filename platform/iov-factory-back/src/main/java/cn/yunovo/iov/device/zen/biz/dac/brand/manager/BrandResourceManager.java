package cn.yunovo.iov.device.zen.biz.dac.brand.manager;

import java.util.List;
import java.util.Date;
import java.util.Map;

import cn.yunovo.iov.device.zen.biz.dac.brand.mapper.BrandResourceMapper;
import cn.yunovo.iov.device.zen.biz.dac.brand.model.BrandResourceDO;
import cn.yunovo.iov.device.zen.biz.dac.brand.model.BrandResourceDTO;
import cn.yunovo.iov.device.zen.biz.dac.brand.model.BrandResourceQuery;
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
public class BrandResourceManager {

	@Autowired
	private BrandResourceMapper brandResourceMapper;

	public BrandResourceDTO getBrandResourceById(Integer id) {
		BrandResourceDO brandResourceDO = brandResourceMapper.getBrandResourceById(id);
		BrandResourceDTO brandResourceDTO = BeanMapper.map(brandResourceDO, BrandResourceDTO.class);
		return brandResourceDTO;
	}
	
	public BrandResourceDTO queryBrandResource(BrandResourceQuery brandResourceQuery) {
		BrandResourceDO brandResourceDO = brandResourceMapper.queryBrandResource(brandResourceQuery);
		BrandResourceDTO brandResourceDTO = BeanMapper.map(brandResourceDO, BrandResourceDTO.class);
		return brandResourceDTO;
	}

	public List<BrandResourceDTO> selectBrandResource(BrandResourceQuery brandResourceQuery, Map<String, Condition> conditionMap) {
		List<BrandResourceDO> list = brandResourceMapper.selectBrandResource(brandResourceQuery, conditionMap);
		return BeanMapper.mapList(list, BrandResourceDTO.class);
	}
	public Integer insertBrandResource(BrandResourceDO brandResourceDO) {
		brandResourceDO.setCreateTime(new Date());
		brandResourceDO.setUpdateTime(new Date());
		return brandResourceMapper.insertBrandResource(brandResourceDO);
	}

	public Integer deleteBrandResourceById(Integer id) {
		return brandResourceMapper.deleteBrandResourceById(id);
	}

	public Integer updateBrandResource(BrandResourceDO brandResourceDO) {
		brandResourceDO.setUpdateTime(new Date());
		return brandResourceMapper.updateBrandResource(brandResourceDO);
	}
	
	public Integer deleteBrandResource(BrandResourceDO brandResourceDO){
		return brandResourceMapper.deleteBrandResource(brandResourceDO);
	}

	
}