package com.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.model.Email;

@Service
public class EmailServiceImpl implements EmailService {

	@Autowired
	JdbcTemplate jdbcTemplate ;
	
	@Override
	public void saveEmail(Email email) {
		// TODO Auto-generated method stub
		String sql = "insert into emails (comp_id ,topic ,content_html ,recipients) values (?,?,?,?)   ";
		Object[] params = new Object[] { email.getComp_id(), email.getTopic(), email.getContent_html(), email.getRecipients() };
		int row = jdbcTemplate.update(sql, params);
		
		
	}

}
