package cn.yunovo.iov.zen.biz.test.service.impl;

import java.util.List;
import java.util.Map;

import cn.yunovo.iov.boot.autoconfigure.auditlog.annotation.OpLog;
import cn.yunovo.iov.boot.autoconfigure.auditlog.enums.OpTypeEnum;
import cn.yunovo.iov.boot.autoconfigure.request.SearchCondition;
import cn.yunovo.iov.framework.commons.beanutils.bean.BeanMapper;
import cn.yunovo.iov.framework.web.PageInfo;
import cn.yunovo.iov.zen.biz.test.manager.DeviceTestManager;
import cn.yunovo.iov.zen.biz.test.model.DeviceTestDO;
import cn.yunovo.iov.zen.biz.test.model.DeviceTestDTO;
import cn.yunovo.iov.zen.biz.test.model.DeviceTestQuery;
import cn.yunovo.iov.zen.biz.test.model.DeviceTestVO;
import cn.yunovo.iov.zen.biz.test.service.DeviceTestService;
import cn.yunovo.iov.boot.autoconfigure.request.select.Condition;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;

@Service
public class DeviceTestServiceImpl implements DeviceTestService {

	@Autowired
	private DeviceTestManager deviceTestManager;

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
}
