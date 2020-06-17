package cn.yunovo.iov.factory.biz.report.software.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import cn.yunovo.iov.boot.autoconfigure.mybatis.GeneralMapper;
import cn.yunovo.iov.boot.autoconfigure.request.select.Condition;
import cn.yunovo.iov.factory.biz.report.software.model.SoftwareDO;
import cn.yunovo.iov.factory.biz.report.software.model.SoftwareQuery;


/**
 * mapper 包为dao包，
 * 注意：方法入参规则
 * 
 * @author huangzz
 *
 */
@Mapper
public interface SoftwareMapper extends GeneralMapper<SoftwareDO> {

	SoftwareDO getSoftwareById(Integer id);
	
	List<SoftwareDO> selectSoftware(@Param("query")SoftwareQuery softwareQuery, @Param("condition")Map<String, Condition> conditionMap);
	Integer insertSoftware(SoftwareDO softwareDO);

	Integer updateSoftware(SoftwareDO softwareDO);

	Integer deleteSoftwareById(Integer id);
}
