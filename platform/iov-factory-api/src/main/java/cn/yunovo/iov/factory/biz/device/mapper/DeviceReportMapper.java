package cn.yunovo.iov.factory.biz.device.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import cn.yunovo.iov.boot.autoconfigure.mybatis.GeneralMapper;
import cn.yunovo.iov.factory.biz.device.model.DeviceCard;
import cn.yunovo.iov.factory.biz.device.model.DeviceReportDO;
import cn.yunovo.iov.factory.biz.device.model.DeviceReportQuery;

/**
 * mapper 包为dao包， 注意：方法入参规则
 * 
 * @author huangzz
 *
 */
@Mapper
public interface DeviceReportMapper extends GeneralMapper<DeviceReportDO> {

	List<DeviceReportDO> listDeviceReport(DeviceReportQuery deviceReportQuery);
	
	List<DeviceReportDO> listDevice(DeviceReportQuery deviceReportQuery);

	Integer saveOrInsertDeviceReport(DeviceReportDO deviceReportDO);

	DeviceCard getDeviceCard(DeviceCard deviceCard);

	Integer saveDeviceCard(DeviceCard deviceCard);
	
	Integer updateDeviceCard(DeviceCard deviceCard);
	
	Integer saveServiceLog(@Param("iccid") String iccid, @Param("optType") Integer optType,
			@Param("reporter") String reporter, @Param("desc") String desc);

	Integer saveSNChange(@Param("sn") String sn, @Param("latestSn") String latestSn, @Param("iccid") String iccid);

}
