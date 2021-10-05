package ua.lviv.javaclub.jbehave.jbehavedemo;

import org.junit.ClassRule;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.TestPropertySourceUtils;
import org.testcontainers.containers.MySQLContainer;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = DEFINED_PORT)
@ActiveProfiles(profiles = "test")
@ContextConfiguration(initializers = BaseIntegrationTest.DockerPostgreDataSourceInitializer.class)
public abstract class BaseIntegrationTest {

	@ClassRule
	public static MySQLContainer<DatasourceContainer> mysqlContainer = DatasourceContainer.getInstance();

	public static class DockerPostgreDataSourceInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

		@Override
		public void initialize(ConfigurableApplicationContext applicationContext) {
			TestPropertySourceUtils.addInlinedPropertiesToEnvironment(
					applicationContext,
					"spring.datasource.url=" + mysqlContainer.getJdbcUrl(),
					"spring.datasource.username=" + mysqlContainer.getUsername(),
					"spring.datasource.password=" + mysqlContainer.getPassword()
			);
		}
	}
}
