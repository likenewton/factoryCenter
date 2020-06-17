package cn.yunovo.iov.device.zen.biz.job;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import cn.yunovo.iov.device.zen.biz.production.test.model.DeviceTestQuery;
import cn.yunovo.iov.device.zen.biz.production.test.service.DeviceTestService;
import cn.yunovo.iov.device.zen.biz.shipping.shipping.model.ShippingListQuery;
import cn.yunovo.iov.device.zen.biz.shipping.shipping.model.ShippingListVO;
import cn.yunovo.iov.device.zen.biz.shipping.shipping.service.ShippingListService;
import cn.yunovo.iov.device.zen.biz.statistics.area.model.StatisticsAreaDO;
import cn.yunovo.iov.device.zen.biz.statistics.area.model.StatisticsAreaDTO;
import cn.yunovo.iov.device.zen.biz.statistics.area.model.StatisticsAreaQuery;
import cn.yunovo.iov.device.zen.biz.statistics.area.service.StatisticsAreaService;
import cn.yunovo.iov.device.zen.biz.statistics.assemble.model.StatisticsAssembleDO;
import cn.yunovo.iov.device.zen.biz.statistics.assemble.model.StatisticsAssembleQuery;
import cn.yunovo.iov.device.zen.biz.statistics.assemble.model.StatisticsAssembleVO;
import cn.yunovo.iov.device.zen.biz.statistics.assemble.service.StatisticsAssembleService;
import cn.yunovo.iov.device.zen.biz.statistics.paster.model.StatisticsPasterDO;
import cn.yunovo.iov.device.zen.biz.statistics.paster.model.StatisticsPasterQuery;
import cn.yunovo.iov.device.zen.biz.statistics.paster.model.StatisticsPasterVO;
import cn.yunovo.iov.device.zen.biz.statistics.paster.service.StatisticsPasterService;
import cn.yunovo.iov.device.zen.biz.statistics.shipping.model.StatisticsShippingListDO;
import cn.yunovo.iov.device.zen.biz.statistics.shipping.model.StatisticsShippingListQuery;
import cn.yunovo.iov.device.zen.biz.statistics.shipping.model.StatisticsShippingListVO;
import cn.yunovo.iov.device.zen.biz.statistics.shipping.service.StatisticsShippingListService;
import cn.yunovo.iov.device.zen.biz.statistics.sumtotal.model.StatisticsSumTotalDO;
import cn.yunovo.iov.device.zen.biz.statistics.sumtotal.model.StatisticsSumTotalDTO;
import cn.yunovo.iov.device.zen.biz.statistics.sumtotal.model.StatisticsSumTotalQuery;
import cn.yunovo.iov.device.zen.biz.statistics.sumtotal.service.StatisticsSumTotalService;
import cn.yunovo.iov.device.zen.biz.statistics.type.model.StatisticsTypeQuery;
import cn.yunovo.iov.device.zen.biz.statistics.type.model.StatisticsTypeVO;
import cn.yunovo.iov.device.zen.biz.statistics.type.service.StatisticsTypeService;
import cn.yunovo.iov.device.zen.framework.config.NacosValueConfig;
import cn.yunovo.iov.framework.commons.lang.date.DateFormatConstants;
import cn.yunovo.iov.framework.commons.lang.date.DateGeneralUtils;
import lombok.extern.slf4j.Slf4j;

@Component
@EnableScheduling
@Slf4j
public class StatisticsTypeClearJob {

	@Autowired
	private StatisticsSumTotalService statisticsSumTotalService;

	@Scheduled(cron = "0 59 23 * * ?")
	//@Scheduled(cron = "*/59 * * * * ?")
	public void start() {
		try {
			StatisticsSumTotalDO statisticsSumTotalDO = new StatisticsSumTotalDO();
			statisticsSumTotalDO.setTodayNumber(0);
			statisticsSumTotalService.updateStatisticsSumTotal(statisticsSumTotalDO);
		} catch (Exception e) {
			log.error("StatisticsTypeClearJob {}", e);
		}
	}
}
