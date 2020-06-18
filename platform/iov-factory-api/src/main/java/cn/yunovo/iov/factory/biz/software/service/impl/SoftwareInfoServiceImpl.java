package cn.yunovo.iov.factory.biz.software.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.yunovo.iov.factory.biz.software.mapper.SoftwareInfoMapper;
import cn.yunovo.iov.factory.biz.software.model.SoftwareInfoDO;
import cn.yunovo.iov.factory.biz.software.model.SoftwareInfoDTO;
import cn.yunovo.iov.factory.biz.software.model.SoftwareInfoQuery;
import cn.yunovo.iov.factory.biz.software.service.SoftwareInfoService;
import cn.yunovo.iov.framework.commons.beanutils.bean.BeanMapper;

@Service
public class SoftwareInfoServiceImpl implements SoftwareInfoService {

	@Autowired
	private SoftwareInfoMapper softwareInfoMapper;
	
	@Override
	public List<SoftwareInfoDTO> listSoftwareInfo(SoftwareInfoQuery softwareInfoQuery) {
		List<SoftwareInfoDO> list = softwareInfoMapper.listSoftwareInfo(softwareInfoQuery);
		return BeanMapper.mapList(list, SoftwareInfoDTO.class);
	}

	@Override
	public int saveOrInsertSoftwareInfo(SoftwareInfoDO softwareInfoDO) {
		return softwareInfoMapper.saveOrInsertSoftwareInfo(softwareInfoDO);
	}

}
