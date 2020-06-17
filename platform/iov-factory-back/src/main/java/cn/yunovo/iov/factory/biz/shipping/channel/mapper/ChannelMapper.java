package cn.yunovo.iov.factory.biz.shipping.channel.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import cn.yunovo.iov.boot.autoconfigure.mybatis.GeneralMapper;
import cn.yunovo.iov.boot.autoconfigure.request.select.Condition;
import cn.yunovo.iov.factory.biz.shipping.channel.model.ChannelDO;
import cn.yunovo.iov.factory.biz.shipping.channel.model.ChannelQuery;


/**
 * mapper 包为dao包，
 * 注意：方法入参规则
 * 
 * @author huangzz
 *
 */
@Mapper
public interface ChannelMapper extends GeneralMapper<ChannelDO> {

	ChannelDO getChannelById(Integer id);
	
	List<ChannelDO> selectChannel(@Param("query")ChannelQuery channelQuery, @Param("condition")Map<String, Condition> conditionMap);
	Integer insertChannel(ChannelDO channelDO);

	Integer updateChannel(ChannelDO channelDO);

	Integer deleteChannelById(Integer id);
}
