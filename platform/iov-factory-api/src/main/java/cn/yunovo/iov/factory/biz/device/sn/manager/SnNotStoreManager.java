package cn.yunovo.iov.factory.biz.device.sn.manager;

import java.util.List;
import java.util.Date;
import java.util.Map;

import cn.yunovo.iov.factory.biz.device.sn.mapper.SnNotStoreMapper;
import cn.yunovo.iov.factory.biz.device.sn.model.SnNotStoreDO;
import cn.yunovo.iov.factory.biz.device.sn.model.SnNotStoreDTO;
import cn.yunovo.iov.factory.biz.device.sn.model.SnNotStoreQuery;
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
public class SnNotStoreManager {

	@Autowired
	private SnNotStoreMapper snNotStoreMapper;

	public SnNotStoreDTO getSnNotStoreById(Integer id) {
		SnNotStoreDO snNotStoreDO = snNotStoreMapper.getSnNotStoreById(id);
		SnNotStoreDTO snNotStoreDTO = BeanMapper.map(snNotStoreDO, SnNotStoreDTO.class);
		return snNotStoreDTO;
	}
	
	public SnNotStoreDTO querySnNotStore(SnNotStoreQuery snNotStoreQuery) {
		SnNotStoreDO snNotStoreDO = snNotStoreMapper.querySnNotStore(snNotStoreQuery);
		SnNotStoreDTO snNotStoreDTO = BeanMapper.map(snNotStoreDO, SnNotStoreDTO.class);
		return snNotStoreDTO;
	}

	public List<SnNotStoreDTO> selectSnNotStore(SnNotStoreQuery snNotStoreQuery, Map<String, Condition> conditionMap) {
		List<SnNotStoreDO> list = snNotStoreMapper.selectSnNotStore(snNotStoreQuery, conditionMap);
		return BeanMapper.mapList(list, SnNotStoreDTO.class);
	}
	public Integer insertSnNotStore(SnNotStoreDO snNotStoreDO) {
		snNotStoreDO.setCreateTime(new Date());
		return snNotStoreMapper.insertSnNotStore(snNotStoreDO);
	}

	public Integer deleteSnNotStoreById(Integer id) {
		return snNotStoreMapper.deleteSnNotStoreById(id);
	}

	public Integer updateSnNotStore(SnNotStoreDO snNotStoreDO) {
		return snNotStoreMapper.updateSnNotStore(snNotStoreDO);
	}
	
	public Integer deleteSnNotStore(SnNotStoreDO snNotStoreDO){
		return snNotStoreMapper.deleteSnNotStore(snNotStoreDO);
	}

	
}