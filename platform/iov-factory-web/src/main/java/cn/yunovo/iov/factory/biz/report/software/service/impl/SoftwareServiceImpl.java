package cn.yunovo.iov.factory.biz.report.software.service.impl;

import java.util.List;
import java.util.Map;

import cn.yunovo.iov.boot.autoconfigure.auditlog.annotation.OpLog;
import cn.yunovo.iov.boot.autoconfigure.auditlog.enums.OpTypeEnum;
import cn.yunovo.iov.boot.autoconfigure.request.SearchCondition;
import cn.yunovo.iov.framework.commons.beanutils.bean.BeanMapper;
import cn.yunovo.iov.framework.web.PageInfo;
import cn.yunovo.iov.boot.autoconfigure.request.select.Condition;
import cn.yunovo.iov.factory.biz.report.software.manager.SoftwareManager;
import cn.yunovo.iov.factory.biz.report.software.model.SoftwareDO;
import cn.yunovo.iov.factory.biz.report.software.model.SoftwareDTO;
import cn.yunovo.iov.factory.biz.report.software.model.SoftwareQuery;
import cn.yunovo.iov.factory.biz.report.software.model.SoftwareVO;
import cn.yunovo.iov.factory.biz.report.software.service.SoftwareService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;

@Service
public class SoftwareServiceImpl implements SoftwareService {

	@Autowired
	private SoftwareManager softwareManager;

	@Override
	public SoftwareDTO getSoftwareById(Integer id) {
		return softwareManager.getSoftwareById(id);
	}
	
	@Override
	@OpLog( opType=OpTypeEnum.QUERY, opName = "根据条件查询[zen 平台，上报设备信息到云端]信息")
	public Object selectSoftware(SoftwareQuery softwareQuery, Map<String, Condition> conditionMap) {
		Page<Object> page = null;

		// [分页查询]
		page = SearchCondition.conditionByPages(conditionMap);

		// [排序查询,不能使用OrderByHelper工具类,存在SQL中 limit 和 order by 顺序错误]
		// SearchCondition.conditionByOrder(conditionMap);

		// [分组查询],[偏移量查询]暂不实现支持查询

		// [执行数据库查询]
		List<SoftwareDTO> softwareBOList = softwareManager.selectSoftware(softwareQuery, conditionMap);
		List<SoftwareVO> softwareVOList = BeanMapper.mapList(softwareBOList, SoftwareVO.class);

		// [limit 查询]
		if (SearchCondition.conditionBylimit(conditionMap)) {
			return softwareVOList;
		}

		// 返回分页
		if (null != page) {
			PageInfo<Page<Object>> pInfo = SearchCondition.returnPage(page);
			page.addAll(softwareVOList);
			return pInfo;
		}

		// 没符合条件查询
		return softwareVOList;
	}
	@Override
	public Integer insertSoftware(SoftwareDO softwareDO) {
		return softwareManager.insertSoftware(softwareDO);
	}

	@Override
	public Integer deleteSoftwareById(Integer id) {
		return softwareManager.deleteSoftwareById(id);
	}

	@Override
	public Integer updateSoftware(SoftwareDO softwareDO) {
		return softwareManager.updateSoftware(softwareDO);
	}
}
