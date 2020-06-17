package cn.yunovo.iov.factory.biz.report.packing.service.impl;

import java.util.List;
import java.util.Map;

import cn.yunovo.iov.boot.autoconfigure.auditlog.annotation.OpLog;
import cn.yunovo.iov.boot.autoconfigure.auditlog.enums.OpTypeEnum;
import cn.yunovo.iov.boot.autoconfigure.request.SearchCondition;
import cn.yunovo.iov.framework.commons.beanutils.bean.BeanMapper;
import cn.yunovo.iov.framework.web.PageInfo;
import cn.yunovo.iov.boot.autoconfigure.request.select.Condition;
import cn.yunovo.iov.factory.biz.report.packing.manager.PackingManager;
import cn.yunovo.iov.factory.biz.report.packing.model.PackingDO;
import cn.yunovo.iov.factory.biz.report.packing.model.PackingDTO;
import cn.yunovo.iov.factory.biz.report.packing.model.PackingQuery;
import cn.yunovo.iov.factory.biz.report.packing.model.PackingVO;
import cn.yunovo.iov.factory.biz.report.packing.service.PackingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;

@Service
public class PackingServiceImpl implements PackingService {

	@Autowired
	private PackingManager packingManager;

	@Override
	public PackingDTO getPackingById(Integer id) {
		return packingManager.getPackingById(id);
	}
	
	@Override
	@OpLog( opType=OpTypeEnum.QUERY, opName = "根据条件查询[设备生产阶段，上报组装信息到云端]信息")
	public Object selectPacking(PackingQuery packingQuery, Map<String, Condition> conditionMap) {
		Page<Object> page = null;

		// [分页查询]
		page = SearchCondition.conditionByPages(conditionMap);

		// [排序查询,不能使用OrderByHelper工具类,存在SQL中 limit 和 order by 顺序错误]
		// SearchCondition.conditionByOrder(conditionMap);

		// [分组查询],[偏移量查询]暂不实现支持查询

		// [执行数据库查询]
		List<PackingDTO> packingBOList = packingManager.selectPacking(packingQuery, conditionMap);
		List<PackingVO> packingVOList = BeanMapper.mapList(packingBOList, PackingVO.class);

		// [limit 查询]
		if (SearchCondition.conditionBylimit(conditionMap)) {
			return packingVOList;
		}

		// 返回分页
		if (null != page) {
			PageInfo<Page<Object>> pInfo = SearchCondition.returnPage(page);
			page.addAll(packingVOList);
			return pInfo;
		}

		// 没符合条件查询
		return packingVOList;
	}
	@Override
	public Integer insertPacking(PackingDO packingDO) {
		return packingManager.insertPacking(packingDO);
	}

	@Override
	public Integer deletePackingById(Integer id) {
		return packingManager.deletePackingById(id);
	}

	@Override
	public Integer updatePacking(PackingDO packingDO) {
		return packingManager.updatePacking(packingDO);
	}
}
