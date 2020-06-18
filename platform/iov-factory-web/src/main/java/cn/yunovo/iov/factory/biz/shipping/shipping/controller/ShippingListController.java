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
import cn.yunovo.iov.boot.autoconfigure.request.select.Condition;
import cn.yunovo.iov.boot.autoconfigure.request.select.Group;
import cn.yunovo.iov.boot.autoconfigure.request.select.Limit;
import cn.yunovo.iov.boot.autoconfigure.request.select.Offset;
import cn.yunovo.iov.boot.autoconfigure.request.select.Order;
import cn.yunovo.iov.boot.autoconfigure.request.select.Pages;
import cn.yunovo.iov.cas.client.authentication.H5ClientAuthenticationFilter;
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
import cn.yunovo.iov.factory.biz.statistics.shipping.model.StatisticsShippingListDO;
import cn.yunovo.iov.factory.biz.statistics.shipping.model.StatisticsShippingListQuery;
import cn.yunovo.iov.factory.biz.statistics.shipping.model.StatisticsShippingListVO;
import cn.yunovo.iov.factory.biz.statistics.shipping.model.StatisticsShippingQuery;
import cn.yunovo.iov.factory.biz.statistics.shipping.model.StatisticsShippingVO;
import cn.yunovo.iov.factory.biz.statistics.shipping.service.StatisticsShippingListService;
import cn.yunovo.iov.factory.biz.statistics.shipping.service.StatisticsShippingService;
import cn.yunovo.iov.factory.biz.statistics.type.model.StatisticsTypeDO;
import cn.yunovo.iov.factory.biz.statistics.type.service.StatisticsTypeService;
import cn.yunovo.iov.factory.framework.Contants;
import cn.yunovo.iov.factory.framework.LoginInfoUtil;
import cn.yunovo.iov.factory.framework.dac.DacHelper;
import cn.yunovo.iov.factory.framework.dac.DacResourceHelper;
import cn.yunovo.iov.factory.framework.dac.bean.LoginUser;
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
	private StatisticsTypeService statisticsTypeService;

	@Autowired
	private StatisticsShippingListService statisticsShippingListService;

	@Autowired
	private DictionaryService dictionaryService;

	private static Map<String, String> dictYearMap;

	private static Map<String, String> dictMonthMap;

	private static Map<String, String> suffixFile = new HashMap<String, String>();

	static {
		suffixFile.put("xls", "xls");
		suffixFile.put("xlsx", "xlsx");
	}

	@SuppressWarnings("unchecked")
	private void statistics(ShippingListVO shippingListVO, String opt, HttpServletRequest request, ChannelDTO channelDTO) {

		// 统计发货数据
		StatisticsShippingQuery statisticsShippingQuery = new StatisticsShippingQuery();
		statisticsShippingQuery.setArea(shippingListVO.getArea());
		statisticsShippingQuery.setBrandName(shippingListVO.getBrandName());
		statisticsShippingQuery.setFactoryName(shippingListVO.getFactoryName());
		StatisticsShippingDTO statisticsShippingDTO = statisticsShippingService.statisticsShipping(statisticsShippingQuery);
		List<StatisticsShippingVO> list = (List<StatisticsShippingVO>) statisticsShippingService.selectStatisticsShipping(statisticsShippingQuery, null);

		StatisticsShippingDO statisticsShippingDO = BeanMapper.map(statisticsShippingDTO, StatisticsShippingDO.class);

		if (null != list && 0 < list.size()) {

			StatisticsShippingVO statisticsShippingVO = list.get(0);

			// 如何发货设备数量为0时，删除统计数据
			if (null == statisticsShippingDTO && "del".equals(opt)) {
				statisticsShippingService.deleteStatisticsShippingById(statisticsShippingVO.getId());
			} else {
				statisticsShippingDO.setUpdateTime(new Date());
				if (null == opt && !"del".equals(opt)) {
					statisticsShippingDO.setLastImporttime(new Date());
				}
				statisticsShippingDO.setId(statisticsShippingVO.getId());
				statisticsShippingService.updateStatisticsShipping(statisticsShippingDO);
			}
		} else {
			statisticsShippingDO.setUpdateTime(new Date());
			statisticsShippingDO.setLastImporttime(new Date());
			statisticsShippingDO.setChannelId(shippingListVO.getChannelId());
			statisticsShippingService.insertStatisticsShipping(statisticsShippingDO);

			// 插入数据权限
			LoginUser loginUser = LoginInfoUtil.LOGINUSER_LOCAL.get();
			if(3 != loginUser.getUserType()) {
				DacResourceHelper.insertChannelResource(Contants.TABLE_STATISTICS_SHIPPING, statisticsShippingDO.getId(), channelDTO.getPhone(), LoginInfoUtil.getLoginBaseInfo(request).getLoginName());
			}
			if(1 != loginUser.getUserType()) {
				DacResourceHelper.insertBrandResource(Contants.TABLE_STATISTICS_SHIPPING, statisticsShippingDO.getId(), channelDTO.getBrandName(), LoginInfoUtil.getLoginBaseInfo(request).getLoginName());
			}

		}

		// 插入到统计任务表
		StatisticsTypeDO statisticsTypeDO = new StatisticsTypeDO();
		statisticsTypeDO.setCreateTime(new Date());
		statisticsTypeDO.setStatisticsType(3);
		statisticsTypeDO.setFactoryName(shippingListVO.getFactoryName());
		statisticsTypeDO.setOrgCode(shippingListVO.getBrandName());
		statisticsTypeDO.setChannelId(shippingListVO.getChannelId());
		statisticsTypeService.insertStatisticsType(statisticsTypeDO);
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
		result.setData(shippingListService.selectShippingList(shippingListQuery, conditionMap));
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
		statistics(shippingListVO, "del", request, null);

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

		// 获取云智码解析组装厂
		String factoryName = "";
		if (StringUtils.isNoneBlank(shippingListVO.getYunovoCode()) && 3 <= shippingListVO.getYunovoCode().length()) {
			factoryName = String.valueOf(shippingListVO.getYunovoCode().charAt(2));
		}

		// 获取云智码解析组装厂
		String productDate = null;
		if ((StringUtils.isBlank(shippingListVO.getProductDate()) || "null".equals(shippingListVO.getProductDate())) && StringUtils.isNoneBlank(shippingListVO.getYunovoCode()) && 16 <= shippingListVO.getYunovoCode().length()) {

			if (null == dictYearMap) {
				DictionaryQuery dictionaryQuery = new DictionaryQuery();
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
		statistics(shippingListVO, null, request, channelDTO);
		result.setData(listDevice.size());

		// 插入数据权限
		LoginUser loginUser = LoginInfoUtil.LOGINUSER_LOCAL.get();
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

}