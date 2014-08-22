package org.jwp.dao;

import org.apache.ibatis.session.SqlSession;
import org.jwp.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MyBatisUserDao implements UserDao {
	private static final Logger log = LoggerFactory.getLogger(MyBatisUserDao.class);
	
	@Autowired
	private SqlSession sqlSession;
	
	
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	
	@Override
	public User findById(String userId) {
		return sqlSession.selectOne("UserMapper.findById", userId);
	}

	@Override
	public void create(User user) {
		sqlSession.selectOne("UserMapper.create", user);
	}

	@Override
	public void update(User user) {
		sqlSession.selectOne("UserMapper.update", user);

	}

}
