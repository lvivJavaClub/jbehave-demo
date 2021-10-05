package ua.lviv.javaclub.jbehave.jbehavedemo.jbehave;

import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.io.CodeLocations;
import org.jbehave.core.io.LoadFromClasspath;
import org.jbehave.core.io.StoryFinder;
import org.jbehave.core.junit.JUnitStories;
import org.jbehave.core.parsers.RegexPrefixCapturingPatternParser;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.InstanceStepsFactory;
import org.jbehave.core.steps.ParameterConverters;
import org.jbehave.core.steps.PrintStreamStepMonitor;
import org.junit.ClassRule;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.TestPropertySourceUtils;
import org.testcontainers.containers.MySQLContainer;
import ua.lviv.javaclub.jbehave.jbehavedemo.DatasourceContainer;
import ua.lviv.javaclub.jbehave.jbehavedemo.jbehave.user.UserConvertor;
import ua.lviv.javaclub.jbehave.jbehavedemo.user.UserRepository;
import ua.lviv.javaclub.jbehave.jbehavedemo.user.UserService;

import java.util.ArrayList;
import java.util.List;

import static org.jbehave.core.io.CodeLocations.codeLocationFromClass;
import static org.jbehave.core.reporters.Format.CONSOLE;
import static org.jbehave.core.reporters.Format.HTML;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = DEFINED_PORT)
@ActiveProfiles(profiles = "test")
@ContextConfiguration(initializers = RunFeaturesTest.DockerPostgreDataSourceInitializer.class)
public class RunFeaturesTest extends JUnitStories {

	@ClassRule
	public static MySQLContainer<DatasourceContainer> mysqlContainer = DatasourceContainer.getInstance();

	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	private UserRepository userRepository;

	//@Test from TestNG
	public void tngRun() {
		run();
	}

	@Override
	public Configuration configuration() {
		return new MostUsefulConfiguration()
				.useStoryLoader(new LoadFromClasspath(this.getClass()))
				.useStepPatternParser(new RegexPrefixCapturingPatternParser("$"))
				.useStepMonitor(new PrintStreamStepMonitor())
				.useParameterConverters(
						new ParameterConverters().addConverters(customConverters(userRepository)))
				.useStoryReporterBuilder(new StoryReporterBuilder()
						.withCodeLocation(codeLocationFromClass(this.getClass()))
						.withFormats(CONSOLE, HTML));
	}

	private ParameterConverters.ParameterConverter[] customConverters(UserRepository userRepository) {
		List<ParameterConverters.ParameterConverter> converters = new ArrayList<>();
		converters.add(new UserConvertor(userRepository));
		return converters.toArray(new ParameterConverters.ParameterConverter[converters.size()]);
	}

	@Override
	public InjectableStepsFactory stepsFactory() {
		return new InstanceStepsFactory(configuration(), new UserSteps(applicationContext));
	}

	@Override
	protected List<String> storyPaths() {
		return new StoryFinder().findPaths(CodeLocations.codeLocationFromClass(this.getClass()), "**/*.story", "**/excluded*.story");
	}

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