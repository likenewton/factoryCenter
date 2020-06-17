package cn.yunovo.iov.factory.biz.shipping.channel.manager;

import java.util.List;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.yunovo.iov.framework.commons.beanutils.bean.BeanMapper;
import cn.yunovo.iov.boot.autoconfigure.request.select.Condition;
import cn.yunovo.iov.factory.biz.shipping.channel.mapper.ChannelMapper;
import cn.yunovo.iov.factory.biz.shipping.channel.model.ChannelDO;
import cn.yunovo.iov.factory.biz.shipping.channel.model.ChannelDTO;
import cn.yunovo.iov.factory.biz.shipping.channel.model.ChannelQuery;

/**
 * Manager层：通用业务处理层
 * @author huangzz
 *
 */
@Component
public class ChannelManager {

	@Autowired
	private ChannelMapper channelMapper;

	public ChannelDTO getChannelById(Integer id) {
		ChannelDO channelDO = channelMapper.getChannelById(id);
		ChannelDTO ChannelDTO = BeanMapper.map(channelDO, ChannelDTO.class);
		return ChannelDTO;
	}

	public List<ChannelDTO> selectChannel(ChannelQuery channelQuery, Map<String, Condition> conditionMap) {
		List<ChannelDO> list = channelMapper.selectChannel(channelQuery, conditionMap);
		return BeanMapper.mapList(list, ChannelDTO.class);
	}
	public Integer insertChannel(ChannelDO channelDO) {
		channelDO.setCreateTime(new Date());
		channelDO.setUpdateTime(new Date());
		return channelMapper.insertChannel(channelDO);
	}

	public Integer deleteChannelById(Integer id) {
		return channelMapper.deleteChannelById(id);
	}

	public Integer updateChannel(ChannelDO channelDO) {
		channelDO.setUpdateTime(new Date());
		return channelMapper.updateChannel(channelDO);
	}
}