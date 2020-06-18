package cn.yunovo.iov.factory.biz.dac.user.manager;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.yunovo.iov.framework.commons.beanutils.bean.BeanMapper;
import cn.yunovo.iov.boot.autoconfigure.request.select.Condition;
import cn.yunovo.iov.factory.biz.dac.user.mapper.DacUserMapper;
import cn.yunovo.iov.factory.biz.dac.user.model.DacUserDO;
import cn.yunovo.iov.factory.biz.dac.user.model.DacUserDTO;
import cn.yunovo.iov.factory.biz.dac.user.model.DacUserQuery;

/**
 * Manager层：通用业务处理层
 * @author huangzz
 *
 */
@Component
public class DacUserManager {

	@Autowired
	private DacUserMapper dacUserMapper;

	public DacUserDTO getDacUserById(Integer id) {
		DacUserDO dacUserDO = dacUserMapper.getDacUserById(id);
		DacUserDTO dacUserDTO = BeanMapper.map(dacUserDO, DacUserDTO.class);
		return dacUserDTO;
	}
	
	public DacUserDTO queryDacUser(DacUserQuery dacUserQuery) {
		DacUserDO dacUserDO = dacUserMapper.queryDacUser(dacUserQuery);
		DacUserDTO dacUserDTO = BeanMapper.map(dacUserDO, DacUserDTO.class);
		return dacUserDTO;
	}

	public List<DacUserDTO> selectDacUser(DacUserQuery dacUserQuery, Map<String, Condition> conditionMap) {
		List<DacUserDO> list = dacUserMapper.selectDacUser(dacUserQuery, conditionMap);
		return BeanMapper.mapList(list, DacUserDTO.class);
	}
	public Integer insertDacUser(DacUserDO dacUserDO) {
		return dacUserMapper.insertDacUser(dacUserDO);
	}

	public Integer deleteDacUserById(Integer id) {
		return dacUserMapper.deleteDacUserById(id);
	}

	public Integer updateDacUser(DacUserDO dacUserDO) {
		return dacUserMapper.updateDacUser(dacUserDO);
	}
	
	public Integer deleteDacUser(DacUserDO dacUserDO){
		return dacUserMapper.deleteDacUser(dacUserDO);
	}

	
}