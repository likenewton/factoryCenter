package cn.yunovo.iov.factory.framework.dac.metadata;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "iov.dac")
public class DacProperties {
	
	private String master;
	
	private Integer userType;
	
	private List<String> dataProvider;

}
