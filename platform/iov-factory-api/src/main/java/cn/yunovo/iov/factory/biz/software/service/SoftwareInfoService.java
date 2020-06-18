package cn.yunovo.iov.factory.biz.software.service;

import java.util.List;

import cn.yunovo.iov.factory.biz.software.model.SoftwareInfoDO;
import cn.yunovo.iov.factory.biz.software.model.SoftwareInfoDTO;
import cn.yunovo.iov.factory.biz.software.model.SoftwareInfoQuery;

/**
 * Service层：相对具体的业务逻辑服务层。对多个Manager的组合复用 。
 * 
 * @author huangzz
 *
 */
public interface SoftwareInfoService {


	List<SoftwareInfoDTO> listSoftwareInfo(SoftwareInfoQuery softwareInfoQuery);

	int saveOrInsertSoftwareInfo(SoftwareInfoDO softwareInfoDO);

}
