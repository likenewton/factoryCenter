package cn.yunovo.iov.factory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import com.github.ore.boot.bootstrap.starter.AbsBootStarter;

@SpringBootApplication
@EnableAsync
@NacosPropertySource(dataId = "${nacos.config.data-id}", groupId = "${nacos.config.group}", autoRefreshed = true)
public class BootStarter extends AbsBootStarter {

	@Override
	public void run(String[] args) {
		SpringApplication.run(BootStarter.class, args);
	}

	public static void main(String[] args) {
		BootStarter starter = new BootStarter();
		starter.run(args);
	}

}
