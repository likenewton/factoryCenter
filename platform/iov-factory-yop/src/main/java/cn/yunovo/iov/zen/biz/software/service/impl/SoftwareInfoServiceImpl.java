package cn.yunovo.iov.zen.biz.software.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.yunovo.iov.framework.commons.beanutils.bean.BeanMapper;
import cn.yunovo.iov.zen.biz.software.mapper.SoftwareInfoMapper;
import cn.yunovo.iov.zen.biz.software.model.SoftwareInfoDO;
import cn.yunovo.iov.zen.biz.software.model.SoftwareInfoDTO;
import cn.yunovo.iov.zen.biz.software.model.SoftwareInfoQuery;
import cn.yunovo.iov.zen.biz.software.service.SoftwareInfoService;

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
