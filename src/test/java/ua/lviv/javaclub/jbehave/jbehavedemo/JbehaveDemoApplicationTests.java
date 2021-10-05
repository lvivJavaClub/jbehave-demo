package ua.lviv.javaclub.jbehave.jbehavedemo;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import static org.junit.Assert.assertNotNull;

public class JbehaveDemoApplicationTests extends BaseIntegrationTest {

	@Autowired
	private ApplicationContext applicationContext;

	@Test
	public void contextLoads() {
		assertNotNull(applicationContext);
	}
}
