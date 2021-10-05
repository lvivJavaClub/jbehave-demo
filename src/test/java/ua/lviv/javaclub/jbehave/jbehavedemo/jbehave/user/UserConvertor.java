package ua.lviv.javaclub.jbehave.jbehavedemo.jbehave.user;

import lombok.RequiredArgsConstructor;
import org.jbehave.core.steps.ParameterConverters;
import ua.lviv.javaclub.jbehave.jbehavedemo.user.User;
import ua.lviv.javaclub.jbehave.jbehavedemo.user.UserRepository;

import java.lang.reflect.Type;

@RequiredArgsConstructor
public class UserConvertor extends ParameterConverters.AbstractParameterConverter<User> {

	private final UserRepository userRepository;

	@Override
	public User convertValue(String name, Type type) {
		return userRepository.findAllByUsername(name);
	}
}
