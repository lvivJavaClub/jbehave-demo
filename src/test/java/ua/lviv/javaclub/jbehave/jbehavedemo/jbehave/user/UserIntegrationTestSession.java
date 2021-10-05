package ua.lviv.javaclub.jbehave.jbehavedemo.jbehave.user;

import lombok.Getter;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ua.lviv.javaclub.jbehave.jbehavedemo.user.User;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class UserIntegrationTestSession {

	@Getter
	private final User.UserBuilder userBuilder = User.builder();

	public User getUser() {
		return userBuilder.build();
	}

	public void setUser(User user) {
		userBuilder
				.id(user.getId())
				.username(user.getUsername())
				.firstname(user.getFirstname())
				.lastname(user.getLastname());
	}
}