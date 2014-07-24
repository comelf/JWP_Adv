package org.jwp.support;

import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.jwp.dao.UserDao;
import org.jwp.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/applicationContext.xml")
public class UserDaoTest {
	private static final Logger log = LoggerFactory
			.getLogger(UserDaoTest.class);

	@Autowired
	private UserDao userDao;

	@Test
	public void test() {
		User user = userDao.findById("woo");
		log.debug("User : {}", user);
	}

	@Test
	public void create() throws Exception {
		User user = new User("id", "pw", "name", "e@mail");
		userDao.create(user);
		User actual = userDao.findById(user.getUserId());
		assertThat(actual, is(user));
		
	}
}
