package org.jwp.dao;

import org.jwp.domain.User;

public interface UserDao {

	User findById(String userId);

	void create(User user);

	void update(User user);

}