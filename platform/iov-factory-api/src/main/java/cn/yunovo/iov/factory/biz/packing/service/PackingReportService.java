package cn.yunovo.iov.factory.biz.packing.service;

import java.util.List;
import java.util.Map;

import com.github.ore.framework.web.api.ResultEntity;

import cn.yunovo.iov.factory.biz.packing.model.PackingReport;
import cn.yunovo.iov.factory.biz.packing.model.PackingReportDTO;
import cn.yunovo.iov.factory.biz.packing.model.PackingReportQuery;
import cn.yunovo.iov.factory.biz.software.model.SoftwareInfoDTO;

/**
 * Service层：相对具体的业务逻辑服务层。对多个Manager的组合复用 。
 * 
 * @author huangzz
 *
 */
public interface PackingReportService {

	ResultEntity<PackingReport> reportPacking(PackingReport packingReport, Map<String, SoftwareInfoDTO> softwareMap);

	List<PackingReportDTO> listPacking(PackingReportQuery packingReportQuery);
}
