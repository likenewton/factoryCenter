package cn.yunovo.iov.device.zen.biz.dac.user.service;

import java.util.Map;

import cn.yunovo.iov.boot.autoconfigure.request.select.Condition;
import cn.yunovo.iov.device.zen.biz.dac.user.model.DacUserDO;
import cn.yunovo.iov.device.zen.biz.dac.user.model.DacUserDTO;
import cn.yunovo.iov.device.zen.biz.dac.user.model.DacUserQuery;

/**
 * Service层：相对具体的业务逻辑服务层。对多个Manager的组合复用 。
 * 
 * @author huangzz
 *
 */
public interface DacUserService {

	DacUserDTO getDacUserById(Integer id);
	
	DacUserDTO queryDacUser(DacUserQuery dacUserQuery);

	Object selectDacUser(DacUserQuery dacUserQuery, Map<String, Condition> conditionMap);
	
	Integer insertDacUser(DacUserDO dacUserDO);

	Integer deleteDacUserById(Integer id);

	Integer updateDacUser(DacUserDO dacUserDO);
	
    Integer deleteDacUser(DacUserDO dacUserDO);

}
