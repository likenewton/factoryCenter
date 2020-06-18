package cn.yunovo.iov.factory.biz.packing.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.ore.framework.web.api.ResultEntity;

import cn.yunovo.iov.factory.biz.Contants;
import cn.yunovo.iov.factory.biz.VendorService;
import cn.yunovo.iov.factory.biz.packing.model.PackingReport;
import cn.yunovo.iov.factory.biz.packing.model.PackingReportDTO;
import cn.yunovo.iov.factory.biz.packing.model.PackingReportQuery;
import cn.yunovo.iov.factory.biz.packing.service.PackingReportService;
import cn.yunovo.iov.factory.biz.software.model.SoftwareInfoDTO;
import cn.yunovo.iov.factory.biz.software.model.SoftwareInfoQuery;
import cn.yunovo.iov.factory.biz.software.service.SoftwareInfoService;
import cn.yunovo.iov.framework.commons.beanutils.bean.BeanMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "PackingController 相关 api")
@RestController
@RequestMapping("/report")
public class PackingController {

	@Autowired
	private PackingReportService packingReportService;

	@Autowired
	private SoftwareInfoService softwareInfoService;

	private static Map<String, SoftwareInfoDTO> softwareMap = new HashMap<String, SoftwareInfoDTO>();

	/**
	 * 组装的时候，上报组装信息
	 * 
	 * @param deviceInfo
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@ApiOperation("上报组装信息")
	@RequestMapping(value = "/packingz", method = RequestMethod.POST)
	public ResultEntity<PackingReport> packingz(@RequestBody PackingReport packingInfo, HttpServletRequest request) {
		try {
			Object map = request.getServletContext().getAttribute(Contants.SOFTWARE_LIST);
			if (null == map) {
				SoftwareInfoQuery softwareInfoQuery = new SoftwareInfoQuery();
				List<SoftwareInfoDTO> list = softwareInfoService.listSoftwareInfo(softwareInfoQuery);
				for (SoftwareInfoDTO software : list) {
					softwareMap.put(software.getSwCode(), software);
				}
				request.getServletContext().setAttribute(Contants.SOFTWARE_LIST, softwareMap);
			} else {
				softwareMap = (Map<String, SoftwareInfoDTO>) map;
			}
			return packingReportService.reportPacking(packingInfo, softwareMap);
		} catch (Exception e) {
			VendorService.sendDing("上报组装信息", "输入参数：" + packingInfo.toString() + "异常信息：" + e.getMessage());
		}
		return null;

	}

	@ApiOperation("根据设备的IMEI，查询信息")
	@RequestMapping(value = "/packingz", method = RequestMethod.GET)
	public List<PackingReport> packingz(String swCode, String imei) {

		// 获取数据
		PackingReportQuery packingReportQuery = new PackingReportQuery();
		packingReportQuery.setImei(imei);
		packingReportQuery.setSwCode(swCode);
		List<PackingReportDTO> list = packingReportService.listPacking(packingReportQuery);
		List<PackingReport> vList = BeanMapper.mapList(list, PackingReport.class);
		return vList;
	}
}
