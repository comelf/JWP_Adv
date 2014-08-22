package org.jwp.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.annotation.PostConstruct;

import org.jwp.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

public class JdbcUserDao extends JdbcDaoSupport implements UserDao{
	private static final Logger log = LoggerFactory
			.getLogger(JdbcUserDao.class);
	
	@PostConstruct
	public void initialize(){
		ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
		populator.addScript(new ClassPathResource("JWP.sql"));
		DatabasePopulatorUtils.execute(populator, getDataSource());
		log.info("database initialized success!");
	}

	/* (non-Javadoc)
	 * @see org.jwp.dao.IUserDao#findById(java.lang.String)
	 */
	@Override
	public User findById(String userId) {
		String sql = "SELECT * FROM USERS WHERE userId = ?";
		RowMapper<User> rowMapper = new RowMapper<User>() {

			@Override
			public User mapRow(ResultSet rs, int rowNum) throws SQLException {
				
				return new User(
						rs.getString("userId"),
						rs.getString("password"),
						rs.getString("name"),
						rs.getString("email"));
			}
		};
		
		try {
			return getJdbcTemplate().queryForObject(sql, rowMapper, userId);
		}catch (EmptyResultDataAccessException e){
			return null;
		}	
	}

	/* (non-Javadoc)
	 * @see org.jwp.dao.IUserDao#create(org.jwp.domain.User)
	 */
	@Override
	public void create(User user) {
		String sql = "insert into USERS values(?,?,?,?)";
		getJdbcTemplate().update(sql,user.getUserId(),user.getPassword(),user.getName(),user.getEmail());
		
	}

	/* (non-Javadoc)
	 * @see org.jwp.dao.IUserDao#update(org.jwp.domain.User)
	 */
	@Override
	public void update(User user) {
		String sql = "update USERS set password= ?, name =?, email = ? where userId= ? ";
		getJdbcTemplate().update(sql,user.getPassword(),user.getName(),user.getEmail(), user.getUserId());
		
	}

}
