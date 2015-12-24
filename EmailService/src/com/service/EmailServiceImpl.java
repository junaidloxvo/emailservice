package com.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import com.model.Attachment;
import com.model.Email;

@Service
public class EmailServiceImpl implements EmailService {

	@Autowired
	JdbcTemplate jdbcTemplate ;
	
	@Override
	public int saveEmail(final Email email) {
		// TODO Auto-generated method stub
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		
	final	String sql = "insert into emails (comp_id ,topic ,content_html ,recipients,sended,sended_date) values (?,?,?,?,?,?)   ";
		/*Object[] params = new Object[] { email.getComp_id(), email.getTopic(), email.getContent_html(), email.getRecipients() ,email.getSended() , new Date() };
		int row = jdbcTemplate.update(sql, params);*/
		
	//	final String INSERT_SQL = "insert into my_test (name) values(?)";
		//final String name = "Rob";
		
		jdbcTemplate.update(
		    new PreparedStatementCreator() {
		        public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
		            PreparedStatement ps =
		                connection.prepareStatement(sql, new String[] {"id"});
		            ps.setLong(1, email.getComp_id());
		            ps.setString(2,email.getTopic() );
		            ps.setString(3,email.getContent_html() );
		            ps.setString(4,email.getRecipients() );
		            ps.setBoolean(5, email.getSended());
		            ps.setDate(6, new java.sql.Date(new Date().getTime()));
		            return ps;
		        }
		    },
		    keyHolder);
		
	return	keyHolder.getKey().intValue();
		
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

	@Override
	public void saveAttachment(Attachment attachment) {
		
		String sql = "insert into attachments (email_id ,content  ,fileName,ext) values (?,?,?,?)   ";
		Object[] params = new Object[] { attachment.getEmail_id(),attachment.getContent(), attachment.getFileName(),"."+attachment.getExt() };
		int row = jdbcTemplate.update(sql, params);
		
	}

	@Override
	public List<Attachment> getAttachmentsById(Long id) {
		// TODO Auto-generated method stub

		
		List<Attachment> attachments = null;
		try {
			String sql = "SELECT * FROM attachments WHERE  `email_id` = '"+id+"' ";
			attachments = jdbcTemplate.query(sql, new RowMapper<Attachment>() {
				@Override
				public Attachment mapRow(ResultSet rs, int rowNum) throws SQLException {
					Attachment attachment = new Attachment();
					
					attachment.setContent(rs.getBytes("content"));
					attachment.setFileName(rs.getString("fileName"));
					attachment.setExt(rs.getString("ext"));
					return attachment;
				}

			});
		} catch (Exception e) {
			return null;
		}
		;
		return attachments;
	
	}

}
