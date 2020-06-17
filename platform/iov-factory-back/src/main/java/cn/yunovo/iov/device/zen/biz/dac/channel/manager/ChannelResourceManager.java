package cn.yunovo.iov.device.zen.biz.dac.channel.manager;

import java.util.List;
import java.util.Date;
import java.util.Map;

import cn.yunovo.iov.device.zen.biz.dac.channel.mapper.ChannelResourceMapper;
import cn.yunovo.iov.device.zen.biz.dac.channel.model.ChannelResourceDO;
import cn.yunovo.iov.device.zen.biz.dac.channel.model.ChannelResourceDTO;
import cn.yunovo.iov.device.zen.biz.dac.channel.model.ChannelResourceQuery;
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
public class ChannelResourceManager {

	@Autowired
	private ChannelResourceMapper channelResourceMapper;

	public ChannelResourceDTO getChannelResourceById(Integer id) {
		ChannelResourceDO channelResourceDO = channelResourceMapper.getChannelResourceById(id);
		ChannelResourceDTO channelResourceDTO = BeanMapper.map(channelResourceDO, ChannelResourceDTO.class);
		return channelResourceDTO;
	}
	
	public ChannelResourceDTO queryChannelResource(ChannelResourceQuery channelResourceQuery) {
		ChannelResourceDO channelResourceDO = channelResourceMapper.queryChannelResource(channelResourceQuery);
		ChannelResourceDTO channelResourceDTO = BeanMapper.map(channelResourceDO, ChannelResourceDTO.class);
		return channelResourceDTO;
	}

	public List<ChannelResourceDTO> selectChannelResource(ChannelResourceQuery channelResourceQuery, Map<String, Condition> conditionMap) {
		List<ChannelResourceDO> list = channelResourceMapper.selectChannelResource(channelResourceQuery, conditionMap);
		return BeanMapper.mapList(list, ChannelResourceDTO.class);
	}
	public Integer insertChannelResource(ChannelResourceDO channelResourceDO) {
		channelResourceDO.setCreateTime(new Date());
		channelResourceDO.setUpdateTime(new Date());
		return channelResourceMapper.insertChannelResource(channelResourceDO);
	}

	public Integer deleteChannelResourceById(Integer id) {
		return channelResourceMapper.deleteChannelResourceById(id);
	}

	public Integer updateChannelResource(ChannelResourceDO channelResourceDO) {
		channelResourceDO.setUpdateTime(new Date());
		return channelResourceMapper.updateChannelResource(channelResourceDO);
	}
	
	public Integer deleteChannelResource(ChannelResourceDO channelResourceDO){
		return channelResourceMapper.deleteChannelResource(channelResourceDO);
	}

	
}