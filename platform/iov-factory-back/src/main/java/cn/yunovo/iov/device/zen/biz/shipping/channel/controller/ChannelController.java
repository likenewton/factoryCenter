package cn.yunovo.iov.device.zen.biz.shipping.channel.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.ore.framework.web.api.ResultEntity;
import com.github.ore.framework.web.utils.ResultMessageUtils;

import cn.yunovo.iov.boot.autoconfigure.request.select.Condition;
import cn.yunovo.iov.boot.autoconfigure.request.select.Group;
import cn.yunovo.iov.boot.autoconfigure.request.select.Limit;
import cn.yunovo.iov.boot.autoconfigure.request.select.Offset;
import cn.yunovo.iov.boot.autoconfigure.request.select.Order;
import cn.yunovo.iov.boot.autoconfigure.request.select.Pages;
import cn.yunovo.iov.device.zen.biz.shipping.channel.model.ChannelDO;
import cn.yunovo.iov.device.zen.biz.shipping.channel.model.ChannelDTO;
import cn.yunovo.iov.device.zen.biz.shipping.channel.model.ChannelQuery;
import cn.yunovo.iov.device.zen.biz.shipping.channel.model.ChannelVO;
import cn.yunovo.iov.device.zen.biz.shipping.channel.service.ChannelService;
import cn.yunovo.iov.device.zen.biz.shipping.shipping.model.ShippingListQuery;
import cn.yunovo.iov.device.zen.biz.shipping.shipping.model.ShippingListVO;
import cn.yunovo.iov.device.zen.biz.shipping.shipping.service.ShippingListService;
import cn.yunovo.iov.device.zen.framework.Contants;
import cn.yunovo.iov.device.zen.framework.LoginInfoUtil;
import cn.yunovo.iov.device.zen.framework.dac.DacHelper;
import cn.yunovo.iov.device.zen.framework.tree.CFNode;
import cn.yunovo.iov.device.zen.framework.tree.CFTree;
import cn.yunovo.iov.device.zen.framework.tree.Tree;
import cn.yunovo.iov.framework.commons.beanutils.bean.BeanMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Extension;
import io.swagger.annotations.ExtensionProperty;
import lombok.extern.slf4j.Slf4j;


/**
 * Web层(展示层/显示层)：主要是对访问控制进行转发，各类基本参数校验，或者不复用的业务简单处理等。
 * 
 * @author huangzz
 *
 */
@Slf4j
@Scope("prototype")
@RestController
@RequestMapping(value = "/shipping/channels")
@Api(value = "[渠道管理]相关 api")
class ChannelController {

	@Autowired
	private ChannelService channelService;
	
	@Autowired
	private ShippingListService shippingListService;
	
	@Autowired
	private LoginInfoUtil loginInfoUtil;
	
	/*
	 * 分页查询访问方式：GET http://ip:port/shipping/channels?page=1&page_size=2
	 * 排除查询访问方式：GET http://ip:port/shipping/channels?name=黄&age=18
	 * 条件查询访问方式：GET http://ip:port/shipping/channels?sort=age,desc(按年龄倒叙)
	 * 条件查询访问方式：GET http://ip:port/shipping/channels?descs/ascs=age(按年龄倒叙)
	 * 指定返回记录的数量：GET http://ip:port/shipping/channels?limit=20
	*/
	@SuppressWarnings("unchecked")
	@ApiOperation(notes="根据条件获取[推送通道]信息",value = "根据条件获取[推送通道]信息", extensions = @Extension(name = "auditLog", properties = @ExtensionProperty(name = "ignore", value = "true")))
	@RequestMapping(method = RequestMethod.GET)
	public ResultEntity<Object> channels(HttpServletRequest request, ChannelQuery channelQuery, Pages pages,
			Limit limit, Order order, Group group, Offset offset, Tree tree) {
		ResultEntity<Object> result = new ResultEntity<Object>();
		Map<String,Condition> conditionMap = new HashMap<String,Condition>();
		conditionMap.put(Condition.PAGES, pages);
		conditionMap.put(Condition.LIMIT, limit);
		conditionMap.put(Condition.ORDER, order);
		conditionMap.put(Condition.OFFSET, offset);
		conditionMap.put(Condition.GROUP, group);
		
		if (null != tree.getTree()) {
			conditionMap.put(Condition.PAGES, null);
		}
		
		Object obj = channelService.selectChannel(channelQuery, conditionMap);
		result.setData(obj);
		if (null != tree.getTree()) {
			// 构造树形数据
			CFTree<ChannelVO> channelTree = new CFTree<ChannelVO>();
			channelTree.buildTree((List<ChannelVO>) obj, "getId", "getParentId", "getChannelName");
			//CFNode<ChannelVO> node = channelTree.getNodeSourceMap().get(String.valueOf(1));
			result.setData(channelTree.getTreeTargetMap().values().stream()
	                .collect(Collectors.toList()));
		}
		
		return result;
	}
	
	// 获取一个对象：GET http://ip:port/shipping/channels/{id}
	@ApiOperation(notes="根据ID获取[渠道管理]信息", value = "根据ID获取[渠道管理]信息", extensions = {
			@Extension(name = "auditLog", properties = {
					@ExtensionProperty(name = "opType", value = "QUERY") }) })
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ChannelVO getChannelById(@PathVariable("id") Integer id) {
		ChannelDTO channelDTO = channelService.getChannelById(id);
		ChannelVO channelVO = BeanMapper.map(channelDTO, ChannelVO.class);
		return channelVO;
	}

	// 删除一个对象：DELETE http://ip:port/shipping/channels/{id}
	@ApiOperation(notes="根据ID删除[渠道管理]信息", value = "根据ID删除[渠道管理]信息", extensions = {
			@Extension(name = "auditLog", properties = {
					@ExtensionProperty(name = "opType", value = "DELETE") }) })
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@SuppressWarnings("unchecked")
	public ResultEntity<Integer> deleteChannelById(HttpServletRequest request,@PathVariable("id") Integer id) {
		ResultEntity<Integer> result = new ResultEntity<>();
		ChannelQuery channelQuery = new ChannelQuery();
		channelQuery.setParentId(id);
		Object obj = channelService.selectChannel(channelQuery, null);
		ShippingListQuery shippingListQuery = new ShippingListQuery();
		shippingListQuery.setChannelId(id);
		List<ShippingListVO> shippingList = (List<ShippingListVO>) shippingListService.selectShippingList(shippingListQuery, null);
		List<ChannelVO> list = (List<ChannelVO>) obj;
		if(0 < list.size() || 0 < shippingList.size()) {
			result.setCode(ResultMessageUtils.splitCode(Contants.BIZ_20002));
			result.setMsg(ResultMessageUtils.splitMsg(Contants.BIZ_20002) + ",不能删除该渠道!");
			return result;
		}
		
		Integer del = channelService.deleteChannelById(id);
		log.info("delete ChannelController delete result[{}]", del);
		
		return result;
	}

	// 保存一个对象：POST http://ip:port/shipping/channels
	@ApiOperation(notes="保存[渠道管理]信息", value = "保存[渠道管理]信息", extensions = {
			@Extension(name = "auditLog", properties = {
					@ExtensionProperty(name = "opType", value = "INSERT") }) })
	@RequestMapping(method = RequestMethod.POST)
	public ChannelVO insertChannel(HttpServletRequest request, @Validated @RequestBody ChannelVO channelVO)
	{
		ChannelDO channelDO = BeanMapper.map(channelVO, ChannelDO.class);
		channelService.insertChannel(channelDO);
		ChannelDTO channelDTO = channelService.getChannelById(channelDO.getId());
		channelVO = BeanMapper.map(channelDTO, ChannelVO.class);
		
		// 插入数据权限
		DacHelper.insertChannelResource(Contants.TABLE_CHANNEL_INFO, channelDTO.getId(),channelDTO.getPhone(),loginInfoUtil.getLoginBaseInfo().getLoginName(),loginInfoUtil.getLoginBaseInfo().getUserType());
		DacHelper.insertBrandResource(Contants.TABLE_CHANNEL_INFO, channelDTO.getId(), channelDTO.getBrandName(), loginInfoUtil.getLoginBaseInfo().getLoginName(),loginInfoUtil.getLoginBaseInfo().getUserType());
		
		log.info("insertChannel ChannelController insert result[{}]", channelVO);
		return channelVO;
	}

	// 用于更新某个资源较完整的内容：PUT http://ip:port/shipping/channels
	@SuppressWarnings("unchecked")
	@ApiOperation(notes="更新[渠道管理]信息", value = "更新[渠道管理]信息", extensions = {
			@Extension(name = "auditLog", properties = {
					@ExtensionProperty(name = "opType", value = "UPDATE") }) })
	@RequestMapping(method = RequestMethod.PUT)
	public ChannelVO editChannel(@Validated @RequestBody ChannelVO channelVO)
	{
		ChannelDO channelDO = BeanMapper.map(channelVO, ChannelDO.class);
		ChannelDTO channelDTO = channelService.getChannelById(channelDO.getId());
		if(channelDTO.getParentId() != channelVO.getParentId()) {
			Integer submitLevel = channelVO.getLevel();
			Integer currentLevel = channelDTO.getLevel();
			Integer alevel = submitLevel - currentLevel;
			
			ChannelQuery channelQuery = new ChannelQuery();
			Object obj = channelService.selectChannel(channelQuery, null);
				// 构造树形数据
				CFTree<ChannelVO> channelTree = new CFTree<ChannelVO>();
				channelTree.buildTree((List<ChannelVO>) obj, "getId", "getParentId", "getChannelName");
				CFNode<ChannelVO> node = channelTree.getNodeSourceMap().get(String.valueOf(channelDO.getId()));
				String ids = channelTree.getNodeIds(node);
				String[] ida = ids.split(",");
				for(String id:ida) {
					ChannelDTO channel = channelService.getChannelById(Integer.valueOf(id));
					channel.setLevel(channel.getLevel() + alevel);
					ChannelDO cDO = BeanMapper.map(channel, ChannelDO.class);
					channelService.updateChannel(cDO);
				}
		}
		
		channelService.updateChannel(channelDO);
		channelVO = BeanMapper.map(channelDTO, ChannelVO.class);
		
		log.info("edit$Channel ChannelController edit result[{}]", channelVO);
		return channelVO ;
	}
	
	// 用于资源的部分内容的更新：PUT http://ip:port/shipping/channels
	@ApiOperation(notes="部分内容的更新[渠道管理]信息", value = "部分内容的更新[渠道管理]信息", extensions = {
			@Extension(name = "auditLog", properties = {
					@ExtensionProperty(name = "opType", value = "UPDATE") }) })
	@RequestMapping(method = RequestMethod.PATCH)
	public ChannelVO updateChannel(@RequestBody ChannelVO channelVO)
	{
		ChannelDO channelDO = BeanMapper.map(channelVO, ChannelDO.class);
		channelService.updateChannel(channelDO);
		
		ChannelDTO channelDTO = channelService.getChannelById(channelDO.getId());
		channelVO = BeanMapper.map(channelDTO, ChannelVO.class);
		
		log.info("updateChannel ChannelController update result[{}]", channelVO);
		return channelVO ;
	}	

}