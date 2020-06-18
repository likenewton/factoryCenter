package cn.yunovo.iov.factory.biz.report.software.service;

import java.util.Map;

import cn.yunovo.iov.boot.autoconfigure.request.select.Condition;
import cn.yunovo.iov.factory.biz.report.software.model.SoftwareDO;
import cn.yunovo.iov.factory.biz.report.software.model.SoftwareDTO;
import cn.yunovo.iov.factory.biz.report.software.model.SoftwareQuery;

/**
 * Service层：相对具体的业务逻辑服务层。对多个Manager的组合复用 。
 * 
 * @author huangzz
 *
 */
public interface SoftwareService {

	SoftwareDTO getSoftwareById(Integer id);

	Object selectSoftware(SoftwareQuery softwareQuery, Map<String, Condition> conditionMap);
	
	Integer insertSoftware(SoftwareDO softwareDO);

	Integer deleteSoftwareById(Integer id);

	Integer updateSoftware(SoftwareDO softwareDO);

}
