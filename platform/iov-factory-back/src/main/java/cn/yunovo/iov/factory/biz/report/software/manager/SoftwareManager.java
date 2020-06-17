package cn.yunovo.iov.factory.biz.report.software.manager;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.yunovo.iov.framework.commons.beanutils.bean.BeanMapper;
import cn.yunovo.iov.boot.autoconfigure.request.select.Condition;
import cn.yunovo.iov.factory.biz.report.software.mapper.SoftwareMapper;
import cn.yunovo.iov.factory.biz.report.software.model.SoftwareDO;
import cn.yunovo.iov.factory.biz.report.software.model.SoftwareDTO;
import cn.yunovo.iov.factory.biz.report.software.model.SoftwareQuery;

/**
 * Manager层：通用业务处理层
 * @author huangzz
 *
 */
@Component
public class SoftwareManager {

	@Autowired
	private SoftwareMapper softwareMapper;

	public SoftwareDTO getSoftwareById(Integer id) {
		SoftwareDO softwareDO = softwareMapper.getSoftwareById(id);
		SoftwareDTO SoftwareDTO = BeanMapper.map(softwareDO, SoftwareDTO.class);
		return SoftwareDTO;
	}

	public List<SoftwareDTO> selectSoftware(SoftwareQuery softwareQuery, Map<String, Condition> conditionMap) {
		List<SoftwareDO> list = softwareMapper.selectSoftware(softwareQuery, conditionMap);
		return BeanMapper.mapList(list, SoftwareDTO.class);
	}
	public Integer insertSoftware(SoftwareDO softwareDO) {
		return softwareMapper.insertSoftware(softwareDO);
	}

	public Integer deleteSoftwareById(Integer id) {
		return softwareMapper.deleteSoftwareById(id);
	}

	public Integer updateSoftware(SoftwareDO softwareDO) {
		return softwareMapper.updateSoftware(softwareDO);
	}
}