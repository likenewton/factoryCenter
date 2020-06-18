package cn.yunovo.iov.factory.biz.other.log.service.impl;

import java.util.List;
import java.util.Map;

import cn.yunovo.iov.boot.autoconfigure.auditlog.annotation.OpLog;
import cn.yunovo.iov.boot.autoconfigure.auditlog.enums.OpTypeEnum;
import cn.yunovo.iov.boot.autoconfigure.request.SearchCondition;
import cn.yunovo.iov.framework.commons.beanutils.bean.BeanMapper;
import cn.yunovo.iov.framework.web.PageInfo;
import cn.yunovo.iov.boot.autoconfigure.request.select.Condition;
import cn.yunovo.iov.factory.biz.other.log.manager.DeviceManager;
import cn.yunovo.iov.factory.biz.other.log.model.DeviceDO;
import cn.yunovo.iov.factory.biz.other.log.model.DeviceDTO;
import cn.yunovo.iov.factory.biz.other.log.model.DeviceQuery;
import cn.yunovo.iov.factory.biz.other.log.model.DeviceVO;
import cn.yunovo.iov.factory.biz.other.log.service.DeviceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;

@Service
public class DeviceServiceImpl implements DeviceService {

	@Autowired
	private DeviceManager deviceManager;

	@Override
	public DeviceDTO getDeviceById(Integer id) {
		return deviceManager.getDeviceById(id);
	}
	
	@Override
	@OpLog( opType=OpTypeEnum.QUERY, opName = "根据条件查询[]信息")
	public Object selectDevice(DeviceQuery deviceQuery, Map<String, Condition> conditionMap) {
		Page<Object> page = null;

		// [分页查询]
		page = SearchCondition.conditionByPages(conditionMap);

		// [排序查询,不能使用OrderByHelper工具类,存在SQL中 limit 和 order by 顺序错误]
		// SearchCondition.conditionByOrder(conditionMap);

		// [分组查询],[偏移量查询]暂不实现支持查询

		// [执行数据库查询]
		List<DeviceDTO> deviceBOList = deviceManager.selectDevice(deviceQuery, conditionMap);
		List<DeviceVO> deviceVOList = BeanMapper.mapList(deviceBOList, DeviceVO.class);

		// [limit 查询]
		if (SearchCondition.conditionBylimit(conditionMap)) {
			return deviceVOList;
		}

		// 返回分页
		if (null != page) {
			PageInfo<Page<Object>> pInfo = SearchCondition.returnPage(page);
			page.addAll(deviceVOList);
			return pInfo;
		}

		// 没符合条件查询
		return deviceVOList;
	}
	@Override
	public Integer insertDevice(DeviceDO deviceDO) {
		return deviceManager.insertDevice(deviceDO);
	}

	@Override
	public Integer deleteDeviceById(Integer id) {
		return deviceManager.deleteDeviceById(id);
	}

	@Override
	public Integer updateDevice(DeviceDO deviceDO) {
		return deviceManager.updateDevice(deviceDO);
	}
}
