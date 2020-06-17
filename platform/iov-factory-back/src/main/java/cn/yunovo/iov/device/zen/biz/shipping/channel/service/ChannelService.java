package cn.yunovo.iov.device.zen.biz.shipping.channel.service;

import java.util.Map;

import cn.yunovo.iov.boot.autoconfigure.request.select.Condition;
import cn.yunovo.iov.device.zen.biz.shipping.channel.model.ChannelDO;
import cn.yunovo.iov.device.zen.biz.shipping.channel.model.ChannelDTO;
import cn.yunovo.iov.device.zen.biz.shipping.channel.model.ChannelQuery;

/**
 * Service层：相对具体的业务逻辑服务层。对多个Manager的组合复用 。
 * 
 * @author huangzz
 *
 */
public interface ChannelService {

	ChannelDTO getChannelById(Integer id);

	Object selectChannel(ChannelQuery channelQuery, Map<String, Condition> conditionMap);
	
	Integer insertChannel(ChannelDO channelDO);

	Integer deleteChannelById(Integer id);

	Integer updateChannel(ChannelDO channelDO);

}
