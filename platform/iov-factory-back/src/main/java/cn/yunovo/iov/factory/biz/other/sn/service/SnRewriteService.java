package cn.yunovo.iov.factory.biz.other.sn.service;

import java.util.Map;

import cn.yunovo.iov.boot.autoconfigure.request.select.Condition;
import cn.yunovo.iov.factory.biz.other.sn.model.SnRewriteDO;
import cn.yunovo.iov.factory.biz.other.sn.model.SnRewriteDTO;
import cn.yunovo.iov.factory.biz.other.sn.model.SnRewriteQuery;

/**
 * Service层：相对具体的业务逻辑服务层。对多个Manager的组合复用 。
 * 
 * @author huangzz
 *
 */
public interface SnRewriteService {

	SnRewriteDTO getSnRewriteById(Integer id);

	Object selectSnRewrite(SnRewriteQuery snRewriteQuery, Map<String, Condition> conditionMap);
	
	Integer insertSnRewrite(SnRewriteDO snRewriteDO);

	Integer deleteSnRewriteById(Integer id);

	Integer updateSnRewrite(SnRewriteDO snRewriteDO);

}
