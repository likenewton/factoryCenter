package cn.yunovo.iov.factory.framework.dac.metadata;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "iov.dac")
public class DacProperties {
	
	// 平台用户可以看全部数据
	private Integer userType;
	
	private List<String> dataProvider;

}
