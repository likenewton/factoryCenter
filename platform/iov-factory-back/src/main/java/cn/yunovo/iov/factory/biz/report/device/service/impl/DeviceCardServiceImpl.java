package cn.yunovo.iov.factory.biz.report.device.service.impl;

import java.util.List;
import java.util.Map;

import cn.yunovo.iov.boot.autoconfigure.auditlog.annotation.OpLog;
import cn.yunovo.iov.boot.autoconfigure.auditlog.enums.OpTypeEnum;
import cn.yunovo.iov.boot.autoconfigure.request.SearchCondition;
import cn.yunovo.iov.framework.commons.beanutils.bean.BeanMapper;
import cn.yunovo.iov.framework.web.PageInfo;
import cn.yunovo.iov.boot.autoconfigure.request.select.Condition;
import cn.yunovo.iov.factory.biz.report.device.manager.DeviceCardManager;
import cn.yunovo.iov.factory.biz.report.device.model.DeviceCardDO;
import cn.yunovo.iov.factory.biz.report.device.model.DeviceCardDTO;
import cn.yunovo.iov.factory.biz.report.device.model.DeviceCardQuery;
import cn.yunovo.iov.factory.biz.report.device.model.DeviceCardVO;
import cn.yunovo.iov.factory.biz.report.device.service.DeviceCardService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;

@Service
public class DeviceCardServiceImpl implements DeviceCardService {

	@Autowired
	private DeviceCardManager deviceCardManager;

	@Override
	public DeviceCardDTO getDeviceCardById(Integer id) {
		return deviceCardManager.getDeviceCardById(id);
	}
	
	@Override
	@OpLog( opType=OpTypeEnum.QUERY, opName = "根据条件查询[三码关系表]信息")
	public Object selectDeviceCard(DeviceCardQuery deviceCardQuery, Map<String, Condition> conditionMap) {
		Page<Object> page = null;

		// [分页查询]
		page = SearchCondition.conditionByPages(conditionMap);

		// [排序查询,不能使用OrderByHelper工具类,存在SQL中 limit 和 order by 顺序错误]
		// SearchCondition.conditionByOrder(conditionMap);

		// [分组查询],[偏移量查询]暂不实现支持查询

		// [执行数据库查询]
		List<DeviceCardDTO> deviceCardBOList = deviceCardManager.selectDeviceCard(deviceCardQuery, conditionMap);
		List<DeviceCardVO> deviceCardVOList = BeanMapper.mapList(deviceCardBOList, DeviceCardVO.class);

		// [limit 查询]
		if (SearchCondition.conditionBylimit(conditionMap)) {
			return deviceCardVOList;
		}

		// 返回分页
		if (null != page) {
			PageInfo<Page<Object>> pInfo = SearchCondition.returnPage(page);
			page.addAll(deviceCardVOList);
			return pInfo;
		}

		// 没符合条件查询
		return deviceCardVOList;
	}
	@Override
	public Integer insertDeviceCard(DeviceCardDO deviceCardDO) {
		return deviceCardManager.insertDeviceCard(deviceCardDO);
	}

	@Override
	public Integer deleteDeviceCardById(Integer id) {
		return deviceCardManager.deleteDeviceCardById(id);
	}

	@Override
	public Integer updateDeviceCard(DeviceCardDO deviceCardDO) {
		return deviceCardManager.updateDeviceCard(deviceCardDO);
	}
}
