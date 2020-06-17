package cn.yunovo.iov.factory.biz.dac.user.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import cn.yunovo.iov.boot.autoconfigure.mybatis.GeneralMapper;
import cn.yunovo.iov.boot.autoconfigure.request.select.Condition;
import cn.yunovo.iov.factory.biz.dac.user.model.DacUserDO;
import cn.yunovo.iov.factory.biz.dac.user.model.DacUserQuery;


/**
 * mapper 包为dao包，
 * 注意：方法入参规则
 * 
 * @author huangzz
 *
 */
@Mapper
public interface DacUserMapper extends GeneralMapper<DacUserDO> {

	DacUserDO getDacUserById(Integer id);
	
	List<DacUserDO> selectDacUser(@Param("query")DacUserQuery dacUserQuery, @Param("condition")Map<String, Condition> conditionMap);
	
	DacUserDO queryDacUser(@Param("query")DacUserQuery dacUserQuery);
	
	Integer insertDacUser(DacUserDO dacUserDO);

	Integer updateDacUser(DacUserDO dacUserDO);

	Integer deleteDacUserById(Integer id);
	
	Integer deleteDacUser(@Param("item")DacUserDO dacUserDO);
}
