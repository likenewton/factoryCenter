package cn.yunovo.iov.device.zen.framework;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.remoting.caucho.HessianProxyFactoryBean;
import org.springframework.stereotype.Component;
import org.sunshine.dcda.view.system.viewcomponent.ICooperateOrganViewComponent;
import org.sunshine.dcda.view.system.viewcomponent.ISystemAreaViewComponent;

import cn.yunovo.iov.device.zen.framework.config.NacosValueConfig;

@Component
public class VendorServiceConfig {

	@Autowired
	private NacosValueConfig nacosValueConfig;

	@Bean(name = "cooperateOrganViewComponent")
	public HessianProxyFactoryBean getCooperateOrganViewComponent() {
		HessianProxyFactoryBean hessianProxyFactoryBean = new HessianProxyFactoryBean();
		hessianProxyFactoryBean.setServiceUrl(nacosValueConfig.usercenterServiceUrl + "/remote/cooperateOrganViewComponent");
		hessianProxyFactoryBean.setServiceInterface(ICooperateOrganViewComponent.class);
		return hessianProxyFactoryBean;
	}
	
	@Bean(name = "systemAreaViewComponent")
	  public HessianProxyFactoryBean getSystemAreaViewComponent() {
	    HessianProxyFactoryBean hessianProxyFactoryBean = new HessianProxyFactoryBean();
	    hessianProxyFactoryBean.setServiceUrl(nacosValueConfig.usercenterServiceUrl+"/remote/systemAreaViewComponent");
	    hessianProxyFactoryBean.setServiceInterface(ISystemAreaViewComponent.class);
	    return hessianProxyFactoryBean;
	  }
}
