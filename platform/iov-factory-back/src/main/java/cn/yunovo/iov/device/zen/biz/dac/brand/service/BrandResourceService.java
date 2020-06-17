package cn.yunovo.iov.device.zen.biz.dac.brand.service;

import java.util.Map;

import cn.yunovo.iov.boot.autoconfigure.request.select.Condition;
import cn.yunovo.iov.device.zen.biz.dac.brand.model.BrandResourceDO;
import cn.yunovo.iov.device.zen.biz.dac.brand.model.BrandResourceDTO;
import cn.yunovo.iov.device.zen.biz.dac.brand.model.BrandResourceQuery;

/**
 * Service层：相对具体的业务逻辑服务层。对多个Manager的组合复用 。
 * 
 * @author huangzz
 *
 */
public interface BrandResourceService {

	BrandResourceDTO getBrandResourceById(Integer id);
	
	BrandResourceDTO queryBrandResource(BrandResourceQuery brandResourceQuery);

	Object selectBrandResource(BrandResourceQuery brandResourceQuery, Map<String, Condition> conditionMap);
	
	Integer insertBrandResource(BrandResourceDO brandResourceDO);

	Integer deleteBrandResourceById(Integer id);

	Integer updateBrandResource(BrandResourceDO brandResourceDO);
	
    Integer deleteBrandResource(BrandResourceDO brandResourceDO);

}
