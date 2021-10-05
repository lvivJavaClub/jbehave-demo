package ua.lviv.javaclub.jbehave.jbehavedemo.user;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
	User findAllByUsername(String name);

}
