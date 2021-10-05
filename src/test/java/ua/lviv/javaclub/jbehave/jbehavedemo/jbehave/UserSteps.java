package ua.lviv.javaclub.jbehave.jbehavedemo.jbehave;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.RequiredArgsConstructor;
import org.jbehave.core.annotations.Aliases;
import org.jbehave.core.annotations.BeforeScenario;
import org.jbehave.core.annotations.Composite;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Named;
import org.jbehave.core.annotations.Pending;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.junit.jupiter.api.Assertions;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import ua.lviv.javaclub.jbehave.jbehavedemo.jbehave.user.UserIntegrationTestSession;
import ua.lviv.javaclub.jbehave.jbehavedemo.user.User;

@Component
@RequiredArgsConstructor
public class UserSteps {
	private final ApplicationContext applicationContext;

	private UserIntegrationTestSession userIntegrationTestSession;

	@BeforeScenario
	public void beforeScenario() {
		userIntegrationTestSession = applicationContext.getBean(UserIntegrationTestSession.class);
	}

	@Given("New user with login $username")
	@Aliases(values = {
			"New user with login <username>",
			"username is <username>"
	})
	public void givenUsername(@Named("username") String username) {
		userIntegrationTestSession.getUserBuilder().username(username);
	}

	@Given("New user with firstname $firstname")
	@Aliases(values = {
			"New user with firstname <firstname>",
			"firstname is <firstname>"
	})
	public void givenFirstname(@Named("firstname") String firstname) {
		userIntegrationTestSession.getUserBuilder().firstname(firstname);
	}

	@Given("New user with lastname $lastname")
	@Aliases(values = {
			"New user with lastname <lastname>",
			"lastname is <lastname>"
	})
	public void givenLastname(@Named("lastname") String lastname) {
		userIntegrationTestSession.getUserBuilder().lastname(lastname);
	}

	@Given("Create user with login $username firstname $firstname and lastname $lastname")
	@Aliases(values = {
			"Create user with login <username> firstname <firstname> and lastname <lastname>",
	})
	@Composite(steps = {
			"Given username is <username>",
			"Given firstname is <firstname>",
			"Given lastname is <lastname>",
	})
	public void givenUserInfo(@Named("username") String username, @Named("firstname") String firstname,
	                          @Named("lastname") String lastname) {
		userIntegrationTestSession.getUserBuilder()
				.username(username)
				.firstname(firstname)
				.lastname(lastname);
	}

	@When("Get info for user with login $username")
	public void whenGetUser(User user) {
		Response response = RestAssured.given().contentType(ContentType.JSON).log().all().
				when().get("http://localhost:8081/users/{id}", user.getId()).
				andReturn().prettyPeek();

		userIntegrationTestSession.setUser(response.as(User.class));
	}

	@When("Create user")
	public void whenCreateUser() {
		Response response = RestAssured.given().contentType(ContentType.JSON).log().all().
				when().body(userIntegrationTestSession.getUser()).post("http://localhost:8081/users").
				andReturn().prettyPeek();

		userIntegrationTestSession.setUser(response.as(User.class));
	}

	@Then("Check login $username")
	@Aliases(values = {
			"Check login <username>"
	})
	public void thenCheckUsername(@Named("username") String username) {
		Assertions.assertEquals(username, userIntegrationTestSession.getUser().getUsername());
	}

	@Then("Check firstname $firstname")
	@Aliases(values = {
			"Check firstname <firstname>"
	})
	public void thenCheckFirstname(@Named("firstname") String firstname) {
		Assertions.assertEquals(firstname, userIntegrationTestSession.getUser().getFirstname());
	}

	@Then("Check lastname $lastname")
	@Aliases(values = {
			"Check lastname <lastname>"
	})
	public void thenCheckLastname(@Named("lastname") String lastname) {
		Assertions.assertEquals(lastname, userIntegrationTestSession.getUser().getLastname());
	}

	@Then("Log data")
	public void thenLogData(@Named("feature") String feature, @Named("user") String user, @Named("issue") String issue) {
		System.err.println("feature: " + feature + " user: " + user + " issue: " + issue);
	}
}