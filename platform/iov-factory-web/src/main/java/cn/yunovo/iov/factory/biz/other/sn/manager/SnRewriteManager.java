package cn.yunovo.iov.factory.biz.other.sn.manager;

import java.util.List;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.yunovo.iov.framework.commons.beanutils.bean.BeanMapper;
import cn.yunovo.iov.boot.autoconfigure.request.select.Condition;
import cn.yunovo.iov.factory.biz.other.sn.mapper.SnRewriteMapper;
import cn.yunovo.iov.factory.biz.other.sn.model.SnRewriteDO;
import cn.yunovo.iov.factory.biz.other.sn.model.SnRewriteDTO;
import cn.yunovo.iov.factory.biz.other.sn.model.SnRewriteQuery;

/**
 * Manager层：通用业务处理层
 * @author huangzz
 *
 */
@Component
public class SnRewriteManager {

	@Autowired
	private SnRewriteMapper snRewriteMapper;

	public SnRewriteDTO getSnRewriteById(Integer id) {
		SnRewriteDO snRewriteDO = snRewriteMapper.getSnRewriteById(id);
		SnRewriteDTO SnRewriteDTO = BeanMapper.map(snRewriteDO, SnRewriteDTO.class);
		return SnRewriteDTO;
	}

	public List<SnRewriteDTO> selectSnRewrite(SnRewriteQuery snRewriteQuery, Map<String, Condition> conditionMap) {
		List<SnRewriteDO> list = snRewriteMapper.selectSnRewrite(snRewriteQuery, conditionMap);
		return BeanMapper.mapList(list, SnRewriteDTO.class);
	}
	public Integer insertSnRewrite(SnRewriteDO snRewriteDO) {
		snRewriteDO.setCreateTime(new Date());
		return snRewriteMapper.insertSnRewrite(snRewriteDO);
	}

	public Integer deleteSnRewriteById(Integer id) {
		return snRewriteMapper.deleteSnRewriteById(id);
	}

	public Integer updateSnRewrite(SnRewriteDO snRewriteDO) {
		return snRewriteMapper.updateSnRewrite(snRewriteDO);
	}
}