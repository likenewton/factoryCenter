package cn.yunovo.iov.factory.biz.test.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.yunovo.iov.boot.autoconfigure.auditlog.annotation.OpLog;
import cn.yunovo.iov.boot.autoconfigure.auditlog.enums.OpTypeEnum;
import cn.yunovo.iov.boot.autoconfigure.request.SearchCondition;
import cn.yunovo.iov.framework.commons.beanutils.bean.BeanMapper;
import cn.yunovo.iov.framework.web.PageInfo;
import lombok.extern.slf4j.Slf4j;
import cn.yunovo.iov.boot.autoconfigure.request.select.Condition;
import cn.yunovo.iov.factory.biz.VendorService;
import cn.yunovo.iov.factory.biz.device.manager.DeviceReportManager;
import cn.yunovo.iov.factory.biz.device.model.Device;
import cn.yunovo.iov.factory.biz.device.model.Device.DeviceRelInfo;
import cn.yunovo.iov.factory.biz.statistics.model.StatisticsTypeDO;
import cn.yunovo.iov.factory.biz.statistics.service.StatisticsTypeService;
import cn.yunovo.iov.factory.biz.test.manager.DeviceTestManager;
import cn.yunovo.iov.factory.biz.test.model.DeviceTestDO;
import cn.yunovo.iov.factory.biz.test.model.DeviceTestDTO;
import cn.yunovo.iov.factory.biz.test.model.DeviceTestItemDO;
import cn.yunovo.iov.factory.biz.test.model.DeviceTestItemVO;
import cn.yunovo.iov.factory.biz.test.model.DeviceTestQuery;
import cn.yunovo.iov.factory.biz.test.model.DeviceTestVO;
import cn.yunovo.iov.factory.biz.test.service.DeviceTestItemService;
import cn.yunovo.iov.factory.biz.test.service.DeviceTestService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;

@Service
@Slf4j
public class DeviceTestServiceImpl implements DeviceTestService {

	@Autowired
	private DeviceTestManager deviceTestManager;
	
	@Autowired
	private DeviceTestItemService deviceTestItemService;

	@Autowired
	private StatisticsTypeService statisticsTypeService;
	
	@Autowired
	private DeviceReportManager deviceReportManager;

	@Override
	public DeviceTestDTO getDeviceTestById(Integer id) {
		return deviceTestManager.getDeviceTestById(id);
	}
	
	@Override
	@OpLog( opType=OpTypeEnum.QUERY, opName = "根据条件查询[工厂设备测试]信息")
	public Object selectDeviceTest(DeviceTestQuery deviceTestQuery, Map<String, Condition> conditionMap) {
		Page<Object> page = null;

		// [分页查询]
		page = SearchCondition.conditionByPages(conditionMap);

		// [排序查询,不能使用OrderByHelper工具类,存在SQL中 limit 和 order by 顺序错误]
		// SearchCondition.conditionByOrder(conditionMap);

		// [分组查询],[偏移量查询]暂不实现支持查询

		// [执行数据库查询]
		List<DeviceTestDTO> deviceTestBOList = deviceTestManager.selectDeviceTest(deviceTestQuery, conditionMap);
		List<DeviceTestVO> deviceTestVOList = BeanMapper.mapList(deviceTestBOList, DeviceTestVO.class);

		// [limit 查询]
		if (SearchCondition.conditionBylimit(conditionMap)) {
			return deviceTestVOList;
		}

		// 返回分页
		if (null != page) {
			PageInfo<Page<Object>> pInfo = SearchCondition.returnPage(page);
			page.addAll(deviceTestVOList);
			return pInfo;
		}

		// 没符合条件查询
		return deviceTestVOList;
	}
	@Override
	public Integer insertDeviceTest(DeviceTestDO deviceTestDO) {
		return deviceTestManager.insertDeviceTest(deviceTestDO);
	}

	@Override
	public Integer deleteDeviceTestById(Integer id) {
		return deviceTestManager.deleteDeviceTestById(id);
	}

	@Override
	public Integer updateDeviceTest(DeviceTestDO deviceTestDO) {
		return deviceTestManager.updateDeviceTest(deviceTestDO);
	}
	
	@Async
	@Override
	public void saveDevice(DeviceTestVO deviceTestVO) {
		int errorNumber = 0;
		int succNumber = 0;
		if (null != deviceTestVO.getTestItems() && 0 < deviceTestVO.getTestItems().size()) {
			List<DeviceTestItemVO> testItems = deviceTestVO.getTestItems();
			for (DeviceTestItemVO testItem : testItems) {
				if (1 == testItem.getTestResult()) {
					errorNumber++;
				} else {
					succNumber++;
				}
			}
		}

		DeviceTestDO deviceTestDO = BeanMapper.map(deviceTestVO, DeviceTestDO.class);
		deviceTestDO.setErrorNumber(errorNumber);
		deviceTestDO.setSuccNumber(succNumber);
		insertDeviceTest(deviceTestDO);
		StatisticsTypeDO statisticsTypeDO = new StatisticsTypeDO();
		statisticsTypeDO.setCreateTime(new Date());
		statisticsTypeDO.setStatisticsType(deviceTestVO.getProductionPhase());
		statisticsTypeDO.setFactoryName(deviceTestVO.getFactoryName());
		statisticsTypeDO.setOrgCode(deviceTestVO.getOrgCode());
		statisticsTypeService.insertStatisticsType(statisticsTypeDO);

		if (null != deviceTestVO.getTestItems() && 0 < deviceTestVO.getTestItems().size()) {
			List<DeviceTestItemVO> testItems = deviceTestVO.getTestItems();
			List<DeviceTestItemDO> testItemsDO = new ArrayList<DeviceTestItemDO>();
			for (DeviceTestItemVO testItem : testItems) {
				testItem.setTestId(deviceTestDO.getId());
				DeviceTestItemDO deviceTestItemDO = BeanMapper.map(testItem, DeviceTestItemDO.class);
				testItemsDO.add(deviceTestItemDO);
			}
			deviceTestItemService.batchInsertDeviceTestItem(testItemsDO);
			deviceTestVO.setTestItems(testItems);
		}

		// 更新设备信息
		Device device = new Device();
		if (StringUtils.isNotBlank(deviceTestVO.getSn()) && 0 == errorNumber && 0 < succNumber) {
			device.setDeviceSn(deviceTestVO.getSn());
			device.setDeviceIccid(deviceTestVO.getIccid());
			device.setDeviceImei(deviceTestVO.getImei());
			device.setSoftVersion(deviceTestVO.getRomVersion());
			device.setDeviceType(Integer.valueOf(deviceTestVO.getDtype()));
			device.setProName(deviceTestVO.getProName());
			
			DeviceRelInfo deviceRelInfo = new DeviceRelInfo();
			deviceRelInfo.setMcuVersion(deviceTestVO.getMcuVersion());
			deviceRelInfo.setPrjname(deviceTestVO.getPrjName());
			deviceRelInfo.setProName(deviceTestVO.getProName());
			
			device.setDeviceRelInfo(deviceRelInfo);

			String reslut = VendorService.updateDevice(device);
			if (1 == deviceTestVO.getProductionPhase()) {
				log.info("贴片更新设备:{}", device);
				deviceReportManager.saveServiceLog(deviceTestVO.getIccid(), 3, "tester", reslut);

			} else if (2 == deviceTestVO.getProductionPhase()) {
				log.info("组装更新设备:{}", device);
				deviceReportManager.saveServiceLog(deviceTestVO.getIccid(), 4, "tester", reslut);
			}
		}
	}
}
