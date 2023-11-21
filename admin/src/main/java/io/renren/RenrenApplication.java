package io.renren;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.modules.ltt.entity.CdDevicesEntity;
import io.renren.modules.ltt.enums.Online;
import io.renren.modules.ltt.service.CdDevicesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.TimeZone;


@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
@EnableScheduling
public class RenrenApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(RenrenApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		TimeZone.setDefault( TimeZone.getTimeZone("UTC"));
		return application.sources(RenrenApplication.class);
	}

}
