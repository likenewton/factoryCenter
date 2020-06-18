package cn.yunovo.iov.factory.biz.dac.channel.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import cn.yunovo.iov.boot.autoconfigure.mybatis.GeneralMapper;
import cn.yunovo.iov.boot.autoconfigure.request.select.Condition;
import cn.yunovo.iov.factory.biz.dac.channel.model.ChannelResourceDO;
import cn.yunovo.iov.factory.biz.dac.channel.model.ChannelResourceQuery;


/**
 * mapper 包为dao包，
 * 注意：方法入参规则
 * 
 * @author huangzz
 *
 */
@Mapper
public interface ChannelResourceMapper extends GeneralMapper<ChannelResourceDO> {

	ChannelResourceDO getChannelResourceById(Integer id);
	
	List<ChannelResourceDO> selectChannelResource(@Param("query")ChannelResourceQuery channelResourceQuery, @Param("condition")Map<String, Condition> conditionMap);
	
	ChannelResourceDO queryChannelResource(@Param("query")ChannelResourceQuery channelResourceQuery);
	
	Integer insertChannelResource(ChannelResourceDO channelResourceDO);

	Integer updateChannelResource(ChannelResourceDO channelResourceDO);

	Integer deleteChannelResourceById(Integer id);
	
	Integer deleteChannelResource(@Param("item")ChannelResourceDO channelResourceDO);
}
