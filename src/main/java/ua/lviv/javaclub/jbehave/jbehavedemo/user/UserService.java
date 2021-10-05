package ua.lviv.javaclub.jbehave.jbehavedemo.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
	private final UserRepository userRepository;

	public User create(User user) {
		return userRepository.save(user);
	}

	public Optional<User> getUserInfo(Long id) {
		return userRepository.findById(id);
	}
}
