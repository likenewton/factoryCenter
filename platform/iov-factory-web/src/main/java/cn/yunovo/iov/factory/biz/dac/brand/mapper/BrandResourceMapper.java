package cn.yunovo.iov.factory.biz.dac.brand.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import cn.yunovo.iov.boot.autoconfigure.mybatis.GeneralMapper;
import cn.yunovo.iov.boot.autoconfigure.request.select.Condition;
import cn.yunovo.iov.factory.biz.dac.brand.model.BrandResourceDO;
import cn.yunovo.iov.factory.biz.dac.brand.model.BrandResourceQuery;


/**
 * mapper 包为dao包，
 * 注意：方法入参规则
 * 
 * @author huangzz
 *
 */
@Mapper
public interface BrandResourceMapper extends GeneralMapper<BrandResourceDO> {

	BrandResourceDO getBrandResourceById(Integer id);
	
	List<BrandResourceDO> selectBrandResource(@Param("query")BrandResourceQuery brandResourceQuery, @Param("condition")Map<String, Condition> conditionMap);
	
	BrandResourceDO queryBrandResource(@Param("query")BrandResourceQuery brandResourceQuery);
	
	Integer insertBrandResource(BrandResourceDO brandResourceDO);

	Integer updateBrandResource(BrandResourceDO brandResourceDO);

	Integer deleteBrandResourceById(Integer id);
	
	Integer deleteBrandResource(@Param("item")BrandResourceDO brandResourceDO);
}
