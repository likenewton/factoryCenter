package cn.yunovo.iov.factory.biz.dac.channel.service.impl;

import java.util.List;
import java.util.Map;

import cn.yunovo.iov.boot.autoconfigure.auditlog.annotation.OpLog;
import cn.yunovo.iov.boot.autoconfigure.auditlog.enums.OpTypeEnum;
import cn.yunovo.iov.boot.autoconfigure.request.SearchCondition;
import cn.yunovo.iov.framework.commons.beanutils.bean.BeanMapper;
import cn.yunovo.iov.framework.web.PageInfo;
import cn.yunovo.iov.boot.autoconfigure.request.select.Condition;
import cn.yunovo.iov.factory.biz.dac.channel.manager.ChannelResourceManager;
import cn.yunovo.iov.factory.biz.dac.channel.model.ChannelResourceDO;
import cn.yunovo.iov.factory.biz.dac.channel.model.ChannelResourceDTO;
import cn.yunovo.iov.factory.biz.dac.channel.model.ChannelResourceQuery;
import cn.yunovo.iov.factory.biz.dac.channel.model.ChannelResourceVO;
import cn.yunovo.iov.factory.biz.dac.channel.service.ChannelResourceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;

@Service
public class ChannelResourceServiceImpl implements ChannelResourceService {

	@Autowired
	private ChannelResourceManager channelResourceManager;

	@Override
	public ChannelResourceDTO getChannelResourceById(Integer id) {
		return channelResourceManager.getChannelResourceById(id);
	}
	
	@Override
	public ChannelResourceDTO queryChannelResource(ChannelResourceQuery channelResourceQuery) {
		return channelResourceManager.queryChannelResource(channelResourceQuery);
	}
	
	@Override
	@OpLog( opType=OpTypeEnum.QUERY, opName = "根据条件查询[数据权限：资源，主体，规则关系表]信息")
	public Object selectChannelResource(ChannelResourceQuery channelResourceQuery, Map<String, Condition> conditionMap) {
		Page<Object> page = null;

		// [分页查询]
		page = SearchCondition.conditionByPages(conditionMap);

		// [排序查询,不能使用OrderByHelper工具类,存在SQL中 limit 和 order by 顺序错误,Mybatis的分页拦截器和排序拦截器冲突]
		// SearchCondition.conditionByOrder(conditionMap);

		// [分组查询],[偏移量查询]暂不实现支持查询

		// [执行数据库查询]
		List<ChannelResourceDTO> channelResourceBOList = channelResourceManager.selectChannelResource(channelResourceQuery, conditionMap);
		List<ChannelResourceVO> channelResourceVOList = BeanMapper.mapList(channelResourceBOList, ChannelResourceVO.class);

		// [limit 查询]
		if (SearchCondition.conditionBylimit(conditionMap)) {
			return channelResourceVOList;
		}

		// 返回分页
		if (null != page) {
			PageInfo<Page<Object>> pInfo = SearchCondition.returnPage(page);
			page.addAll(channelResourceVOList);
			return pInfo;
		}

		// 没符合条件查询
		return channelResourceVOList;
	}
	@Override
	public Integer insertChannelResource(ChannelResourceDO channelResourceDO) {
		return channelResourceManager.insertChannelResource(channelResourceDO);
	}

	@Override
	public Integer deleteChannelResourceById(Integer id) {
		return channelResourceManager.deleteChannelResourceById(id);
	}

	@Override
	public Integer updateChannelResource(ChannelResourceDO channelResourceDO) {
		return channelResourceManager.updateChannelResource(channelResourceDO);
	}
	
	@Override
	public Integer deleteChannelResource(ChannelResourceDO channelResourceDO){
		return channelResourceManager.deleteChannelResource(channelResourceDO);
	}
}
