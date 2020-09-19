package dev.ericrybarczyk.jpahibernatedemo;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JpaHibernateDemoApplicationTests {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Test
	void contextLoads() {
		//simple example of adding to console logging
		logger.info("*** Context Loaded Successfully ***");
	}

}
