package cn.yunovo.iov.device.zen.biz.report.report.service.impl;

import java.util.List;
import java.util.Map;

import cn.yunovo.iov.boot.autoconfigure.auditlog.annotation.OpLog;
import cn.yunovo.iov.boot.autoconfigure.auditlog.enums.OpTypeEnum;
import cn.yunovo.iov.boot.autoconfigure.request.SearchCondition;
import cn.yunovo.iov.framework.commons.beanutils.bean.BeanMapper;
import cn.yunovo.iov.framework.web.PageInfo;
import cn.yunovo.iov.boot.autoconfigure.request.select.Condition;
import cn.yunovo.iov.device.zen.biz.report.report.manager.DeviceReportManager;
import cn.yunovo.iov.device.zen.biz.report.report.model.DeviceReportDO;
import cn.yunovo.iov.device.zen.biz.report.report.model.DeviceReportDTO;
import cn.yunovo.iov.device.zen.biz.report.report.model.DeviceReportQuery;
import cn.yunovo.iov.device.zen.biz.report.report.model.DeviceReportVO;
import cn.yunovo.iov.device.zen.biz.report.report.service.DeviceReportService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;

@Service
public class DeviceReportServiceImpl implements DeviceReportService {

	@Autowired
	private DeviceReportManager deviceReportManager;

	@Override
	public DeviceReportDTO getDeviceReportById(Integer id) {
		return deviceReportManager.getDeviceReportById(id);
	}
	
	@Override
	@OpLog( opType=OpTypeEnum.QUERY, opName = "根据条件查询[设备生产阶段，上报设备信息到云端]信息")
	public Object selectDeviceReport(DeviceReportQuery deviceReportQuery, Map<String, Condition> conditionMap) {
		Page<Object> page = null;

		// [分页查询]
		page = SearchCondition.conditionByPages(conditionMap);

		// [排序查询,不能使用OrderByHelper工具类,存在SQL中 limit 和 order by 顺序错误]
		// SearchCondition.conditionByOrder(conditionMap);

		// [分组查询],[偏移量查询]暂不实现支持查询

		// [执行数据库查询]
		List<DeviceReportDTO> deviceReportBOList = deviceReportManager.selectDeviceReport(deviceReportQuery, conditionMap);
		List<DeviceReportVO> deviceReportVOList = BeanMapper.mapList(deviceReportBOList, DeviceReportVO.class);

		// [limit 查询]
		if (SearchCondition.conditionBylimit(conditionMap)) {
			return deviceReportVOList;
		}

		// 返回分页
		if (null != page) {
			PageInfo<Page<Object>> pInfo = SearchCondition.returnPage(page);
			page.addAll(deviceReportVOList);
			return pInfo;
		}

		// 没符合条件查询
		return deviceReportVOList;
	}
	@Override
	public Integer insertDeviceReport(DeviceReportDO deviceReportDO) {
		return deviceReportManager.insertDeviceReport(deviceReportDO);
	}

	@Override
	public Integer deleteDeviceReportById(Integer id) {
		return deviceReportManager.deleteDeviceReportById(id);
	}

	@Override
	public Integer updateDeviceReport(DeviceReportDO deviceReportDO) {
		return deviceReportManager.updateDeviceReport(deviceReportDO);
	}
}
