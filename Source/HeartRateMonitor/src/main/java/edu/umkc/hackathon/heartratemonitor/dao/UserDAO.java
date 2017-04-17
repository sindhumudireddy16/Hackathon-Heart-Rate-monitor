package edu.umkc.hackathon.heartratemonitor.dao;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import edu.umkc.hackathon.heartratemonitor.model.User;

@Repository
@Transactional
public class UserDAO extends JdbcDaoSupport{
	
	@Autowired
	public UserDAO(DataSource datasource){
		this.setDataSource(datasource);
	}
	
	public User findUser(User user){
		
		return user;
	}
	
	public int createUser(User user){
		return -1;
	}
}
