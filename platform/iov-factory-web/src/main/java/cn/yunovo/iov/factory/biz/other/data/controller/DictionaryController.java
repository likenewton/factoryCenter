package cn.yunovo.iov.factory.biz.other.data.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import cn.yunovo.iov.factory.biz.dac.user.model.DacUserDTO;
import cn.yunovo.iov.factory.biz.dac.user.model.DacUserQuery;
import cn.yunovo.iov.factory.biz.dac.user.service.DacUserService;
import cn.yunovo.iov.factory.biz.other.data.model.DictionaryDO;
import cn.yunovo.iov.factory.biz.other.data.model.DictionaryDTO;
import cn.yunovo.iov.factory.biz.other.data.model.DictionaryQuery;
import cn.yunovo.iov.factory.biz.other.data.model.DictionaryVO;
import cn.yunovo.iov.factory.biz.other.data.service.DictionaryService;
import cn.yunovo.iov.factory.framework.LoginInfoUtil;
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
@RequestMapping(value = "/other/dictionarys")
@Api(value = "[]相关 api")
class DictionaryController {
	
	@Autowired
	private DictionaryService dictionaryService;
	
	@Autowired
	private DacUserService dacUserService;

	/*
	 * 分页查询访问方式：GET http://ip:port/other/dictionarys?page=1&page_size=2
	 * 排除查询访问方式：GET http://ip:port/other/dictionarys?name=黄&age=18
	 * 条件查询访问方式：GET http://ip:port/other/dictionarys?sort=age,desc(按年龄倒叙)
	 * 条件查询访问方式：GET http://ip:port/other/dictionarys?descs/ascs=age(按年龄倒叙)
	 * 指定返回记录的数量：GET http://ip:port/other/dictionarys?limit=20
	*/
	@ApiOperation(notes="根据条件获取[推送通道]信息",value = "根据条件获取[推送通道]信息", extensions = @Extension(name = "auditLog", properties = @ExtensionProperty(name = "ignore", value = "true")))
	@RequestMapping(method = RequestMethod.GET)
	public ResultEntity<Object> dictionarys(HttpServletRequest request, DictionaryQuery dictionaryQuery, Pages pages,
			Limit limit, Order order, Group group, Offset offset) {
		ResultEntity<Object> result = new ResultEntity<Object>();
		Map<String,Condition> conditionMap = new HashMap<String,Condition>();
		conditionMap.put(Condition.PAGES, pages);
		conditionMap.put(Condition.LIMIT, limit);
		conditionMap.put(Condition.ORDER, order);
		conditionMap.put(Condition.OFFSET, offset);
		conditionMap.put(Condition.GROUP, group);
		Object obj = dictionaryService.selectDictionary(dictionaryQuery, conditionMap);
		result.setData(obj);
		List<DictionaryVO> list = (List<DictionaryVO>) obj;
		List<DictionaryVO> filterList = new ArrayList<DictionaryVO>();
		Map<String, DictionaryVO> map = new HashMap<String, DictionaryVO>();
		
		// 如何是工厂账号登录，只能看到自己的工厂
		if(2 == LoginInfoUtil.getLoginBaseInfo(request).getUserType()) {
			DacUserQuery dacUserQuery = new DacUserQuery();
			dacUserQuery.setUserId(LoginInfoUtil.getLoginBaseInfo(request).getLoginName());
			DacUserDTO dacUserDTO = dacUserService.queryDacUser(dacUserQuery);
			
			for(DictionaryVO vo :list) {
				if(0 == vo.getWordType()||1 == vo.getWordType()  ) {
					if(dacUserDTO.getUserMapper().equals(vo.getWordKey())) {
						filterList.add(vo);
					}
				}else {
					filterList.add(vo);
				}
			}
			result.setData(filterList);
		}
		
		return result;
	}
	
	// 获取一个对象：GET http://ip:port/other/dictionarys/{id}
	@ApiOperation(notes="根据ID获取[]信息", value = "根据ID获取[]信息", extensions = {
			@Extension(name = "auditLog", properties = {
					@ExtensionProperty(name = "opType", value = "QUERY") }) })
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public DictionaryVO getDictionaryById(@PathVariable("id") Integer id) {
		DictionaryDTO dictionaryDTO = dictionaryService.getDictionaryById(id);
		DictionaryVO dictionaryVO = BeanMapper.map(dictionaryDTO, DictionaryVO.class);
		return dictionaryVO;
	}

	// 删除一个对象：DELETE http://ip:port/other/dictionarys/{id}
	@ApiOperation(notes="根据ID删除[]信息", value = "根据ID删除[]信息", extensions = {
			@Extension(name = "auditLog", properties = {
					@ExtensionProperty(name = "opType", value = "DELETE") }) })
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResultEntity<Integer> deleteDictionaryById(@PathVariable("id") Integer id) {
		ResultEntity<Integer> result = new ResultEntity<>();
		Integer del = dictionaryService.deleteDictionaryById(id);
		log.info("delete DictionaryController delete result[{}]", del);
		return result;
	}

	// 保存一个对象：POST http://ip:port/other/dictionarys
	@ApiOperation(notes="保存[]信息", value = "保存[]信息", extensions = {
			@Extension(name = "auditLog", properties = {
					@ExtensionProperty(name = "opType", value = "INSERT") }) })
	@RequestMapping(method = RequestMethod.POST)
	public DictionaryVO insertDictionary(@Validated @RequestBody DictionaryVO dictionaryVO)
	{
		DictionaryDO dictionaryDO = BeanMapper.map(dictionaryVO, DictionaryDO.class);
		dictionaryService.insertDictionary(dictionaryDO);
		
		DictionaryDTO dictionaryDTO = dictionaryService.getDictionaryById(dictionaryDO.getId());
		dictionaryVO = BeanMapper.map(dictionaryDTO, DictionaryVO.class);
		
		log.info("insertDictionary DictionaryController insert result[{}]", dictionaryVO);
		return dictionaryVO;
	}

	// 用于更新某个资源较完整的内容：PUT http://ip:port/other/dictionarys
	@ApiOperation(notes="更新[]信息", value = "更新[]信息", extensions = {
			@Extension(name = "auditLog", properties = {
					@ExtensionProperty(name = "opType", value = "UPDATE") }) })
	@RequestMapping(method = RequestMethod.PUT)
	public DictionaryVO editDictionary(@Validated @RequestBody DictionaryVO dictionaryVO)
	{
		DictionaryDO dictionaryDO = BeanMapper.map(dictionaryVO, DictionaryDO.class);
		dictionaryService.updateDictionary(dictionaryDO);
		
		DictionaryDTO dictionaryDTO = dictionaryService.getDictionaryById(dictionaryDO.getId());
		dictionaryVO = BeanMapper.map(dictionaryDTO, DictionaryVO.class);
		
		log.info("edit$Dictionary DictionaryController edit result[{}]", dictionaryVO);
		return dictionaryVO ;
	}
	
	// 用于资源的部分内容的更新：PUT http://ip:port/other/dictionarys
	@ApiOperation(notes="部分内容的更新[]信息", value = "部分内容的更新[]信息", extensions = {
			@Extension(name = "auditLog", properties = {
					@ExtensionProperty(name = "opType", value = "UPDATE") }) })
	@RequestMapping(method = RequestMethod.PATCH)
	public DictionaryVO updateDictionary(@RequestBody DictionaryVO dictionaryVO)
	{
		DictionaryDO dictionaryDO = BeanMapper.map(dictionaryVO, DictionaryDO.class);
		dictionaryService.updateDictionary(dictionaryDO);
		
		DictionaryDTO dictionaryDTO = dictionaryService.getDictionaryById(dictionaryDO.getId());
		dictionaryVO = BeanMapper.map(dictionaryDTO, DictionaryVO.class);
		
		log.info("updateDictionary DictionaryController update result[{}]", dictionaryVO);
		return dictionaryVO ;
	}	


}