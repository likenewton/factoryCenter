package cn.yunovo.iov.device.zen.biz.other.sn.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import cn.yunovo.iov.device.zen.biz.other.sn.model.SnRewriteDO;
import cn.yunovo.iov.device.zen.biz.other.sn.model.SnRewriteQuery;
import cn.yunovo.iov.boot.autoconfigure.mybatis.GeneralMapper;
import cn.yunovo.iov.boot.autoconfigure.request.select.Condition;


/**
 * mapper 包为dao包，
 * 注意：方法入参规则
 * 
 * @author huangzz
 *
 */
@Mapper
public interface SnRewriteMapper extends GeneralMapper<SnRewriteDO> {

	SnRewriteDO getSnRewriteById(Integer id);
	
	List<SnRewriteDO> selectSnRewrite(@Param("query")SnRewriteQuery snRewriteQuery, @Param("condition")Map<String, Condition> conditionMap);
	Integer insertSnRewrite(SnRewriteDO snRewriteDO);

	Integer updateSnRewrite(SnRewriteDO snRewriteDO);

	Integer deleteSnRewriteById(Integer id);
}
