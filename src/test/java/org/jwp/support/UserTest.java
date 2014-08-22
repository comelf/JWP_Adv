package org.jwp.support;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.BeforeClass;
import org.junit.Test;
import org.jwp.domain.Authenticate;
import org.jwp.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserTest {
	private static Validator validator;
	private static final Logger log = LoggerFactory
			.getLogger(UserTest.class);
	
	@BeforeClass
	public static void setUp(){
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}
	
	@Test
	public void userIdWhenIsEmpty() {
		User user = new User("","password","name","aaa@aaa.com");
		Set<ConstraintViolation<User>> constrantViolations = validator.validate(user);
		assertThat(constrantViolations.size(), is(2));
		
		for(ConstraintViolation<User> constrantViolation : constrantViolations){
			log.debug("Validation error Message : {}",constrantViolation.getMessage());
		}
	}
	
	//'woo','dddd','가나다','qqqq@qqqq.com'
	@Test
	public void matchPassword() throws Exception {
		String password = "password";
		Authenticate authenticate = new Authenticate("userId",password);
		User user = new User("UserId",password,"name","javajigi@slipp.net");
		
		assertTrue(user.matchPassword(authenticate));
		
		authenticate = new Authenticate("userId", "password2");
		assertFalse(user.matchPassword(authenticate));
		
		
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void updateWhenMisMatchUserId() throws Exception{
		User user = new User("UserId","password","namee","javajigi@slipp.net");
		User updateUser = new User("Id","password","woow","javajigi@slipp.net");
		User updatedUser = user.update(updateUser);
	}
	
	@Test
	public void update() throws Exception{
		User user = new User("UserId","password","namee","javajigi@slipp.net");
		User updateUser = new User("UserId","password","woow","javajigi@slipp.net");
		User updatedUser = user.update(updateUser);
		assertThat(updateUser, is(updatedUser));
	}

}
