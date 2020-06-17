package cn.yunovo.iov.factory.biz.packing.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import cn.yunovo.iov.boot.autoconfigure.mybatis.GeneralMapper;
import cn.yunovo.iov.factory.biz.packing.model.PackingReportDO;
import cn.yunovo.iov.factory.biz.packing.model.PackingReportQuery;

/**
 * mapper 包为dao包， 注意：方法入参规则
 * 
 * @author huangzz
 *
 */
@Mapper
public interface PackingReportMapper extends GeneralMapper<PackingReportDO> {

	Integer saveOrInsertPackingReport(PackingReportDO packingReportDO);
	
	List<PackingReportDO> listPackingReport(PackingReportQuery packingReportQuery);
}
