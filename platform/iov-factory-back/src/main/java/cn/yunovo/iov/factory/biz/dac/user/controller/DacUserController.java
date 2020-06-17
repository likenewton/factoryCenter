package cn.yunovo.iov.factory.biz.dac.user.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.ore.framework.web.api.ResultEntity;

import cn.yunovo.iov.framework.commons.beanutils.bean.BeanMapper;
import cn.yunovo.iov.boot.autoconfigure.request.select.Condition;
import cn.yunovo.iov.boot.autoconfigure.request.select.Group;
import cn.yunovo.iov.boot.autoconfigure.request.select.Limit;
import cn.yunovo.iov.boot.autoconfigure.request.select.Offset;
import cn.yunovo.iov.boot.autoconfigure.request.select.Order;
import cn.yunovo.iov.boot.autoconfigure.request.select.Pages;
import cn.yunovo.iov.factory.biz.dac.user.model.DacUserDO;
import cn.yunovo.iov.factory.biz.dac.user.model.DacUserDTO;
import cn.yunovo.iov.factory.biz.dac.user.model.DacUserQuery;
import cn.yunovo.iov.factory.biz.dac.user.model.DacUserVO;
import cn.yunovo.iov.factory.biz.dac.user.service.DacUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ExtensionProperty;
import io.swagger.annotations.Extension;
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
@RequestMapping(value = "/dac/users")
@Api(value = "[]相关 api")
class DacUserController {

	@Autowired
	private DacUserService dacUserService;

	/*
	 * 分页查询访问方式：GET http://ip:port/dac/users?page=1&page_size=2
	 * 排除查询访问方式：GET http://ip:port/dac/users?name=黄&age=18
	 * 条件查询访问方式：GET http://ip:port/dac/users?sort=age,desc(按年龄倒叙)
	 * 条件查询访问方式：GET http://ip:port/dac/users?descs/ascs=age(按年龄倒叙)
	 * 指定返回记录的数量：GET http://ip:port/dac/users?limit=20
	*/
	@ApiOperation(notes="根据条件获取[推送通道]信息",value = "根据条件获取[推送通道]信息", extensions = @Extension(name = "auditLog", properties = @ExtensionProperty(name = "ignore", value = "true")))
	@RequestMapping(method = RequestMethod.GET)
	public ResultEntity<Object> dacUsers(HttpServletRequest request, DacUserQuery dacUserQuery, Pages pages,
			Limit limit, Order order, Group group, Offset offset) {
		ResultEntity<Object> result = new ResultEntity<Object>();
		Map<String,Condition> conditionMap = new HashMap<String,Condition>();
		conditionMap.put(Condition.PAGES, pages);
		conditionMap.put(Condition.LIMIT, limit);
		conditionMap.put(Condition.ORDER, order);
		conditionMap.put(Condition.OFFSET, offset);
		conditionMap.put(Condition.GROUP, group);
		result.setData(dacUserService.selectDacUser(dacUserQuery, conditionMap));
		return result;
	}
	
	// 获取一个对象：GET http://ip:port/dac/users/{id}
	@ApiOperation(notes="根据ID获取[]信息", value = "根据ID获取[]信息", extensions = {
			@Extension(name = "auditLog", properties = {
					@ExtensionProperty(name = "opType", value = "QUERY") }) })
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public DacUserVO getDacUserById(@PathVariable("id") Integer id) {
		DacUserDTO dacUserDTO = dacUserService.getDacUserById(id);
		DacUserVO dacUserVO = BeanMapper.map(dacUserDTO, DacUserVO.class);
		return dacUserVO;
	}

	// 删除一个对象：DELETE http://ip:port/dac/users/{id}
	@ApiOperation(notes="根据ID删除[]信息", value = "根据ID删除[]信息", extensions = {
			@Extension(name = "auditLog", properties = {
					@ExtensionProperty(name = "opType", value = "DELETE") }) })
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResultEntity<Integer> deleteDacUserById(@PathVariable("id") Integer id) {
		ResultEntity<Integer> result = new ResultEntity<>();
		Integer del = dacUserService.deleteDacUserById(id);
		log.info("delete DacUserController delete result[{}]", del);
		return result;
	}

	// 保存一个对象：POST http://ip:port/dac/users
	@ApiOperation(notes="保存[]信息", value = "保存[]信息", extensions = {
			@Extension(name = "auditLog", properties = {
					@ExtensionProperty(name = "opType", value = "INSERT") }) })
	@RequestMapping(method = RequestMethod.POST)
	public DacUserVO insertDacUser(@Validated @RequestBody DacUserVO dacUserVO)
	{
		DacUserDO dacUserDO = BeanMapper.map(dacUserVO, DacUserDO.class);
		dacUserService.insertDacUser(dacUserDO);
		
		DacUserDTO dacUserDTO = dacUserService.getDacUserById(dacUserDO.getId());
		dacUserVO = BeanMapper.map(dacUserDTO, DacUserVO.class);
		
		log.info("insertDacUser DacUserController insert result[{}]", dacUserVO);
		return dacUserVO;
	}

	// 用于更新某个资源较完整的内容：PUT http://ip:port/dac/users
	@ApiOperation(notes="更新[]信息", value = "更新[]信息", extensions = {
			@Extension(name = "auditLog", properties = {
					@ExtensionProperty(name = "opType", value = "UPDATE") }) })
	@RequestMapping(method = RequestMethod.PUT)
	public DacUserVO editDacUser(@Validated @RequestBody DacUserVO dacUserVO)
	{
		DacUserDO dacUserDO = BeanMapper.map(dacUserVO, DacUserDO.class);
		dacUserService.updateDacUser(dacUserDO);
		
		DacUserDTO dacUserDTO = dacUserService.getDacUserById(dacUserDO.getId());
		dacUserVO = BeanMapper.map(dacUserDTO, DacUserVO.class);
		
		log.info("edit$DacUser DacUserController edit result[{}]", dacUserVO);
		return dacUserVO ;
	}
	
	// 用于资源的部分内容的更新：PUT http://ip:port/dac/users
	@ApiOperation(notes="部分内容的更新[]信息", value = "部分内容的更新[]信息", extensions = {
			@Extension(name = "auditLog", properties = {
					@ExtensionProperty(name = "opType", value = "UPDATE") }) })
	@RequestMapping(method = RequestMethod.PATCH)
	public DacUserVO updateDacUser(@RequestBody DacUserVO dacUserVO)
	{
		DacUserDO dacUserDO = BeanMapper.map(dacUserVO, DacUserDO.class);
		dacUserService.updateDacUser(dacUserDO);
		
		DacUserDTO dacUserDTO = dacUserService.getDacUserById(dacUserDO.getId());
		dacUserVO = BeanMapper.map(dacUserDTO, DacUserVO.class);
		
		log.info("updateDacUser DacUserController update result[{}]", dacUserVO);
		return dacUserVO ;
	}	


}