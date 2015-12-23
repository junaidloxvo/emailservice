package com.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.model.Email;

@Service
public class EmailServiceImpl implements EmailService {

	@Autowired
	JdbcTemplate jdbcTemplate ;
	
	@Override
	public void saveEmail(Email email) {
		// TODO Auto-generated method stub
		String sql = "insert into emails (comp_id ,topic ,content_html ,recipients,sended,sended_date) values (?,?,?,?,?,?)   ";
		Object[] params = new Object[] { email.getComp_id(), email.getTopic(), email.getContent_html(), email.getRecipients() ,email.getSended() , new Date() };
		int row = jdbcTemplate.update(sql, params);
		
		
	}
	
	public List<Email> getPendingEmails() {
	
		List<Email> emails = null;
		try {
			String sql = "SELECT * FROM emails WHERE  `sended` = '0' ";
			emails = jdbcTemplate.query(sql, new RowMapper<Email>() {
				@Override
				public Email mapRow(ResultSet rs, int rowNum) throws SQLException {
					Email email = new Email();
					email.setId(rs.getLong("id"));
					email.setContent_html(rs.getString("content_html"));
					email.setComp_id(rs.getLong("comp_id"));
					email.setRecipients(rs.getString("recipients"));
					email.setTopic(rs.getString("topic"));
					return email;
				}

			});
		} catch (Exception e) {
			return null;
		}
		;
		return emails;
	}

	@Override
	public void updateEmail(Email email) {
		
		int row = jdbcTemplate.update(
                "update `emails`  set sended   = ? , sended_date = ? where id = ?",  true,new Date(), email.getId());
		
	}

	@Override
	public Email getEmailByid(Long id) {
		Email email = null;
		try {
			String sql = "SELECT * FROM emails WHERE id = ?  ";
			email = (Email) jdbcTemplate.queryForObject(
					sql, new Object[] { id },
					new BeanPropertyRowMapper(Email.class));
		} catch (Exception e) {
			return null;
		}
	
		return email;
	}

}
