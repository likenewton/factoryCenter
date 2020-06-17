package cn.yunovo.iov.device.zen.biz.statistics.repetition.service.impl;

import java.util.List;
import java.util.Map;

import cn.yunovo.iov.boot.autoconfigure.auditlog.annotation.OpLog;
import cn.yunovo.iov.boot.autoconfigure.auditlog.enums.OpTypeEnum;
import cn.yunovo.iov.boot.autoconfigure.request.SearchCondition;
import cn.yunovo.iov.framework.commons.beanutils.bean.BeanMapper;
import cn.yunovo.iov.framework.web.PageInfo;
import cn.yunovo.iov.boot.autoconfigure.request.select.Condition;
import cn.yunovo.iov.device.zen.biz.statistics.repetition.manager.RepetitionReportManager;
import cn.yunovo.iov.device.zen.biz.statistics.repetition.model.RepetitionReportVO;
import cn.yunovo.iov.device.zen.biz.statistics.repetition.model.RepetitionReportDO;
import cn.yunovo.iov.device.zen.biz.statistics.repetition.model.RepetitionReportDTO;
import cn.yunovo.iov.device.zen.biz.statistics.repetition.model.RepetitionReportQuery;
import cn.yunovo.iov.device.zen.biz.statistics.repetition.service.RepetitionReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;

@Service
public class RepetitionReportServiceImpl implements RepetitionReportService {

	@Autowired
	private RepetitionReportManager repetitionReportManager;

	@Override
	public RepetitionReportDTO getRepetitionReportById(Integer id) {
		return repetitionReportManager.getRepetitionReportById(id);
	}
	
	@Override
	@OpLog( opType=OpTypeEnum.QUERY, opName = "根据条件查询[]信息")
	public Object selectRepetitionReport(RepetitionReportQuery repetitionReportQuery, Map<String, Condition> conditionMap) {
		Page<Object> page = null;

		// [分页查询]
		page = SearchCondition.conditionByPages(conditionMap);

		// [排序查询,不能使用OrderByHelper工具类,存在SQL中 limit 和 order by 顺序错误]
		// SearchCondition.conditionByOrder(conditionMap);

		// [分组查询],[偏移量查询]暂不实现支持查询

		// [执行数据库查询]
		List<RepetitionReportDTO> repetitionReportBOList = repetitionReportManager.selectRepetitionReport(repetitionReportQuery, conditionMap);
		List<RepetitionReportVO> repetitionReportVOList = BeanMapper.mapList(repetitionReportBOList, RepetitionReportVO.class);

		// [limit 查询]
		if (SearchCondition.conditionBylimit(conditionMap)) {
			return repetitionReportVOList;
		}

		// 返回分页
		if (null != page) {
			PageInfo<Page<Object>> pInfo = SearchCondition.returnPage(page);
			page.addAll(repetitionReportVOList);
			return pInfo;
		}

		// 没符合条件查询
		return repetitionReportVOList;
	}
	@Override
	public Integer insertRepetitionReport(RepetitionReportDO repetitionReportDO) {
		return repetitionReportManager.insertRepetitionReport(repetitionReportDO);
	}

	@Override
	public Integer deleteRepetitionReportById(Integer id) {
		return repetitionReportManager.deleteRepetitionReportById(id);
	}

	@Override
	public Integer updateRepetitionReport(RepetitionReportDO repetitionReportDO) {
		return repetitionReportManager.updateRepetitionReport(repetitionReportDO);
	}

	@Override
	public void stateRepetitionReport(List<RepetitionReportDTO> iccidList, List<RepetitionReportDTO> snList, List<RepetitionReportDTO> imeiList) {
		repetitionReportManager.stateRepetitionReport(iccidList);
		repetitionReportManager.stateRepetitionReport(snList);
		repetitionReportManager.stateRepetitionReport(imeiList);
	}
}
