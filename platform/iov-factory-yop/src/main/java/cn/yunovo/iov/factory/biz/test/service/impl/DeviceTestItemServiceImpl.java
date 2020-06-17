package cn.yunovo.iov.factory.biz.test.service.impl;

import java.util.List;
import java.util.Map;

import cn.yunovo.iov.boot.autoconfigure.auditlog.annotation.OpLog;
import cn.yunovo.iov.boot.autoconfigure.auditlog.enums.OpTypeEnum;
import cn.yunovo.iov.boot.autoconfigure.request.SearchCondition;
import cn.yunovo.iov.framework.commons.beanutils.bean.BeanMapper;
import cn.yunovo.iov.framework.web.PageInfo;
import cn.yunovo.iov.boot.autoconfigure.request.select.Condition;
import cn.yunovo.iov.factory.biz.test.manager.DeviceTestItemManager;
import cn.yunovo.iov.factory.biz.test.model.DeviceTestItemDO;
import cn.yunovo.iov.factory.biz.test.model.DeviceTestItemDTO;
import cn.yunovo.iov.factory.biz.test.model.DeviceTestItemQuery;
import cn.yunovo.iov.factory.biz.test.model.DeviceTestItemVO;
import cn.yunovo.iov.factory.biz.test.service.DeviceTestItemService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;

@Service
public class DeviceTestItemServiceImpl implements DeviceTestItemService {

	@Autowired
	private DeviceTestItemManager deviceTestItemManager;

	@Override
	public DeviceTestItemDTO getDeviceTestItemById(Integer id) {
		return deviceTestItemManager.getDeviceTestItemById(id);
	}
	
	@Override
	@OpLog( opType=OpTypeEnum.QUERY, opName = "根据条件查询[设备测试项]信息")
	public Object selectDeviceTestItem(DeviceTestItemQuery deviceTestItemQuery, Map<String, Condition> conditionMap) {
		Page<Object> page = null;

		// [分页查询]
		page = SearchCondition.conditionByPages(conditionMap);

		// [排序查询,不能使用OrderByHelper工具类,存在SQL中 limit 和 order by 顺序错误]
		// SearchCondition.conditionByOrder(conditionMap);

		// [分组查询],[偏移量查询]暂不实现支持查询

		// [执行数据库查询]
		List<DeviceTestItemDTO> deviceTestItemBOList = deviceTestItemManager.selectDeviceTestItem(deviceTestItemQuery, conditionMap);
		List<DeviceTestItemVO> deviceTestItemVOList = BeanMapper.mapList(deviceTestItemBOList, DeviceTestItemVO.class);

		// [limit 查询]
		if (SearchCondition.conditionBylimit(conditionMap)) {
			return deviceTestItemVOList;
		}

		// 返回分页
		if (null != page) {
			PageInfo<Page<Object>> pInfo = SearchCondition.returnPage(page);
			page.addAll(deviceTestItemVOList);
			return pInfo;
		}

		// 没符合条件查询
		return deviceTestItemVOList;
	}
	@Override
	public Integer insertDeviceTestItem(DeviceTestItemDO deviceTestItemDO) {
		return deviceTestItemManager.insertDeviceTestItem(deviceTestItemDO);
	}

	@Override
	public Integer deleteDeviceTestItemById(Integer id) {
		return deviceTestItemManager.deleteDeviceTestItemById(id);
	}

	@Override
	public Integer updateDeviceTestItem(DeviceTestItemDO deviceTestItemDO) {
		return deviceTestItemManager.updateDeviceTestItem(deviceTestItemDO);
	}

	@Override
	public Integer batchInsertDeviceTestItem(List<DeviceTestItemDO> list) {
		return deviceTestItemManager.batchInsertDeviceTestItem(list);
	}
}
