package cn.yunovo.iov.device.zen.framework.dac;

import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.github.pagehelper.autoconfigure.PageHelperAutoConfiguration;

import cn.yunovo.iov.device.zen.framework.dac.interceptor.DacQueryInterceptor;
import cn.yunovo.iov.device.zen.framework.dac.interceptor.DacUpdateInterceptor;
import cn.yunovo.iov.device.zen.framework.dac.metadata.DacProperties;

@Configuration
@AutoConfigureAfter(MybatisAutoConfiguration.class)
@AutoConfigureBefore(PageHelperAutoConfiguration.class)
@EnableConfigurationProperties({ DacProperties.class })
public class DataAccessControlAutoConfiguration {

	@Autowired
	private List<SqlSessionFactory> sqlSessionFactoryList;

	@Autowired
	private DacProperties dacProperties;

	@PostConstruct
	public void addADCHelperInterceptor() {
		DacQueryInterceptor queryInterceptor = new DacQueryInterceptor();
		if (null != dacProperties.getDataProvider()) {
			queryInterceptor.setDacProperties(dacProperties);
			queryInterceptor.setProperties(null);
			DacUpdateInterceptor updateInterceptor = new DacUpdateInterceptor();
			updateInterceptor.setDacProperties(dacProperties);
			updateInterceptor.setProperties(null);
			for (SqlSessionFactory sqlSessionFactory : sqlSessionFactoryList) {
				sqlSessionFactory.getConfiguration().getInterceptors();
				sqlSessionFactory.getConfiguration().addInterceptor(queryInterceptor);
				sqlSessionFactory.getConfiguration().addInterceptor(updateInterceptor);
			}
		}

	}
}
