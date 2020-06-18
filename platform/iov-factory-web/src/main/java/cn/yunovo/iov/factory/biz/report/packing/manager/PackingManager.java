package cn.yunovo.iov.factory.biz.report.packing.manager;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.yunovo.iov.framework.commons.beanutils.bean.BeanMapper;
import cn.yunovo.iov.boot.autoconfigure.request.select.Condition;
import cn.yunovo.iov.factory.biz.report.packing.mapper.PackingMapper;
import cn.yunovo.iov.factory.biz.report.packing.model.PackingDO;
import cn.yunovo.iov.factory.biz.report.packing.model.PackingDTO;
import cn.yunovo.iov.factory.biz.report.packing.model.PackingQuery;

/**
 * Manager层：通用业务处理层
 * @author huangzz
 *
 */
@Component
public class PackingManager {

	@Autowired
	private PackingMapper packingMapper;

	public PackingDTO getPackingById(Integer id) {
		PackingDO packingDO = packingMapper.getPackingById(id);
		PackingDTO PackingDTO = BeanMapper.map(packingDO, PackingDTO.class);
		return PackingDTO;
	}

	public List<PackingDTO> selectPacking(PackingQuery packingQuery, Map<String, Condition> conditionMap) {
		List<PackingDO> list = packingMapper.selectPacking(packingQuery, conditionMap);
		return BeanMapper.mapList(list, PackingDTO.class);
	}
	public Integer insertPacking(PackingDO packingDO) {
		return packingMapper.insertPacking(packingDO);
	}

	public Integer deletePackingById(Integer id) {
		return packingMapper.deletePackingById(id);
	}

	public Integer updatePacking(PackingDO packingDO) {
		return packingMapper.updatePacking(packingDO);
	}
}