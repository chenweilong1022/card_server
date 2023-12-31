package io.renren;

import io.renren.service.SysGeneratorService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RenrenApplicationTests {

	@Autowired
	private SysGeneratorService sysGeneratorService;

	@Test
	public void contextLoads() {
		sysGeneratorService.generatorCode(new String[]{
				"cd_project_statistics",
		});
	}

	@Test
	public void contextLoadsUpdate() {
		sysGeneratorService.generatorCodeUpdate(new String[]{
				"cd_card_lock",
				"cd_project",
		});
	}

}
