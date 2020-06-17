package cn.yunovo.iov.factory.biz.job;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import cn.yunovo.iov.factory.biz.production.test.model.DeviceTestQuery;
import cn.yunovo.iov.factory.biz.production.test.service.DeviceTestService;
import cn.yunovo.iov.factory.biz.shipping.channel.model.ChannelDTO;
import cn.yunovo.iov.factory.biz.shipping.channel.service.ChannelService;
import cn.yunovo.iov.factory.biz.shipping.shipping.model.ShippingListQuery;
import cn.yunovo.iov.factory.biz.shipping.shipping.model.ShippingListVO;
import cn.yunovo.iov.factory.biz.shipping.shipping.service.ShippingListService;
import cn.yunovo.iov.factory.biz.statistics.area.model.StatisticsAreaDO;
import cn.yunovo.iov.factory.biz.statistics.area.model.StatisticsAreaDTO;
import cn.yunovo.iov.factory.biz.statistics.area.model.StatisticsAreaQuery;
import cn.yunovo.iov.factory.biz.statistics.area.service.StatisticsAreaService;
import cn.yunovo.iov.factory.biz.statistics.assemble.model.StatisticsAssembleDO;
import cn.yunovo.iov.factory.biz.statistics.assemble.model.StatisticsAssembleQuery;
import cn.yunovo.iov.factory.biz.statistics.assemble.model.StatisticsAssembleVO;
import cn.yunovo.iov.factory.biz.statistics.assemble.service.StatisticsAssembleService;
import cn.yunovo.iov.factory.biz.statistics.paster.model.StatisticsPasterDO;
import cn.yunovo.iov.factory.biz.statistics.paster.model.StatisticsPasterQuery;
import cn.yunovo.iov.factory.biz.statistics.paster.model.StatisticsPasterVO;
import cn.yunovo.iov.factory.biz.statistics.paster.service.StatisticsPasterService;
import cn.yunovo.iov.factory.biz.statistics.shipping.model.StatisticsShippingListDO;
import cn.yunovo.iov.factory.biz.statistics.shipping.model.StatisticsShippingListQuery;
import cn.yunovo.iov.factory.biz.statistics.shipping.model.StatisticsShippingListVO;
import cn.yunovo.iov.factory.biz.statistics.shipping.service.StatisticsShippingListService;
import cn.yunovo.iov.factory.biz.statistics.sumtotal.model.StatisticsSumTotalDO;
import cn.yunovo.iov.factory.biz.statistics.sumtotal.model.StatisticsSumTotalDTO;
import cn.yunovo.iov.factory.biz.statistics.sumtotal.model.StatisticsSumTotalQuery;
import cn.yunovo.iov.factory.biz.statistics.sumtotal.service.StatisticsSumTotalService;
import cn.yunovo.iov.factory.biz.statistics.type.model.StatisticsTypeQuery;
import cn.yunovo.iov.factory.biz.statistics.type.model.StatisticsTypeVO;
import cn.yunovo.iov.factory.biz.statistics.type.service.StatisticsTypeService;
import cn.yunovo.iov.factory.framework.Contants;
import cn.yunovo.iov.factory.framework.YunovoCodeUtil;
import cn.yunovo.iov.factory.framework.config.NacosValueConfig;
import cn.yunovo.iov.factory.framework.dac.DacHelper;
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
	private ShippingListService shippingListService;

	@Autowired
	private StatisticsSumTotalService statisticsSumTotalService;

	@Autowired
	private StatisticsShippingListService statisticsShippingListService;

	@Autowired
	private StatisticsAreaService statisticsAreaService;

	@Autowired
	private ChannelService channelService;

	@Autowired
	private NacosValueConfig nacosValueConfig;

	private void statisticsSumTotal(Integer statisticsType, Integer todayNumber, Integer sumTotal) {
		if (null == todayNumber || null == sumTotal) {
			return;
		}
		StatisticsSumTotalQuery statisticsSumTotalQuery = new StatisticsSumTotalQuery();
		statisticsSumTotalQuery.setStatisticsType(statisticsType);
		StatisticsSumTotalDTO statisticsSumTotalDTO = statisticsSumTotalService.queryStatisticsSumTotal(statisticsSumTotalQuery);

		StatisticsSumTotalDO statisticsSumTotalDO = new StatisticsSumTotalDO();
		statisticsSumTotalDO.setStatisticsType(statisticsType);
		statisticsSumTotalDO.setTodayNumber(todayNumber);
		statisticsSumTotalDO.setSumTotal(sumTotal);
		if (null != statisticsSumTotalDTO) {
			statisticsSumTotalDO.setId(statisticsSumTotalDTO.getId());
			statisticsSumTotalService.updateStatisticsSumTotal(statisticsSumTotalDO);
		} else {
			statisticsSumTotalService.insertStatisticsSumTotal(statisticsSumTotalDO);
		}
	}

	@SuppressWarnings("unchecked")
	private void statisticsArea(StatisticsTypeVO statisticsTypeVO) {
		ShippingListQuery shippingListQuery = new ShippingListQuery();
		Integer deviceNumber = 0;
		shippingListQuery.setBrandName(statisticsTypeVO.getOrgCode());
		shippingListQuery.setFactoryName(statisticsTypeVO.getFactoryName());
		shippingListQuery.setChannelId(statisticsTypeVO.getChannelId());
		List<ShippingListVO> shippings = (List<ShippingListVO>) shippingListService.selectShippingList(shippingListQuery, null, false);
		if (null != shippings && 0 < shippings.size()) {
			ShippingListVO shippingListVO = shippings.get(0);
			String area = YunovoCodeUtil.getArea(shippingListVO.getArea());
	
			shippingListQuery = new ShippingListQuery();
			shippingListQuery.setArea(area);
			shippingListQuery.setBrandName(statisticsTypeVO.getOrgCode());
			shippingListQuery.setFactoryName(statisticsTypeVO.getFactoryName());
			shippingListQuery.setChannelId(statisticsTypeVO.getChannelId());
			deviceNumber = shippingListService.statisticsCurrentDay(shippingListQuery);
			if (null == deviceNumber) {
				deviceNumber = 0;
			}
			StatisticsAreaQuery statisticsAreaQuery = new StatisticsAreaQuery();
			statisticsAreaQuery.setArea(area);
			statisticsAreaQuery.setBrandName(statisticsTypeVO.getOrgCode());
			statisticsAreaQuery.setFactoryName(statisticsTypeVO.getFactoryName());
			statisticsAreaQuery.setChannelId(statisticsTypeVO.getChannelId());
			StatisticsAreaDTO statisticsAreaDTO = statisticsAreaService.queryStatisticsArea(statisticsAreaQuery);
	
			StatisticsAreaDO statisticsAreaDO = new StatisticsAreaDO();
			statisticsAreaDO.setArea(area);
			statisticsAreaDO.setChannelId(statisticsTypeVO.getChannelId());
			statisticsAreaDO.setFactoryName(statisticsTypeVO.getFactoryName());
			statisticsAreaDO.setBrandName(shippingListVO.getBrandName());
			statisticsAreaDO.setDeviceNumber(deviceNumber);
	
			if (null != statisticsAreaDTO) {
				statisticsAreaDO.setId(statisticsAreaDTO.getId());
				statisticsAreaService.updateStatisticsArea(statisticsAreaDO);
			} else {
				statisticsAreaService.insertStatisticsArea(statisticsAreaDO);
				ChannelDTO channelDTO = channelService.getChannelById(statisticsTypeVO.getChannelId());
	
				// 插入数据权限
				DacHelper.insertChannelResource(Contants.TABLE_STATISTICS_AREA, statisticsAreaDO.getId(), channelDTO.getPhone(), statisticsTypeVO.getCreateId(), null);
				DacHelper.insertBrandResource(Contants.TABLE_STATISTICS_AREA, statisticsAreaDO.getId(), statisticsTypeVO.getOrgCode(), statisticsTypeVO.getCreateId(), null);
				DacHelper.insertFactoryResource(Contants.TABLE_STATISTICS_AREA, statisticsAreaDO.getId(), null, statisticsTypeVO.getCreateId(), null, statisticsTypeVO.getFactoryName());
	
			}
		}
	}

	@SuppressWarnings({ "unchecked" })
	private void statisticsShipping(String reportTimeString, Date reportTime, StatisticsTypeVO statisticsTypeVO) {

		// 统计每天发货数量
		ShippingListQuery shippingListQuery = new ShippingListQuery();
		shippingListQuery.setImportTime(reportTimeString);
		shippingListQuery.setBrandName(statisticsTypeVO.getOrgCode());
		shippingListQuery.setFactoryName(statisticsTypeVO.getFactoryName());
		Integer deviceNumber = shippingListService.statisticsCurrentDay(shippingListQuery);
		if (null == deviceNumber) {
			deviceNumber = 0;
		}
		StatisticsShippingListQuery statisticsShippingListQuery = new StatisticsShippingListQuery();
		statisticsShippingListQuery.setBrandName(statisticsTypeVO.getOrgCode());
		statisticsShippingListQuery.setFactoryName(statisticsTypeVO.getFactoryName());
		statisticsShippingListQuery.setReportTime(reportTimeString);
		List<StatisticsShippingListVO> shippingList = (List<StatisticsShippingListVO>) statisticsShippingListService.selectStatisticsShippingList(statisticsShippingListQuery, null, false);

		StatisticsShippingListDO statisticsShippingListDO = new StatisticsShippingListDO();
		statisticsShippingListDO.setBrandName(statisticsTypeVO.getOrgCode());
		statisticsShippingListDO.setFactoryName(statisticsTypeVO.getFactoryName());
		statisticsShippingListDO.setReportTime(reportTime);

		if (null != shippingList && 0 < shippingList.size()) {
			StatisticsShippingListVO statisticsShippingVO = shippingList.get(0);
			statisticsShippingListDO.setId(statisticsShippingVO.getId());
			statisticsShippingListDO.setDeviceNumber(deviceNumber);
			statisticsShippingListService.updateStatisticsShippingList(statisticsShippingListDO);
		} else {
			statisticsShippingListDO.setDeviceNumber(deviceNumber);
			statisticsShippingListDO.setChannelId(statisticsTypeVO.getChannelId());
			statisticsShippingListService.insertStatisticsShippingList(statisticsShippingListDO);

			ChannelDTO channelDTO = channelService.getChannelById(statisticsTypeVO.getChannelId());

			// 插入数据权限
			DacHelper.insertChannelResource(Contants.TABLE_STATISTICS_SHIPPINGLIST, statisticsShippingListDO.getId(), channelDTO.getPhone(), statisticsTypeVO.getCreateId(), null);
			DacHelper.insertBrandResource(Contants.TABLE_STATISTICS_SHIPPINGLIST, statisticsShippingListDO.getId(), statisticsTypeVO.getOrgCode(), statisticsTypeVO.getCreateId(), null);
			DacHelper.insertFactoryResource(Contants.TABLE_STATISTICS_SHIPPINGLIST, statisticsShippingListDO.getId(), null, statisticsTypeVO.getCreateId(), null, statisticsTypeVO.getFactoryName());

		}

		// 统计省发货数量
		statisticsArea(statisticsTypeVO);

		Integer todayNumber = 0;
		Integer sumTotal = 0;

		// 统计发货总数
		statisticsShippingListQuery = new StatisticsShippingListQuery();
		statisticsShippingListQuery.setReportTime(reportTimeString);
		todayNumber = statisticsShippingListService.statisticsCurrentDay(statisticsShippingListQuery);

		// 全部发货总数
		statisticsShippingListQuery = new StatisticsShippingListQuery();
		sumTotal = statisticsShippingListService.statisticsCurrentDay(statisticsShippingListQuery);

		statisticsSumTotal(3, todayNumber, sumTotal);
	}

	@SuppressWarnings({ "unchecked" })
	private void statisticsAssemble(String reportTimeString, Date reportTime, StatisticsTypeVO statisticsTypeVO) {
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

			// 特殊情况可以硬编码方式：插入组装数据机构用户权限
			DacHelper.insertBrandResource(Contants.TABLE_STATISTICS_ASSEMBLE, statisticsAssembleDO.getId(), statisticsTypeVO.getOrgCode(), statisticsTypeVO.getCreateId(), null);

			// 特殊情况可以硬编码方式：插入贴片数据工厂用户权限
			DacHelper.insertFactoryResource(Contants.TABLE_STATISTICS_ASSEMBLE, statisticsAssembleDO.getId(), null, statisticsTypeVO.getCreateId(), null, statisticsTypeVO.getFactoryName());

		}

		Integer todayNumber = 0;
		Integer sumTotal = 0;

		// 统计今天组装总数
		statisticsAssembleQuery = new StatisticsAssembleQuery();
		statisticsAssembleQuery.setReportTime(reportTimeString);
		todayNumber = statisticsAssembleService.statisticsCurrentDay(statisticsAssembleQuery);

		// 全部组装总数
		statisticsAssembleQuery = new StatisticsAssembleQuery();
		sumTotal = statisticsAssembleService.statisticsCurrentDay(statisticsAssembleQuery);

		statisticsSumTotal(2, todayNumber, sumTotal);
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

			// 特殊情况可以硬编码方式：插入组装数据机构用户权限
			DacHelper.insertBrandResource(Contants.TABLE_STATISTICS_PASTER, statisticsPasterDO.getId(), statisticsTypeVO.getOrgCode(), statisticsTypeVO.getCreateId(), null);

			// 特殊情况可以硬编码方式：插入贴片数据工厂用户权限
			DacHelper.insertFactoryResource(Contants.TABLE_STATISTICS_PASTER, statisticsPasterDO.getId(), null, statisticsTypeVO.getCreateId(), null,statisticsTypeVO.getFactoryName());

		}

		Integer todayNumber = 0;
		Integer sumTotal = 0;

		// 统计今天贴片总数
		statisticsPasterQuery = new StatisticsPasterQuery();
		statisticsPasterQuery.setReportTime(reportTimeString);
		todayNumber = statisticsPasterService.statisticsCurrentDay(statisticsPasterQuery);

		// 全部贴片总数
		statisticsPasterQuery = new StatisticsPasterQuery();
		sumTotal = statisticsPasterService.statisticsCurrentDay(statisticsPasterQuery);

		statisticsSumTotal(1, todayNumber, sumTotal);
	}

	@Scheduled(cron = "*/59 * * * * ?")
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

								// 查询设备数量
								statisticsAssemble(reportTimeString, reportTime, statisticsTypeVO);
							}

							// 首页统计当天发货
							if (3 == statisticsTypeVO.getStatisticsType()) {

								statisticsShipping(reportTimeString, reportTime, statisticsTypeVO);
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
