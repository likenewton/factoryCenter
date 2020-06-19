package cn.yunovo.iov.factory.biz.job;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.github.ore.boot.context.SpringContext;

import cn.yunovo.iov.factory.biz.dac.user.model.DacUserDTO;
import cn.yunovo.iov.factory.biz.dac.user.model.DacUserQuery;
import cn.yunovo.iov.factory.biz.dac.user.service.DacUserService;
import cn.yunovo.iov.factory.biz.production.test.model.DeviceTestQuery;
import cn.yunovo.iov.factory.biz.production.test.service.DeviceTestService;
import cn.yunovo.iov.factory.biz.statistics.assemble.model.StatisticsAssembleDO;
import cn.yunovo.iov.factory.biz.statistics.assemble.model.StatisticsAssembleQuery;
import cn.yunovo.iov.factory.biz.statistics.assemble.model.StatisticsAssembleVO;
import cn.yunovo.iov.factory.biz.statistics.assemble.service.StatisticsAssembleService;
import cn.yunovo.iov.factory.biz.statistics.paster.model.StatisticsPasterDO;
import cn.yunovo.iov.factory.biz.statistics.paster.model.StatisticsPasterQuery;
import cn.yunovo.iov.factory.biz.statistics.paster.model.StatisticsPasterVO;
import cn.yunovo.iov.factory.biz.statistics.paster.service.StatisticsPasterService;
import cn.yunovo.iov.factory.biz.statistics.type.model.StatisticsTypeQuery;
import cn.yunovo.iov.factory.biz.statistics.type.model.StatisticsTypeVO;
import cn.yunovo.iov.factory.biz.statistics.type.service.StatisticsTypeService;
import cn.yunovo.iov.factory.framework.Contants;
import cn.yunovo.iov.factory.framework.config.NacosValueConfig;
import cn.yunovo.iov.factory.framework.dac.DacResourceHelper;
import cn.yunovo.iov.framework.commons.lang.date.DateFormatConstants;
import cn.yunovo.iov.framework.commons.lang.date.DateGeneralUtils;
import lombok.extern.slf4j.Slf4j;

@Component
@EnableScheduling
@Slf4j
public class StatisticsTypeJob {

	@Autowired
	private StatisticsTypeService statisticsTypeService;

	@Autowired
	private DeviceTestService deviceTestService;

	@Autowired
	private StatisticsAssembleService statisticsAssembleService;

	@Autowired
	private StatisticsPasterService statisticsPasterService;

	@Autowired
	private NacosValueConfig nacosValueConfig;

	@SuppressWarnings({ "unchecked" })
	private void statisticsAssemble(String reportTimeString, Date reportTime, StatisticsTypeVO statisticsTypeVO) {

		if (StringUtils.isBlank(statisticsTypeVO.getFactoryName()) || StringUtils.isBlank(statisticsTypeVO.getFactoryName())) {
			return;
		}
		DeviceTestQuery deviceTestQuery = new DeviceTestQuery();
		deviceTestQuery.setFactoryName(statisticsTypeVO.getFactoryName());
		deviceTestQuery.setOrgCode(statisticsTypeVO.getOrgCode());
		deviceTestQuery.setProductionPhase(2);
		deviceTestQuery.setCreateTime(reportTimeString);
		Integer total = deviceTestService.sumStatisticsDeviceTest(deviceTestQuery);

		// 统计错误
		Integer errorTotal = deviceTestService.sumErrorStatisticsDeviceTest(deviceTestQuery);
		if (null == errorTotal) {
			errorTotal = 0;
		}

		// 统计
		StatisticsAssembleQuery statisticsAssembleQuery = new StatisticsAssembleQuery();
		statisticsAssembleQuery.setBrandName(statisticsTypeVO.getOrgCode());
		statisticsAssembleQuery.setFactoryName(statisticsTypeVO.getFactoryName());
		statisticsAssembleQuery.setReportTime(reportTimeString);
		List<StatisticsAssembleVO> salist = (List<StatisticsAssembleVO>) statisticsAssembleService.selectStatisticsAssemble(statisticsAssembleQuery, null, false);
		StatisticsAssembleDO statisticsAssembleDO = new StatisticsAssembleDO();
		statisticsAssembleDO.setBrandName(statisticsTypeVO.getOrgCode());
		statisticsAssembleDO.setFactoryName(statisticsTypeVO.getFactoryName());
		statisticsAssembleDO.setDeviceNumber(total);
		statisticsAssembleDO.setErrorNumber(errorTotal);
		statisticsAssembleDO.setReportTime(reportTime);

		if (null != salist && 0 < salist.size()) {
			statisticsAssembleDO.setId(salist.get(0).getId());
			statisticsAssembleService.updateStatisticsAssemble(statisticsAssembleDO);
		} else {

			statisticsAssembleService.insertStatisticsAssemble(statisticsAssembleDO);

			// 插入数据权限
			DacResourceHelper.insertBrandResource(Contants.TABLE_STATISTICS_ASSEMBLE, statisticsAssembleDO.getId(), statisticsTypeVO.getOrgCode(), statisticsTypeVO.getCreateId());
			DacUserService dacUserService = SpringContext.getBean(DacUserService.class);
			DacUserQuery dacUserQuery = new DacUserQuery();
			dacUserQuery.setUserMapper(statisticsTypeVO.getFactoryName());
			dacUserQuery.setUserType(2);
			DacUserDTO dacUserDTO = dacUserService.queryDacUser(dacUserQuery);
			if (null != dacUserDTO) {
				DacResourceHelper.insertFactoryResource(Contants.TABLE_STATISTICS_ASSEMBLE, statisticsAssembleDO.getId(), dacUserDTO.getUserId(), statisticsTypeVO.getCreateId());
			}
		}

	}

	/**
	 * 统计贴片
	 * 
	 * @param reportTimeString
	 * @param reportTime
	 * @param statisticsTypeVO
	 */
	@SuppressWarnings({ "unchecked" })
	private void statisticsPaster(String reportTimeString, Date reportTime, StatisticsTypeVO statisticsTypeVO) {

		if (StringUtils.isBlank(statisticsTypeVO.getFactoryName()) || StringUtils.isBlank(statisticsTypeVO.getFactoryName())) {
			return;
		}

		// 查询贴片设备数量
		DeviceTestQuery deviceTestQuery = new DeviceTestQuery();
		deviceTestQuery.setFactoryName(statisticsTypeVO.getFactoryName());
		deviceTestQuery.setOrgCode(statisticsTypeVO.getOrgCode());
		deviceTestQuery.setProductionPhase(1);
		deviceTestQuery.setCreateTime(reportTimeString);
		Integer total = deviceTestService.sumStatisticsDeviceTest(deviceTestQuery);

		// 统计错误
		Integer errorTotal = deviceTestService.sumErrorStatisticsDeviceTest(deviceTestQuery);
		if (null == errorTotal) {
			errorTotal = 0;
		}

		// 统计
		StatisticsPasterQuery statisticsPasterQuery = new StatisticsPasterQuery();
		statisticsPasterQuery.setFactoryName(statisticsTypeVO.getFactoryName());
		statisticsPasterQuery.setReportTime(reportTimeString);
		statisticsPasterQuery.setBrandName(statisticsTypeVO.getOrgCode());
		List<StatisticsPasterVO> plist = (List<StatisticsPasterVO>) statisticsPasterService.selectStatisticsPaster(statisticsPasterQuery, null, false);
		StatisticsPasterDO statisticsPasterDO = new StatisticsPasterDO();
		statisticsPasterDO.setBrandName(statisticsTypeVO.getOrgCode());
		statisticsPasterDO.setFactoryName(statisticsTypeVO.getFactoryName());
		statisticsPasterDO.setErrorNumber(errorTotal);
		statisticsPasterDO.setPasterNumber(total);
		statisticsPasterDO.setReportTime(reportTime);

		if (null != plist && 0 < plist.size()) {
			statisticsPasterDO.setId(plist.get(0).getId());
			statisticsPasterService.updateStatisticsPaster(statisticsPasterDO);
		} else {

			statisticsPasterService.insertStatisticsPaster(statisticsPasterDO);

			// 插入数据权限
			DacResourceHelper.insertBrandResource(Contants.TABLE_STATISTICS_PASTER, statisticsPasterDO.getId(), statisticsTypeVO.getOrgCode(), statisticsTypeVO.getCreateId());
			DacUserService dacUserService = SpringContext.getBean(DacUserService.class);
			DacUserQuery dacUserQuery = new DacUserQuery();
			dacUserQuery.setUserMapper(statisticsTypeVO.getFactoryName());
			dacUserQuery.setUserType(2);
			DacUserDTO dacUserDTO = dacUserService.queryDacUser(dacUserQuery);
			if (null != dacUserDTO) {
				DacResourceHelper.insertFactoryResource(Contants.TABLE_STATISTICS_PASTER, statisticsPasterDO.getId(), dacUserDTO.getUserId(), statisticsTypeVO.getCreateId());
			}
		}
	}

	@Scheduled(cron = "*/45 * * * * ?")
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void start() {
		if (nacosValueConfig.statisticsAble) {
			try {
				StatisticsTypeQuery statisticsTypeQuery = new StatisticsTypeQuery();
				Object obj = statisticsTypeService.selectStatisticsType(statisticsTypeQuery, null);
				if (null != obj) {
					List l = (List) obj;
					if (0 < l.size()) {

						String reportTimeString = DateGeneralUtils.format(new Date(), DateFormatConstants.yyyy_MM_dd);
						Date reportTime = DateGeneralUtils.parseDate(reportTimeString, DateFormatConstants.yyyy_MM_dd);

						List<StatisticsTypeVO> list = (List<StatisticsTypeVO>) obj;
						for (StatisticsTypeVO statisticsTypeVO : list) {

							// 统计贴片
							if (1 == statisticsTypeVO.getStatisticsType()) {
								statisticsPaster(reportTimeString, reportTime, statisticsTypeVO);

							}
							
							// 统计组装
							if (2 == statisticsTypeVO.getStatisticsType()) {
								statisticsAssemble(reportTimeString, reportTime, statisticsTypeVO);
							}

							statisticsTypeService.deleteStatisticsTypeById(statisticsTypeVO.getId());
						}
					}
				}
			} catch (Exception e) {
				log.error("StatisticsTypeJob {}", e);
			}
		}
	}
}
