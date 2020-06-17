package cn.yunovo.iov.factory.biz.software.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import cn.yunovo.iov.boot.autoconfigure.mybatis.GeneralMapper;
import cn.yunovo.iov.factory.biz.software.model.SoftwareInfoDO;
import cn.yunovo.iov.factory.biz.software.model.SoftwareInfoQuery;

@Mapper
public interface SoftwareInfoMapper extends GeneralMapper<SoftwareInfoDO> {
	
	List<SoftwareInfoDO> listSoftwareInfo(SoftwareInfoQuery softwareInfoQuery);

	int saveOrInsertSoftwareInfo(SoftwareInfoDO softwareInfoDO);
}
