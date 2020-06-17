package cn.yunovo.iov.zen.biz.packing.manager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.yunovo.iov.framework.commons.beanutils.bean.BeanMapper;
import cn.yunovo.iov.zen.biz.packing.mapper.PackingReportMapper;
import cn.yunovo.iov.zen.biz.packing.model.PackingReportDO;
import cn.yunovo.iov.zen.biz.packing.model.PackingReportDTO;
import cn.yunovo.iov.zen.biz.packing.model.PackingReportQuery;

/**
 * Manager层：通用业务处理层
 * 
 * @author huangzz
 *
 */
@Component
public class PackingReportManager {

	@Autowired
	private PackingReportMapper packingReportMapper;

	public Integer saveOrInsertDeviceReport(PackingReportDO packingReportDO) {
		return packingReportMapper.saveOrInsertPackingReport(packingReportDO);
	}
	
	public List<PackingReportDTO> listPackingReport(PackingReportQuery packingReportQuery) {
		List<PackingReportDO> list = packingReportMapper.listPackingReport(packingReportQuery);
		return BeanMapper.mapList(list, PackingReportDTO.class);
	}
}