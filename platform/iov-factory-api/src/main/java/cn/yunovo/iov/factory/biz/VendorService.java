package cn.yunovo.iov.factory.biz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.github.ore.boot.context.SpringContext;
import com.github.ore.framework.web.api.ResultEntity;
import com.google.gson.Gson;

import cn.yunovo.iov.factory.biz.device.model.Device;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class VendorService {

	@NacosValue(value = "${app.device.service}", autoRefreshed = true)
	private String url;

	@NacosValue(value = "${app.cdp.service}", autoRefreshed = true)
	private String cdpUrl;

	@NacosValue(value = "${app.ding.url}", autoRefreshed = true)
	private String dingUrl;

	@NacosValue(value = "${app.ding.close}", autoRefreshed = true)
	private Boolean dingClose = true;

	private static int timeout = 60 * 1000;

	@Autowired
	private RestTemplate restTemplate;

	@LoadBalanced
	@Bean
	public RestTemplate restTemplate() {
		SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
		requestFactory.setConnectTimeout(timeout);
		requestFactory.setReadTimeout(timeout);
		return new RestTemplate(requestFactory);
	}

	/**
	 * 设备变更(ICCID 变更)
	 * 
	 * @param sn
	 * @param iccid
	 * @return
	 */
	public static String infoUpdate(String sn, String iccid, String imei) {
		log.info("/device/infoUpdate request sn:{},iccid:{}", sn, iccid);
		try {
			HttpHeaders headers = new HttpHeaders();
			MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
			headers.setContentType(type);
			headers.add("Accept", MediaType.APPLICATION_JSON.toString());
			VendorService vendorService = SpringContext.getBean(VendorService.class);
			String result = vendorService.restTemplate.getForEntity(vendorService.url + "/device/infoUpdate?sn=" + sn + "&iccid=" + iccid + "&imei=" + imei, String.class).getBody();
			log.info("{}/device/infoUpdate response {}", vendorService.url, result);
			Gson gson = new Gson();
			@SuppressWarnings("unchecked")
			ResultEntity<String> resultEntity = gson.fromJson(result, ResultEntity.class);
			if (!resultEntity.getCode().equals("0")) {
				VendorService.sendDing("根据SN修改ICCID发生错误", "输入参数：SN[" + sn + "],ICCID[" + iccid + "],设备中心返回信息:[" + resultEntity.getMsg() + "]");
			}

			return result;
		} catch (Exception e) {
			VendorService.sendDing("根据SN修改ICCID发生异常", "输入参数：SN[" + sn + "],ICCID[" + iccid + "],异常信息:[" + e.getMessage() + "]");
			log.error("infoUpdate {}", e);
			Gson gson = new Gson();
			return gson.toJson(ResultEntity.data(null).code("1").msg(e.getMessage()));
		}
	}

	/**
	 * 设备SN变更
	 * 
	 * @param sn
	 * @param iccid
	 * @return
	 */
	public static String snUpdate(String sn, String iccid, String customSN) {
		log.info("/device/snUpdate request sn:{},iccid{}", sn, iccid);
		try {
			HttpHeaders headers = new HttpHeaders();
			MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
			headers.setContentType(type);
			headers.add("Accept", MediaType.APPLICATION_JSON.toString());
			VendorService vendorService = SpringContext.getBean(VendorService.class);
			String result = vendorService.restTemplate.getForEntity(vendorService.url + "/device/snUpdate?sn=" + sn + "&iccid=" + iccid + "&type=" + customSN, String.class).getBody();
			log.info("{}/device/snUpdate response {}", vendorService.url, result);
			Gson gson = new Gson();
			@SuppressWarnings("unchecked")
			ResultEntity<String> resultEntity = gson.fromJson(result, ResultEntity.class);
			if (!resultEntity.getCode().equals("0")) {
				VendorService.sendDing("根据ICCID修改SN发生错误", "输入参数：SN[" + sn + "],ICCID[" + iccid + "],设备中心返回信息:[" + resultEntity.getMsg() + "]");
			}
			return result;
		} catch (Exception e) {
			VendorService.sendDing("根据ICCID修改SN发生异常", "输入参数：SN[" + sn + "],ICCID[" + iccid + "],异常信息:[" + e.getMessage() + "]");
			log.error("snUpdate {}", e);
			Gson gson = new Gson();
			return gson.toJson(ResultEntity.data(null).code("1").msg(e.getMessage()));
		}
	}

	/**
	 * ICCID卡机构变更
	 * 
	 * @param org
	 * @param proName
	 * @param iccid
	 * @return
	 */
	public static String orgUpdate(String org, String proName, String iccid, String imei, String sn) {
		log.info("/device/orgUpdate request org:{},iccid{}", org, iccid);
		try {
			HttpHeaders headers = new HttpHeaders();
			MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
			headers.setContentType(type);
			headers.add("Accept", MediaType.APPLICATION_JSON.toString());
			VendorService vendorService = SpringContext.getBean(VendorService.class);
			String result = vendorService.restTemplate.getForEntity(vendorService.url + "/iccid/orgUpdate?org=" + org + "&pro_name=" + proName + "&iccid=" + iccid + "&imei=" + imei + "&sn=" + sn, String.class).getBody();
			log.info("{}/iccid/orgUpdate {}", vendorService.url, result);
			Gson gson = new Gson();
			@SuppressWarnings("unchecked")
			ResultEntity<String> resultEntity = gson.fromJson(result, ResultEntity.class);
			if (!resultEntity.getCode().equals("0")) {
				VendorService.sendDing("ICCID卡机构变更发生错误", "输入参数：org[" + org + "],proName[" + proName + "],iccid[" + iccid + "],设备中心返回信息:[" + resultEntity.getMsg() + "]");
			}
			return result;
		} catch (Exception e) {
			VendorService.sendDing("ICCID卡机构变更发生异常", "输入参数：org[" + org + "],proName[" + proName + "],异常信息:[" + e.getMessage() + "]");
			log.error("orgUpdate {}", e);
			Gson gson = new Gson();
			return gson.toJson(ResultEntity.data(null).code("1").msg(e.getMessage()));
		}
	}

	/**
	 * 更新设备信息
	 * 
	 * @param org
	 * @param proName
	 * @param iccid
	 * @return
	 */
	public static String updateDevice(Device device) {
		log.info("updateDevice /dcs/devices request body {}", device);
		try {
			Gson gson = new Gson();
			HttpHeaders headers = new HttpHeaders();
			MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
			headers.setContentType(type);
			headers.add("Accept", MediaType.APPLICATION_JSON.toString());
			VendorService vendorService = SpringContext.getBean(VendorService.class);
			HttpEntity<String> formEntity = new HttpEntity<String>(gson.toJson(device), headers);
			vendorService.restTemplate.put(vendorService.url + "/devices", formEntity);
			log.info("{}/dcs/devices {}", vendorService.url);
			return gson.toJson(ResultEntity.data(null).code("0"));
		} catch (Exception e) {
			VendorService.sendDing("更新设备信息异常", "输入参数：device[" + device + "],异常信息:[" + e.getMessage() + "]");
			log.error("updateDevice {}", e);
			Gson gson = new Gson();
			return gson.toJson(ResultEntity.data(null).code("1").msg(e.getMessage()));
		}
	}
	
	/**
	 * 更新设备信息
	 * 
	 * @param org
	 * @param proName
	 * @param iccid
	 * @return
	 */
	public static ResultEntity<String> queryDevice(String sn) {
		log.info("queryDevice /dcs/devices request sn{}", sn);
		try {
			Gson gson = new Gson();
			HttpHeaders headers = new HttpHeaders();
			MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
			headers.setContentType(type);
			headers.add("Accept", MediaType.APPLICATION_JSON.toString());
			VendorService vendorService = SpringContext.getBean(VendorService.class);
			String result = vendorService.restTemplate.getForEntity(vendorService.url + "/devices?sn=" + sn+"&noCache=false", String.class).getBody();
			log.info("{}/dcs/devices {}", vendorService.url, result);
			@SuppressWarnings("unchecked")
			ResultEntity<String> resultEntity = gson.fromJson(result, ResultEntity.class);
			return resultEntity;
		} catch (Exception e) {
			VendorService.sendDing("查询设备信息异常", "输入参数：sn[" + sn + "],异常信息:[" + e.getMessage() + "]");
			log.error("queryDevice {}", e);
			return null;
		}
	}

	/**
	 * ICCID卡机构变更
	 * 
	 * @param org
	 * @param proName
	 * @param iccid
	 * @return
	 */
	public static void sendDing(String title, String text) {
		VendorService vendorService = SpringContext.getBean(VendorService.class);
		if (!vendorService.dingClose) {
			log.info("/rest/api/ding/sendDing {},{}", title, text);
			try {
				SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
				requestFactory.setConnectTimeout(10 * 1000);
				requestFactory.setReadTimeout(10 * 1000);
				RestTemplate restTemplate = new RestTemplate(requestFactory);
				HttpHeaders headers = new HttpHeaders();
				MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
				headers.setContentType(type);
				headers.add("Accept", MediaType.APPLICATION_JSON.toString());

				String result = restTemplate.getForEntity(vendorService.cdpUrl + "/rest/api/ding/sendDing?dingUrl=" + vendorService.dingUrl + "&title=" + title + "&text=[iov-device-zen]" + title + "--->" + text, String.class).getBody();
				log.info("{}/rest/api/ding/sendDing {}", vendorService.dingUrl, result);
			} catch (Exception e) {
				log.error("sendDing {}", e);
			}
		}
	}

}
