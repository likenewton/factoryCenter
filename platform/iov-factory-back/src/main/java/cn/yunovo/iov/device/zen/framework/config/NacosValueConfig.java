package cn.yunovo.iov.device.zen.framework.config;

import org.springframework.stereotype.Component;

import com.alibaba.nacos.api.config.annotation.NacosValue;

@Component
public class NacosValueConfig {
	
	@NacosValue(value = "${app.user.center-service-url}", autoRefreshed = true)
	public String usercenterServiceUrl;
	
	@NacosValue(value = "${app.user.statistics-able}", autoRefreshed = true)
	public Boolean statisticsAble;
	
	@NacosValue(value = "${iov.app.org-codes}", autoRefreshed = true)
	public String orgCodes;
	
	

}
