package cn.yunovo.iov.factory.biz.packing.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.ore.framework.web.api.ResultEntity;
import com.google.gson.Gson;

import cn.yunovo.iov.factory.biz.VendorService;
import cn.yunovo.iov.factory.biz.device.manager.DeviceReportManager;
import cn.yunovo.iov.factory.biz.device.model.DeviceCard;
import cn.yunovo.iov.factory.biz.packing.manager.PackingReportManager;
import cn.yunovo.iov.factory.biz.packing.model.PackingReport;
import cn.yunovo.iov.factory.biz.packing.model.PackingReportDO;
import cn.yunovo.iov.factory.biz.packing.model.PackingReportDTO;
import cn.yunovo.iov.factory.biz.packing.model.PackingReportQuery;
import cn.yunovo.iov.factory.biz.packing.service.PackingReportService;
import cn.yunovo.iov.factory.biz.software.model.SoftwareInfoDTO;
import cn.yunovo.iov.framework.commons.beanutils.bean.BeanMapper;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PackingReportServiceImpl implements PackingReportService {

	@Autowired
	private DeviceReportManager deviceReportManager;

	@Autowired
	private PackingReportManager packingReportManager;

	@SuppressWarnings("unchecked")
	private ResultEntity<PackingReport> updateOrgCode(PackingReport packingReport, Map<String, SoftwareInfoDTO> softwareMap, ResultEntity<PackingReport> re) {

		String reslut = null;
		String swCode = packingReport.getSwCode();

		swCode = swCode.replace(packingReport.getImei(), "");
		DeviceCard deviceCard = new DeviceCard();
		deviceCard.setImei(packingReport.getImei());
		deviceCard = deviceReportManager.getDeviceCard(deviceCard);

		// 根据SWCODE,修改组织机构
		if (null != deviceCard) {
			SoftwareInfoDTO softwareInfo = softwareMap.get(swCode);
			if (null != softwareInfo) {
				reslut = VendorService.orgUpdate(softwareInfo.getOrgCode(), softwareInfo.getProjectModel(), deviceCard.getIccid(), deviceCard.getImei(), deviceCard.getSn());
				Gson gson = new Gson();
				ResultEntity<String> resultEntity = gson.fromJson(reslut, ResultEntity.class);
				log.info("根据SWCODE,修改组织机构,{}", resultEntity);
				if (!resultEntity.getCode().equals("0")) {
					// re.setMsg(resultEntity.getMsg());
					log.error(resultEntity.getMsg());
				}
				deviceReportManager.saveServiceLog(deviceCard.getIccid(), 1, packingReport.getReporter(), reslut);
			} else {
				VendorService.sendDing("根据SWCODE,修改组织机构 ", "软件信息还没上报swCode：" + swCode);
				re.setMsg("软件信息还没上报swCode：" + swCode);
				deviceReportManager.saveServiceLog(deviceCard.getIccid(), 1, packingReport.getReporter(), "软件信息还没上报swCode：" + swCode);
			}
		} else {
			VendorService.sendDing("根据SWCODE,修改组织机构 ", "根据上报的IMEI:" + packingReport.getImei() + "信息，没找到对应的 ICCID");
			re.setMsg("根据上报的IMEI:" + packingReport.getImei() + "信息，没找到对应的 ICCID");
		}
		return re;
	}

	@Override
	public ResultEntity<PackingReport> reportPacking(PackingReport packingReport, Map<String, SoftwareInfoDTO> softwareMap) {

		ResultEntity<PackingReport> re = new ResultEntity<PackingReport>();
		re.setData(packingReport);
		if (StringUtils.isNotEmpty(packingReport.getImei()) && StringUtils.isNotEmpty(packingReport.getSwCode())) {

			// 保存上报信息
			PackingReportDO packingReportDO = BeanMapper.map(packingReport, PackingReportDO.class);
			packingReportDO.setCreateDatetime(new Date());
			packingReportManager.saveOrInsertDeviceReport(packingReportDO);

			// 修改流量卡的组织机构
			return updateOrgCode(packingReport, softwareMap, re);
		} else {
			re.setMsg("imei/swCode 都不能为空!");
		}
		return re;

	}

	@Override
	public List<PackingReportDTO> listPacking(PackingReportQuery packingReportQuery) {
		return packingReportManager.listPackingReport(packingReportQuery);
	}

}
