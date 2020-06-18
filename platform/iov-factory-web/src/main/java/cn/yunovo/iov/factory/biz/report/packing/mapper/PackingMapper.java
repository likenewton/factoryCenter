package cn.yunovo.iov.factory.biz.report.packing.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import cn.yunovo.iov.boot.autoconfigure.mybatis.GeneralMapper;
import cn.yunovo.iov.boot.autoconfigure.request.select.Condition;
import cn.yunovo.iov.factory.biz.report.packing.model.PackingDO;
import cn.yunovo.iov.factory.biz.report.packing.model.PackingQuery;


/**
 * mapper 包为dao包，
 * 注意：方法入参规则
 * 
 * @author huangzz
 *
 */
@Mapper
public interface PackingMapper extends GeneralMapper<PackingDO> {

	PackingDO getPackingById(Integer id);
	
	List<PackingDO> selectPacking(@Param("query")PackingQuery packingQuery, @Param("condition")Map<String, Condition> conditionMap);
	Integer insertPacking(PackingDO packingDO);

	Integer updatePacking(PackingDO packingDO);

	Integer deletePackingById(Integer id);
}
