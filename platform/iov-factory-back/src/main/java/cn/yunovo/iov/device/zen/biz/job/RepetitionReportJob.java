package cn.yunovo.iov.device.zen.biz.job;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import cn.yunovo.iov.device.zen.biz.report.report.manager.DeviceReportManager;
import cn.yunovo.iov.device.zen.biz.statistics.repetition.model.RepetitionReportDTO;
import cn.yunovo.iov.device.zen.biz.statistics.repetition.service.RepetitionReportService;
import lombok.extern.slf4j.Slf4j;

@Component
@EnableScheduling
@Slf4j
public class RepetitionReportJob {

	@Autowired
	private DeviceReportManager deviceReportManager;

	@Autowired
	private RepetitionReportService repetitionReportService;

	@Scheduled(cron = "0 30 23 * * ?")
	public void start() {
		try {
			List<RepetitionReportDTO> iccidList = deviceReportManager.selectIccidReportTimes();
			List<RepetitionReportDTO> snList = deviceReportManager.selectSnReportTimes();
			List<RepetitionReportDTO> imeiList = deviceReportManager.selectImeiReportTimes();
			repetitionReportService.stateRepetitionReport(iccidList, snList, imeiList);
		} catch (Exception e) {
			log.error("RepetitionReportJob {}", e);
		}
	}
}
