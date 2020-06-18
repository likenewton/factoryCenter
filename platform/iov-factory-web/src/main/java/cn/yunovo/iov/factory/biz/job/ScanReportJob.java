package cn.yunovo.iov.factory.biz.job;

import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import cn.yunovo.iov.factory.biz.report.report.manager.DeviceReportManager;
import cn.yunovo.iov.factory.biz.statistics.report.model.ScanReportDO;
import cn.yunovo.iov.factory.biz.statistics.report.service.ScanReportService;
import cn.yunovo.iov.framework.commons.lang.date.DateFormatConstants;
import lombok.extern.slf4j.Slf4j;

@Component
@EnableScheduling
@Slf4j
public class ScanReportJob {

	@Autowired
	private DeviceReportManager deviceReportManager;

	@Autowired
	private ScanReportService scanReportService;

	@Scheduled(cron = "0 59 23 * * ?")
	public void start() {
		try {
			Date stateDatetime = new Date();
			Integer times = deviceReportManager.selectReportTimesByDay(DateFormatUtils.format(stateDatetime, DateFormatConstants.yyyy_MM_dd));
			ScanReportDO scanReportDO = new ScanReportDO();
			scanReportDO.setReportTimes(times);
			scanReportDO.setStateDatetime(stateDatetime);
			scanReportService.insertScanReport(scanReportDO);
			System.out.println("ScanReportJob " + new Date());
		} catch (Exception e) {
			log.error("ScanReportJob {}", e);
		}
	}
}
