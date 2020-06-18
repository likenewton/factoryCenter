package cn.yunovo.iov.factory.biz.dac.channel.service;

import java.util.Map;

import cn.yunovo.iov.boot.autoconfigure.request.select.Condition;
import cn.yunovo.iov.factory.biz.dac.channel.model.ChannelResourceDO;
import cn.yunovo.iov.factory.biz.dac.channel.model.ChannelResourceDTO;
import cn.yunovo.iov.factory.biz.dac.channel.model.ChannelResourceQuery;

/**
 * Service层：相对具体的业务逻辑服务层。对多个Manager的组合复用 。
 * 
 * @author huangzz
 *
 */
public interface ChannelResourceService {

	ChannelResourceDTO getChannelResourceById(Integer id);
	
	ChannelResourceDTO queryChannelResource(ChannelResourceQuery channelResourceQuery);

	Object selectChannelResource(ChannelResourceQuery channelResourceQuery, Map<String, Condition> conditionMap);
	
	Integer insertChannelResource(ChannelResourceDO channelResourceDO);

	Integer deleteChannelResourceById(Integer id);

	Integer updateChannelResource(ChannelResourceDO channelResourceDO);
	
    Integer deleteChannelResource(ChannelResourceDO channelResourceDO);

}
