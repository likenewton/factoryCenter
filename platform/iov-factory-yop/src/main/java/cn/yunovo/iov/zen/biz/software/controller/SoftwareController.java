package cn.yunovo.iov.zen.biz.software.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.ore.framework.web.api.ResultEntity;

import cn.yunovo.iov.framework.commons.beanutils.bean.BeanMapper;
import cn.yunovo.iov.framework.commons.lang.date.DateFormatConstants;
import cn.yunovo.iov.zen.biz.Contants;
import cn.yunovo.iov.zen.biz.VendorService;
import cn.yunovo.iov.zen.biz.software.model.SoftwareInfo;
import cn.yunovo.iov.zen.biz.software.model.SoftwareInfoDO;
import cn.yunovo.iov.zen.biz.software.model.SoftwareInfoDTO;
import cn.yunovo.iov.zen.biz.software.model.SoftwareInfoQuery;
import cn.yunovo.iov.zen.biz.software.service.SoftwareInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "SoftwareController 相关 api")
@RestController
@RequestMapping("/report")
public class SoftwareController {

	@Autowired
	private SoftwareInfoService softwareInfoService;

	private static Map<String, SoftwareInfoDTO> softwareMap = new HashMap<String, SoftwareInfoDTO>();

	@SuppressWarnings("unchecked")
	@ApiOperation("包装发货时上报软件信息")
	@RequestMapping(value = "/softwares", method = RequestMethod.POST)
	public ResultEntity<Integer> softwares(@RequestBody SoftwareInfo softwareInfo, HttpServletRequest request) {
		try {

			ResultEntity<Integer> result = new ResultEntity<>();
			if (StringUtils.isNotEmpty(softwareInfo.getSwCode()) && StringUtils.isNotEmpty(softwareInfo.getProjectModel())
					&& StringUtils.isNotEmpty(softwareInfo.getOrgCode())) {

				Object map = request.getServletContext().getAttribute(Contants.SOFTWARE_LIST);

				if (null == map) {
					SoftwareInfoQuery softwareInfoQuery = new SoftwareInfoQuery();
					List<SoftwareInfoDTO> list = softwareInfoService.listSoftwareInfo(softwareInfoQuery);
					for (SoftwareInfoDTO software : list) {
						softwareMap.put(software.getSwCode(), software);
					}
				} else {
					softwareMap = (Map<String, SoftwareInfoDTO>) map;
					SoftwareInfoDTO software = new SoftwareInfoDTO();
					software.setOrgCode(softwareInfo.getOrgCode());
					software.setProjectModel(softwareInfo.getProjectModel());
					software.setSwCode(softwareInfo.getSwCode());
					software.setRomVersion(softwareInfo.getRomVersion());
					software.setReporter(softwareInfo.getReporter());
					softwareMap.put(softwareInfo.getSwCode(), software);
				}

				request.getServletContext().setAttribute(Contants.SOFTWARE_LIST, softwareMap);
				SoftwareInfoDO softwareInfoDO = BeanMapper.map(softwareInfo, SoftwareInfoDO.class);
				result.setData(softwareInfoService.saveOrInsertSoftwareInfo(softwareInfoDO));
			} else {
				result.setMsg("swCode/projectModel/orgCode都不能为空!");
				result.setData(0);
			}
			return result;
		} catch (Exception e) {
			VendorService.sendDing("上报软件信息发生异常", "输入参数：" + softwareInfo.toString() + "异常信息：" + e.getMessage());
		}
		return null;

	}

	@ApiOperation("根据软件信息的swCode/romVersion，查询上报信息")
	@RequestMapping(value = "/softwares", method = RequestMethod.GET)
	public List<SoftwareInfo> softwares(String swCode, String romVersion) {

		// 获取数据
		SoftwareInfoQuery softwareInfoQuery = new SoftwareInfoQuery();
		softwareInfoQuery.setSwCode(swCode);
		softwareInfoQuery.setRomVersion(romVersion);
		List<SoftwareInfoDTO> list = softwareInfoService.listSoftwareInfo(softwareInfoQuery);
		List<SoftwareInfo> softwareInfoVOList = new ArrayList<SoftwareInfo>();

		if (!list.isEmpty()) {
			for (SoftwareInfoDTO softwareInfoDTO : list) {
				SoftwareInfo softwareInfo = BeanMapper.map(softwareInfoDTO, SoftwareInfo.class);
				softwareInfo.setReportTime(DateFormatUtils.format(softwareInfoDTO.getAddDatetime(),
						DateFormatConstants.yyyy_MM_dd_HH_mm_ss));
				softwareInfoVOList.add(softwareInfo);
			}
		}
		return softwareInfoVOList;
	}

}
