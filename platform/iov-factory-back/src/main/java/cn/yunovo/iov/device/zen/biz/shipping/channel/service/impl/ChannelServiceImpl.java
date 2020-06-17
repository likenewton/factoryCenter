package cn.yunovo.iov.device.zen.biz.shipping.channel.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;

import cn.yunovo.iov.boot.autoconfigure.auditlog.annotation.OpLog;
import cn.yunovo.iov.boot.autoconfigure.auditlog.enums.OpTypeEnum;
import cn.yunovo.iov.boot.autoconfigure.request.SearchCondition;
import cn.yunovo.iov.boot.autoconfigure.request.select.Condition;
import cn.yunovo.iov.device.zen.biz.shipping.channel.manager.ChannelManager;
import cn.yunovo.iov.device.zen.biz.shipping.channel.model.ChannelDO;
import cn.yunovo.iov.device.zen.biz.shipping.channel.model.ChannelDTO;
import cn.yunovo.iov.device.zen.biz.shipping.channel.model.ChannelQuery;
import cn.yunovo.iov.device.zen.biz.shipping.channel.model.ChannelVO;
import cn.yunovo.iov.device.zen.biz.shipping.channel.service.ChannelService;
import cn.yunovo.iov.device.zen.framework.LoginInfoUtil;
import cn.yunovo.iov.device.zen.framework.dac.DacHelper;
import cn.yunovo.iov.device.zen.framework.dac.bean.LoginUser;
import cn.yunovo.iov.framework.commons.beanutils.bean.BeanMapper;
import cn.yunovo.iov.framework.web.PageInfo;

@Service
public class ChannelServiceImpl implements ChannelService {

	@Autowired
	private ChannelManager channelManager;

	@Autowired
	private LoginInfoUtil loginInfoUtil;

	@Override
	public ChannelDTO getChannelById(Integer id) {
		return channelManager.getChannelById(id);
	}

	@Override
	@OpLog(opType = OpTypeEnum.QUERY, opName = "根据条件查询[渠道管理]信息")
	public Object selectChannel(ChannelQuery channelQuery, Map<String, Condition> conditionMap) {
		Page<Object> page = null;

		if(null == channelQuery.getBrandName()) {
			DacHelper.setUser(LoginUser.create().userId(loginInfoUtil.getLoginBaseInfo().getLoginName()).userType(loginInfoUtil.getLoginBaseInfo().getUserType()));
		}
		
		// [分页查询]
		page = SearchCondition.conditionByPages(conditionMap);

		// [排序查询,不能使用OrderByHelper工具类,存在SQL中 limit 和 order by 顺序错误]
		// SearchCondition.conditionByOrder(conditionMap);

		// [分组查询],[偏移量查询]暂不实现支持查询

		// [执行数据库查询]
		List<ChannelDTO> channelBOList = channelManager.selectChannel(channelQuery, conditionMap);
		List<ChannelVO> channelVOList = BeanMapper.mapList(channelBOList, ChannelVO.class);

		// [limit 查询]
		if (SearchCondition.conditionBylimit(conditionMap)) {
			return channelVOList;
		}

		// 返回分页
		if (null != page) {
			PageInfo<Page<Object>> pInfo = SearchCondition.returnPage(page);
			page.addAll(channelVOList);
			return pInfo;
		}

		// 没符合条件查询
		return channelVOList;
	}

	@Override
	public Integer insertChannel(ChannelDO channelDO) {
		DacHelper.setUser(LoginUser.create().userId(loginInfoUtil.getLoginBaseInfo().getLoginName()).userType(loginInfoUtil.getLoginBaseInfo().getUserType()));
		return channelManager.insertChannel(channelDO);
	}

	@Override
	public Integer deleteChannelById(Integer id) {
		DacHelper.setUser(LoginUser.create().userId(loginInfoUtil.getLoginBaseInfo().getLoginName()).userType(loginInfoUtil.getLoginBaseInfo().getUserType()));
		return channelManager.deleteChannelById(id);
	}

	@Override
	public Integer updateChannel(ChannelDO channelDO) {
		return channelManager.updateChannel(channelDO);
	}
}
