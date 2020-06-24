package cn.yunovo.iov.factory.biz.shipping.shipping.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jasig.cas.client.validation.Assertion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.github.ore.framework.web.api.ResultEntity;
import com.github.ore.framework.web.utils.ResultMessageUtils;

import cn.yunovo.iov.boot.autoconfigure.cas.h5.bean.LoginInfo;
import cn.yunovo.iov.boot.autoconfigure.dac.DacHelper;
import cn.yunovo.iov.boot.autoconfigure.dac.bean.UserInfo;
import cn.yunovo.iov.boot.autoconfigure.request.select.Condition;
import cn.yunovo.iov.boot.autoconfigure.request.select.Group;
import cn.yunovo.iov.boot.autoconfigure.request.select.Limit;
import cn.yunovo.iov.boot.autoconfigure.request.select.Offset;
import cn.yunovo.iov.boot.autoconfigure.request.select.Order;
import cn.yunovo.iov.boot.autoconfigure.request.select.Pages;
import cn.yunovo.iov.cas.client.authentication.H5ClientAuthenticationFilter;
import cn.yunovo.iov.factory.biz.dac.brand.model.BrandResourceDTO;
import cn.yunovo.iov.factory.biz.dac.brand.model.BrandResourceQuery;
import cn.yunovo.iov.factory.biz.dac.brand.service.BrandResourceService;
import cn.yunovo.iov.factory.biz.dac.channel.model.ChannelResourceDTO;
import cn.yunovo.iov.factory.biz.dac.channel.model.ChannelResourceQuery;
import cn.yunovo.iov.factory.biz.dac.channel.service.ChannelResourceService;
import cn.yunovo.iov.factory.biz.dac.factory.model.FactoryResourceDTO;
import cn.yunovo.iov.factory.biz.dac.factory.model.FactoryResourceQuery;
import cn.yunovo.iov.factory.biz.dac.factory.service.FactoryResourceService;
import cn.yunovo.iov.factory.biz.dac.flogistics.model.FlogisticsResourceDTO;
import cn.yunovo.iov.factory.biz.dac.flogistics.model.FlogisticsResourceQuery;
import cn.yunovo.iov.factory.biz.dac.flogistics.service.FlogisticsResourceService;
import cn.yunovo.iov.factory.biz.dac.resource.model.DataResourceDTO;
import cn.yunovo.iov.factory.biz.dac.resource.model.DataResourceQuery;
import cn.yunovo.iov.factory.biz.dac.resource.service.DataResourceService;
import cn.yunovo.iov.factory.biz.dac.user.model.DacUserDTO;
import cn.yunovo.iov.factory.biz.dac.user.model.DacUserQuery;
import cn.yunovo.iov.factory.biz.dac.user.service.DacUserService;
import cn.yunovo.iov.factory.biz.other.data.model.DictionaryDTO;
import cn.yunovo.iov.factory.biz.other.data.model.DictionaryQuery;
import cn.yunovo.iov.factory.biz.other.data.model.DictionaryVO;
import cn.yunovo.iov.factory.biz.other.data.service.DictionaryService;
import cn.yunovo.iov.factory.biz.shipping.channel.model.ChannelDTO;
import cn.yunovo.iov.factory.biz.shipping.channel.service.ChannelService;
import cn.yunovo.iov.factory.biz.shipping.shipping.model.ShippingDeviceDO;
import cn.yunovo.iov.factory.biz.shipping.shipping.model.ShippingListDO;
import cn.yunovo.iov.factory.biz.shipping.shipping.model.ShippingListDTO;
import cn.yunovo.iov.factory.biz.shipping.shipping.model.ShippingListQuery;
import cn.yunovo.iov.factory.biz.shipping.shipping.model.ShippingListVO;
import cn.yunovo.iov.factory.biz.shipping.shipping.service.ShippingDeviceService;
import cn.yunovo.iov.factory.biz.shipping.shipping.service.ShippingListService;
import cn.yunovo.iov.factory.biz.statistics.shipping.model.StatisticsShippingDO;
import cn.yunovo.iov.factory.biz.statistics.shipping.model.StatisticsShippingDTO;
import cn.yunovo.iov.factory.biz.statistics.shipping.model.StatisticsShippingQuery;
import cn.yunovo.iov.factory.biz.statistics.shipping.model.StatisticsShippingVO;
import cn.yunovo.iov.factory.biz.statistics.shipping.service.StatisticsShippingService;
import cn.yunovo.iov.factory.framework.Contants;
import cn.yunovo.iov.factory.framework.LoginInfoUtil;
import cn.yunovo.iov.factory.framework.dac.DacResourceHelper;
import cn.yunovo.iov.framework.commons.beanutils.bean.BeanMapper;
import cn.yunovo.iov.framework.commons.lang.date.DateFormatConstants;
import cn.yunovo.iov.framework.commons.lang.date.DateGeneralUtils;
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
@RequestMapping(value = "/shipping/lists")
@Api(value = "[发货导入清单]相关 api")
class ShippingListController {

	@Autowired
	private ShippingListService shippingListService;

	@Autowired
	private ChannelService channelService;

	@Autowired
	private ShippingDeviceService shippingDeviceService;

	@Autowired
	private StatisticsShippingService statisticsShippingService;
	
	@Autowired
	private BrandResourceService brandResourceService;
	
	@Autowired
	private ChannelResourceService channelResourceService;
	
	@Autowired
	private FactoryResourceService factoryResourceService;
	
	@Autowired
	private FlogisticsResourceService flogisticsResourceService;
	
	@Autowired
	private DataResourceService dataResourceService;

	
	@Autowired
	private DictionaryService dictionaryService;

	private static Map<String, String> dictYearMap;

	private static Map<String, String> dictMonthMap;
	
	@Autowired
	private DacUserService dacUserService;

	private static Map<String, String> suffixFile = new HashMap<String, String>();

	static {
		suffixFile.put("xls", "xls");
		suffixFile.put("xlsx", "xlsx");
	}

	@SuppressWarnings("unchecked")
	private void statistics(ShippingListVO shippingListVO, String opt, HttpServletRequest request, ChannelDTO channelDTO, String typeId) {
		getDac(request, typeId);

		// 统计发货数据
		StatisticsShippingQuery statisticsShippingQuery = new StatisticsShippingQuery();
		statisticsShippingQuery.setArea(shippingListVO.getArea());
		statisticsShippingQuery.setBrandName(shippingListVO.getBrandName());
		statisticsShippingQuery.setFactoryName(shippingListVO.getFactoryName());
		statisticsShippingQuery.setChannelId(shippingListVO.getChannelId());

		// 查询是否存在统计数据
		DacHelper.skip(false);
		List<StatisticsShippingVO> list = (List<StatisticsShippingVO>) statisticsShippingService.selectStatisticsShipping(statisticsShippingQuery, null);

		// 新增发货分组
		if (null != list && 0 == list.size()) {
			Date day = new Date();
			StatisticsShippingDO statisticsShippingDO = new StatisticsShippingDO();
			statisticsShippingDO.setArea(shippingListVO.getArea());
			statisticsShippingDO.setBrandName(shippingListVO.getBrandName());
			statisticsShippingDO.setFactoryName(shippingListVO.getFactoryName());
			statisticsShippingDO.setChannelId(shippingListVO.getChannelId());
			statisticsShippingDO.setUpdateTime(day);
			statisticsShippingDO.setLastImporttime(day);
			statisticsShippingDO.setChannelId(shippingListVO.getChannelId());
			statisticsShippingDO.setDeviceNumber(shippingListVO.getDeviceNumber());
			statisticsShippingService.insertStatisticsShipping(statisticsShippingDO);
			shippingListVO.setGroupId(statisticsShippingDO.getId());
			ShippingListDO shippingListDO = BeanMapper.map(shippingListVO, ShippingListDO.class);
			shippingListService.updateShippingList(shippingListDO);

			// 插入数据权限
			UserInfo loginUser = DacHelper.getUser();
			if (3 != loginUser.getUserType()) {
				DacResourceHelper.insertChannelResource(Contants.TABLE_STATISTICS_SHIPPING, statisticsShippingDO.getId(), channelDTO.getPhone(), LoginInfoUtil.getLoginBaseInfo(request).getLoginName());
			}
			if (1 != loginUser.getUserType()) {
				DacResourceHelper.insertBrandResource(Contants.TABLE_STATISTICS_SHIPPING, statisticsShippingDO.getId(), channelDTO.getBrandName(), LoginInfoUtil.getLoginBaseInfo(request).getLoginName());
			}
		} else {
			
			//设置发货组ID值	
			Integer groupId = null;
			if (null != typeId) {
				groupId = Integer.valueOf(typeId);
			} else {
				groupId = list.get(0).getId();
			}
			
			// 先更新再统计
			shippingListVO.setGroupId(groupId);
			ShippingListDO shippingListDO = BeanMapper.map(shippingListVO, ShippingListDO.class);
			shippingListService.updateShippingList(shippingListDO);
			
			// 统计所有设备数量
			DacHelper.skip(true);
			statisticsShippingQuery.setId(groupId);
			StatisticsShippingDTO statisticsShippingDTO = statisticsShippingService.statisticsShipping(statisticsShippingQuery);
			DacHelper.clearProvider();
			DacHelper.clearSkip();

			if ("del".equals(opt) && null == statisticsShippingDTO) {
				statisticsShippingService.deleteStatisticsShippingById(groupId);
			} else {
				StatisticsShippingDO statisticsShippingDO = BeanMapper.map(statisticsShippingDTO, StatisticsShippingDO.class);
				statisticsShippingDO.setId(groupId);
				statisticsShippingService.updateStatisticsShipping(statisticsShippingDO);
			}
		}
	}

	private String getLoginBaseInfo(HttpServletRequest request) {
		if (null != request.getAttribute(H5ClientAuthenticationFilter.CONST_CAS_ASSERTION)) {
			Assertion assertion = (Assertion) request.getAttribute(H5ClientAuthenticationFilter.CONST_CAS_ASSERTION);
			Map<String, Object> attr = assertion.getPrincipal().getAttributes();
			LoginInfo loginInfo = new LoginInfo();
			loginInfo.setLoginName(String.valueOf(attr.get("loginName")));
			loginInfo.setUserName(String.valueOf(attr.get("userName")));
			loginInfo.setId(String.valueOf(attr.get("id")));
			loginInfo.setOrganCode(String.valueOf(attr.get("organCode")));
			return loginInfo.getLoginName();
		}
		return null;
	}

	private void getDac(HttpServletRequest request, String typeId) {
		if (0 == LoginInfoUtil.getLoginBaseInfo(request).getUserType()) {
			String idSring = request.getParameter("id");
			if(null == idSring) {
				idSring = typeId;
			}
			if(StringUtils.isNotBlank(idSring)) {
				Integer id = Integer.valueOf(idSring);
				DataResourceQuery dataResourceQuery = new DataResourceQuery();
				dataResourceQuery.setDataProvider(Contants.TABLE_STATISTICS_SHIPPING);
				dataResourceQuery.setDataId(id);
				
				// 查询平台用户权限
				DataResourceDTO dataResourceDTO = dataResourceService.queryDataResource(dataResourceQuery);
				if (null != dataResourceDTO) {
					UserInfo loginUser = DacHelper.getUser();
					loginUser.userType(0);
					if(null != dataResourceDTO.getSourceCreatorId()) {
						dataResourceDTO = null;
					}else {
						loginUser.loginName(dataResourceDTO.getCreatorId());
						return;
					}
				}
				
				// 查询品牌数据权限
				BrandResourceDTO brandResourceDTO = null;
				if(null == dataResourceDTO) {
					BrandResourceQuery brandResourceQuery = new BrandResourceQuery();
					brandResourceQuery.setDataProvider(Contants.TABLE_STATISTICS_SHIPPING);
					brandResourceQuery.setDataId(id);
					brandResourceDTO = brandResourceService.queryBrandResource(brandResourceQuery);
					if (null != brandResourceDTO) {
						UserInfo loginUser = DacHelper.getUser();
						loginUser.userType(1);
						if(null != brandResourceDTO.getSourceCreatorId()) {
							brandResourceDTO = null;
						}else {
							loginUser.loginName(brandResourceDTO.getCreatorId());
							return;
						}
					}
					
				}
				
				// 查询工厂数据权限
				FactoryResourceDTO factoryResourceDTO = null;
				if(null == brandResourceDTO) {
					FactoryResourceQuery factoryResourceQuery = new FactoryResourceQuery();
					factoryResourceQuery.setDataProvider(Contants.TABLE_STATISTICS_SHIPPING);
					factoryResourceQuery.setDataId(id);
					factoryResourceDTO = factoryResourceService.queryFactoryResource(factoryResourceQuery);
					if (null != factoryResourceDTO) {
						UserInfo loginUser = DacHelper.getUser();
						loginUser.userType(2);
						if(null != factoryResourceDTO.getSourceCreatorId()) {
							factoryResourceDTO = null;
						}else {
							loginUser.loginName(factoryResourceDTO.getCreatorId());
							return;
						}
					}
				}
				
				// 查询渠道数据权限
				ChannelResourceDTO channelResourceDTO = null;
				if(null == factoryResourceDTO) {
					ChannelResourceQuery channelResourceQuery = new ChannelResourceQuery();
					channelResourceQuery.setDataProvider(Contants.TABLE_STATISTICS_SHIPPING);
					channelResourceQuery.setDataId(id);
					channelResourceDTO = channelResourceService.queryChannelResource(channelResourceQuery);
					if (null != channelResourceDTO) {
						UserInfo loginUser = DacHelper.getUser();
						loginUser.userType(3);
						if(null != channelResourceDTO.getSourceCreatorId()) {
							channelResourceDTO = null;
						}else {
							loginUser.loginName(channelResourceDTO.getCreatorId());
							return;
						}
					}
					
				}
				
				// 查询物流数据权限
				FlogisticsResourceDTO flogisticsResourceDTO = null;
				if(null == channelResourceDTO) {
					FlogisticsResourceQuery flogisticsResourceQuery = new FlogisticsResourceQuery();
					flogisticsResourceQuery.setDataProvider(Contants.TABLE_STATISTICS_SHIPPING);
					flogisticsResourceQuery.setDataId(id);
					flogisticsResourceDTO = flogisticsResourceService.queryFlogisticsResource(flogisticsResourceQuery);
					if (null != flogisticsResourceDTO) {
						UserInfo loginUser = DacHelper.getUser();
						loginUser.userType(4);
						if(null != flogisticsResourceDTO.getSourceCreatorId()) {
							flogisticsResourceDTO = null;
						}else {
							loginUser.loginName(flogisticsResourceDTO.getCreatorId());
							return;
						}
					}
				}
			}
			}
			
			
	}

	/*
	 * 分页查询访问方式：GET http://ip:port/shipping/lists?page=1&page_size=2 排除查询访问方式：GET http://ip:port/shipping/lists?name=黄&age=18 条件查询访问方式：GET http://ip:port/shipping/lists?sort=age,desc(按年龄倒叙) 条件查询访问方式：GET http://ip:port/shipping/lists?descs/ascs=age(按年龄倒叙)
	 * 指定返回记录的数量：GET http://ip:port/shipping/lists?limit=20
	 */
	@ApiOperation(notes = "根据条件获取[推送通道]信息", value = "根据条件获取[推送通道]信息", extensions = @Extension(name = "auditLog", properties = @ExtensionProperty(name = "ignore", value = "true")))
	@RequestMapping(method = { RequestMethod.GET })
	public ResultEntity<Object> shippingLists(HttpServletRequest request, ShippingListQuery shippingListQuery, Pages pages, Limit limit, Order order, Group group, Offset offset) {
		ResultEntity<Object> result = new ResultEntity<Object>();
		Map<String, Condition> conditionMap = new HashMap<String, Condition>();
		conditionMap.put(Condition.PAGES, pages);
		conditionMap.put(Condition.LIMIT, limit);
		conditionMap.put(Condition.ORDER, order);
		conditionMap.put(Condition.OFFSET, offset);
		conditionMap.put(Condition.GROUP, group);
		String idSring = request.getParameter("id");
		if(StringUtils.isNotBlank(idSring)) {
			Integer groupId = Integer.valueOf(idSring);
			shippingListQuery.setGroupId(groupId);
		}
		DacHelper.skip();
		result.setData(shippingListService.selectShippingList(shippingListQuery, conditionMap));
		DacHelper.clearProvider();
		DacHelper.clearSkip();
		return result;
	}

	// 获取一个对象：GET http://ip:port/shipping/lists/{id}
	@ApiOperation(notes = "根据ID获取[发货导入清单]信息", value = "根据ID获取[发货导入清单]信息", extensions = { @Extension(name = "auditLog", properties = { @ExtensionProperty(name = "opType", value = "QUERY") }) })
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ShippingListVO getShippingListById(@PathVariable("id") Integer id) {
		ShippingListDTO shippingListDTO = shippingListService.getShippingListById(id);
		ShippingListVO shippingListVO = BeanMapper.map(shippingListDTO, ShippingListVO.class);
		return shippingListVO;
	}

	// 删除一个对象：DELETE http://ip:port/shipping/lists/{id}
	@ApiOperation(notes = "根据ID删除[发货导入清单]信息", value = "根据ID删除[发货导入清单]信息", extensions = { @Extension(name = "auditLog", properties = { @ExtensionProperty(name = "opType", value = "DELETE") }) })
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResultEntity<Integer> deleteShippingListById(HttpServletRequest request, @PathVariable("id") Integer id) {
		ResultEntity<Integer> result = new ResultEntity<>();
		ShippingListDTO shippingListDTO = shippingListService.getShippingListById(id);
		Integer del = shippingListService.deleteShippingListById(id);
		shippingDeviceService.deleteShippingDeviceByShippingId(id);

		// 统计数据
		ShippingListVO shippingListVO = new ShippingListVO();
		shippingListVO.setArea(shippingListDTO.getArea());
		shippingListVO.setBrandName(shippingListDTO.getBrandName());
		shippingListVO.setFactoryName(shippingListDTO.getFactoryName());
		shippingListVO.setChannelId(shippingListDTO.getChannelId());
		statistics(shippingListVO, "del", request, null,request.getParameter("statisticsId"));

		log.info("delete ShippingListController delete result[{}]", del);
		return result;
	}

	// 保存一个对象：POST http://ip:port/shipping/lists
	@SuppressWarnings("unchecked")
	@ApiOperation(notes = "保存[发货导入清单]信息", value = "保存[发货导入清单]信息", extensions = { @Extension(name = "auditLog", properties = { @ExtensionProperty(name = "opType", value = "INSERT") }) })
	@RequestMapping(method = RequestMethod.POST)
	public ResultEntity<Integer> insertShippingList(HttpServletRequest request, @Validated ShippingListVO shippingListVO) throws ParseException {
		ResultEntity<Integer> result = new ResultEntity<>();
		if (null != shippingListVO.getImeis() && !(shippingListVO.getImeis() instanceof String)) {
			MultipartFile file = (MultipartFile) shippingListVO.getImeis();
			String[] strArray = file.getOriginalFilename().split("\\.");
			int suffixIndex = strArray.length - 1;
			String subfix = strArray[suffixIndex];
			if (!suffixFile.containsKey(subfix)) {
				result.setCode(ResultMessageUtils.splitCode(Contants.BIZ_20006));
				result.setMsg(ResultMessageUtils.splitMsg(Contants.BIZ_20006));
				return result;
			}
		}
		
		
		//判断是否够3为云智码
		if (StringUtils.isNoneBlank(shippingListVO.getYunovoCode()) && 2 >= shippingListVO.getYunovoCode().length()) {
			result.setCode(ResultMessageUtils.splitCode(Contants.BIZ_20013));
			result.setMsg(ResultMessageUtils.splitMsg(Contants.BIZ_20013));
			return result;
		}

		// 检查第一位字母获取云智码解析厂
		String factoryName = "";
		if (StringUtils.isNoneBlank(shippingListVO.getYunovoCode()) && 1 <= shippingListVO.getYunovoCode().length()) {
			factoryName = String.valueOf(shippingListVO.getYunovoCode().charAt(0));
		}

		DictionaryQuery dictionaryQuery = new DictionaryQuery();
		dictionaryQuery.setWordKey(factoryName);
		dictionaryQuery.setWordType(0);
		DictionaryDTO dictionaryDTO = dictionaryService.queryDictionary(dictionaryQuery);
		if (null == dictionaryDTO) {
			result.setCode(ResultMessageUtils.splitCode(Contants.BIZ_20011));
			result.setMsg(ResultMessageUtils.splitMsg(Contants.BIZ_20011));
			return result;
		}

		// 检查第二位字母获取云智码解析厂
		if (StringUtils.isNoneBlank(shippingListVO.getYunovoCode()) && 2 <= shippingListVO.getYunovoCode().length()) {
			factoryName = String.valueOf(shippingListVO.getYunovoCode().charAt(1));
		}

		dictionaryQuery = new DictionaryQuery();
		dictionaryQuery.setWordKey(factoryName);
		dictionaryQuery.setWordType(0);
		dictionaryDTO = dictionaryService.queryDictionary(dictionaryQuery);
		if (null == dictionaryDTO) {
			result.setCode(ResultMessageUtils.splitCode(Contants.BIZ_20011));
			result.setMsg(ResultMessageUtils.splitMsg(Contants.BIZ_20011));
			return result;
		}

		// 检查第三位字母获取云智码解析厂
		if (StringUtils.isNoneBlank(shippingListVO.getYunovoCode()) && 3 <= shippingListVO.getYunovoCode().length()) {
			factoryName = String.valueOf(shippingListVO.getYunovoCode().charAt(2));
		}

		// 检查填写的组装厂是否配置再数据字段表
		dictionaryQuery = new DictionaryQuery();
		dictionaryQuery.setWordKey(factoryName);
		dictionaryQuery.setWordType(1);
		dictionaryDTO = dictionaryService.queryDictionary(dictionaryQuery);
		if (null == dictionaryDTO) {
			result.setCode(ResultMessageUtils.splitCode(Contants.BIZ_20012));
			result.setMsg(ResultMessageUtils.splitMsg(Contants.BIZ_20012));
			return result;
		}

		// 如何是工厂账号登录，只能看到自己的工厂
		if (2 == LoginInfoUtil.getLoginBaseInfo(request).getUserType()) {
			DacUserQuery dacUserQuery = new DacUserQuery();
			dacUserQuery.setUserId(LoginInfoUtil.getLoginBaseInfo(request).getLoginName());
			DacUserDTO dacUserDTO = dacUserService.queryDacUser(dacUserQuery);
			if (null != dacUserDTO && !factoryName.equals(dacUserDTO.getUserMapper())) {
				result.setCode(ResultMessageUtils.splitCode(Contants.BIZ_20014));
				result.setMsg(ResultMessageUtils.splitMsg(Contants.BIZ_20014));
				return result;
			}
		}
				
		// 获取云智码解析组装厂
		String productDate = null;
		if ((StringUtils.isBlank(shippingListVO.getProductDate()) || "null".equals(shippingListVO.getProductDate())) && StringUtils.isNoneBlank(shippingListVO.getYunovoCode()) && 16 <= shippingListVO.getYunovoCode().length()) {

			if (null == dictYearMap) {
				dictionaryQuery = new DictionaryQuery();
				dictionaryQuery.setWordType(7);
				List<DictionaryVO> list = (List<DictionaryVO>) dictionaryService.selectDictionary(dictionaryQuery, null);
				dictYearMap = list.stream().collect(Collectors.toMap(DictionaryVO::getWordKey, DictionaryVO::getWordValue));

				dictionaryQuery.setWordType(8);
				list = (List<DictionaryVO>) dictionaryService.selectDictionary(dictionaryQuery, null);
				dictMonthMap = list.stream().collect(Collectors.toMap(DictionaryVO::getWordKey, DictionaryVO::getWordValue));
			}

			char a = shippingListVO.getYunovoCode().charAt(14);
			String year = dictYearMap.get(String.valueOf(a));
			if (null != year) {
				char b = shippingListVO.getYunovoCode().charAt(15);
				String month = dictMonthMap.get(String.valueOf(b));
				productDate = year + "-" + month;
			}
			shippingListVO.setProductDate(productDate);
		}

		DacHelper.skip();
		ChannelDTO channelDTO = channelService.getChannelById(shippingListVO.getChannelId());
		shippingListVO.setArea(channelDTO.getArea());

		shippingListVO.setFactoryName(factoryName);
		ShippingListDO shippingListDO = BeanMapper.map(shippingListVO, ShippingListDO.class);
		shippingListDO.setImportTime(new Date());
		shippingListDO.setDeviceNumber(0);

		shippingListDO.setProductDate(shippingListVO.getProductDate());

		List<ShippingDeviceDO> listDevice = new ArrayList<ShippingDeviceDO>();
		if (shippingListVO.getImeis() instanceof String) {
			String imeiString = shippingListVO.getImeis().toString();
			if (StringUtils.isNotBlank(imeiString)) {
				String[] imeis = imeiString.split("\\n");
				if (0 < imeis.length) {
					for (String imei : imeis) {
						ShippingDeviceDO shippingDeviceDO = new ShippingDeviceDO();
						shippingDeviceDO.setCreateTime(new Date());
						shippingDeviceDO.setImei(imei);
						shippingDeviceDO.setShippingId(shippingListDO.getId());
						listDevice.add(shippingDeviceDO);
					}
				}
			}
		} else {

			if (null != shippingListVO.getImeis()) {
				MultipartFile file = (MultipartFile) shippingListVO.getImeis();
				XSSFWorkbook wb = null;
				try {
					wb = new XSSFWorkbook(file.getInputStream());
					Sheet sheet = wb.getSheetAt(0);
					int rowNum = sheet.getPhysicalNumberOfRows();
					if (1 < rowNum) {
						int coloumNum = sheet.getRow(0).getPhysicalNumberOfCells();
						String imeiName = sheet.getRow(0).getCell(0).getStringCellValue();
						if (1 == coloumNum && "imei".equals(imeiName.toLowerCase())) {

							for (int r = 1; r < rowNum; r++) {
								Row row = sheet.getRow(r);
								for (int c = 0; c < coloumNum; c++) {
									Cell cell = row.getCell(c);
									if (null == cell) {
										continue;
									}
									cell.setCellType(Cell.CELL_TYPE_STRING);
									if (0 == c) {
										String imei = cell.getStringCellValue().trim();
										if (StringUtils.isNotBlank(imei)) {
											ShippingDeviceDO shippingDeviceDO = new ShippingDeviceDO();
											shippingDeviceDO.setCreateTime(new Date());
											shippingDeviceDO.setShippingId(shippingListDO.getId());
											shippingDeviceDO.setImei(imei);
											listDevice.add(shippingDeviceDO);
										}

									}
								}

							}
						}
					}
					if (0 == listDevice.size()) {
						result.setCode(ResultMessageUtils.splitCode(Contants.BIZ_20010));
						result.setMsg(ResultMessageUtils.splitMsg(Contants.BIZ_20010));
						return result;
					}

				} catch (IOException e) {
					e.printStackTrace();
					result.setCode(ResultMessageUtils.splitCode(Contants.BIZ_20006));
					result.setMsg(ResultMessageUtils.splitMsg(Contants.BIZ_20006));
					return result;
				}
			}
		}

		shippingListService.insertShippingList(shippingListDO);
		for (ShippingDeviceDO shippingDeviceDO : listDevice) {
			shippingDeviceDO.setShippingId(shippingListDO.getId());
		}

		shippingListDO.setOperator(getLoginBaseInfo(request));
		shippingListDO.setDeviceNumber(listDevice.size());
		if (0 < listDevice.size()) {
			shippingDeviceService.batchInsertDevice(listDevice);
		}
		shippingListService.updateShippingList(shippingListDO);

		ShippingListDTO shippingListDTO = shippingListService.getShippingListById(shippingListDO.getId());
		shippingListVO = BeanMapper.map(shippingListDTO, ShippingListVO.class);

		// 统计数据
		statistics(shippingListVO, "insert", request, channelDTO,null);
		result.setData(listDevice.size());

		// 插入数据权限
		UserInfo loginUser = DacHelper.getUser();
		if(3 != loginUser.getUserType()) {
			DacResourceHelper.insertChannelResource(Contants.TABLE_SHIPPING_LIST, shippingListDO.getId(), channelDTO.getPhone(), LoginInfoUtil.getLoginBaseInfo(request).getLoginName());
		}
		if(1 != loginUser.getUserType()) {
			DacResourceHelper.insertBrandResource(Contants.TABLE_SHIPPING_LIST, shippingListDO.getId(), channelDTO.getBrandName(), LoginInfoUtil.getLoginBaseInfo(request).getLoginName());
		}
		
		log.info("insertShippingList ShippingListController insert result[{}]", shippingListVO);
		return result;
	}

	// 用于更新某个资源较完整的内容：PUT http://ip:port/shipping/lists
	@ApiOperation(notes = "更新[发货导入清单]信息", value = "更新[发货导入清单]信息", extensions = { @Extension(name = "auditLog", properties = { @ExtensionProperty(name = "opType", value = "UPDATE") }) })
	@RequestMapping(method = RequestMethod.PUT)
	public ShippingListVO editShippingList(@Validated @RequestBody ShippingListVO shippingListVO) {
		ShippingListDO shippingListDO = BeanMapper.map(shippingListVO, ShippingListDO.class);
		shippingListService.updateShippingList(shippingListDO);

		ShippingListDTO shippingListDTO = shippingListService.getShippingListById(shippingListDO.getId());
		shippingListVO = BeanMapper.map(shippingListDTO, ShippingListVO.class);

		log.info("edit$ShippingList ShippingListController edit result[{}]", shippingListVO);
		return shippingListVO;
	}

	// 用于资源的部分内容的更新：PUT http://ip:port/shipping/lists
	@ApiOperation(notes = "部分内容的更新[发货导入清单]信息", value = "部分内容的更新[发货导入清单]信息", extensions = { @Extension(name = "auditLog", properties = { @ExtensionProperty(name = "opType", value = "UPDATE") }) })
	@RequestMapping(method = RequestMethod.PATCH)
	public ShippingListVO updateShippingList(@RequestBody ShippingListVO shippingListVO) {
		ShippingListDO shippingListDO = BeanMapper.map(shippingListVO, ShippingListDO.class);
		shippingListService.updateShippingList(shippingListDO);

		ShippingListDTO shippingListDTO = shippingListService.getShippingListById(shippingListDO.getId());
		shippingListVO = BeanMapper.map(shippingListDTO, ShippingListVO.class);

		log.info("updateShippingList ShippingListController update result[{}]", shippingListVO);
		return shippingListVO;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/state",method = RequestMethod.GET)
	public ResultEntity<List<Map<String,Object>>> state(ShippingListQuery shippingListQuery){
		String selStartTime = null;
		String selEndTime = null;
		Date currentDate = new Date();
		if (StringUtils.isBlank(shippingListQuery.getSelStartTime())) {
			selStartTime = DateGeneralUtils.format(DateGeneralUtils.addDays(currentDate, -14), DateFormatConstants.yyyy_MM_dd);
			shippingListQuery.setSelStartTime(selStartTime);
		}

		if (StringUtils.isBlank(shippingListQuery.getSelEndTime())) {
			selEndTime = DateGeneralUtils.format(currentDate, DateFormatConstants.yyyy_MM_dd);
			shippingListQuery.setSelEndTime(selEndTime);
		}

		Map<String, String> dayMap = new HashMap<String, String>();
		for (int i = 0; i < 15; i++) {
			String day = DateGeneralUtils.format(DateGeneralUtils.addDays(currentDate, -i), DateFormatConstants.yyyy_MM_dd);
			dayMap.put(day, day);
		}
		
		ResultEntity<List<Map<String,Object>>> result = new ResultEntity<List<Map<String,Object>>>();
		Object obj = shippingListService.selectShippingList(shippingListQuery, null);
		List<ShippingListVO> list = (List<ShippingListVO>) obj;
		Map<String,List<ShippingListVO>> map = new HashMap<String,List<ShippingListVO>>();
		List<Map<String,Object>> resultList = new ArrayList<Map<String,Object>>();
		
		for(ShippingListVO vo:list) {
			String importTime= vo.getImportTime().substring(0,10);
			if(!map.containsKey(importTime)) {
				List<ShippingListVO> timeList = new ArrayList<ShippingListVO>();
				timeList.add(vo);
				map.put(importTime, timeList);
			}else {
				List<ShippingListVO> sList = map.get(importTime);
				Map<String, ShippingListVO> listMap = new HashMap<String, ShippingListVO>();
				for(ShippingListVO shipping:sList) {
					String key = shipping.getFactoryName() + shipping.getBrandName();
					listMap.put(key, shipping);
				}
				
				List<ShippingListVO> newList = listMap.values().stream().collect(Collectors.toList());
				for(ShippingListVO shipping:newList) {
					String key = vo.getFactoryName() + vo.getBrandName();
					if(!listMap.containsKey(key)) {
						sList.add(vo);
						break;
					}else {
						String key1 = shipping.getFactoryName() + shipping.getBrandName();
						if(key1.equals(key)) {
							Integer deviceNumber = vo.getDeviceNumber() + shipping.getDeviceNumber();
							shipping.setDeviceNumber(deviceNumber);
							break;
						}
					}
				}
			}
		}
		
        List<Object> re = map.keySet().stream().collect(Collectors.toList());
        Map<String, String> timeMap = new HashMap<String, String>();
        for(Object o:re) {
        	Map<String,Object> reMap = new HashMap<String,Object>();
        	String key = o.toString();
        	reMap.put("time", key);
        	reMap.put("list", map.get(key));
        	timeMap.put(key, key);
        	resultList.add(reMap);
        }
        
        List<String> dayList = dayMap.keySet().stream().collect(Collectors.toList());
		for (String day : dayList) {
			if (!timeMap.containsKey(day)) {
				Map<String, Object> reMap = new HashMap<String, Object>();
				reMap.put("time", day);
				reMap.put("list", null);
				resultList.add(reMap);
			}
		}
		
		result.setData(resultList);
		return result;
	}	


}