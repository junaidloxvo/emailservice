package com.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;



@JsonInclude(Include.NON_NULL)
public class Email {


	private Long comp_id;
	private String 	topic;
	private String content_html;
	private String recipients;
	private String receipents_cc;
	private Boolean sended ;
	private Date sended_date;
	private String last_error ;
	private Date last_send_attempt ;
	private String system_name  ;
	private String send_as_email  ;
	private String send_as_name  ;
	private String created_by_name ;
	private String created_by_initials  ;


	public Long getComp_id() {
		return comp_id;
	}

	public void setComp_id(Long comp_id) {
		this.comp_id = comp_id;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getContent_html() {
		return content_html;
	}

	public void setContent_html(String content_html) {
		this.content_html = content_html;
	}

	public String getRecipients() {
		return recipients;
	}

	public void setRecipients(String recipients) {
		this.recipients = recipients;
	}

	public String getReceipents_cc() {
		return receipents_cc;
	}

	public void setReceipents_cc(String receipents_cc) {
		this.receipents_cc = receipents_cc;
	}

	public Boolean getSended() {
		return sended;
	}

	public void setSended(Boolean sended) {
		this.sended = sended;
	}

	public Date getSended_date() {
		return sended_date;
	}

	public void setSended_date(Date sended_date) {
		this.sended_date = sended_date;
	}

	public String getLast_error() {
		return last_error;
	}

	public void setLast_error(String last_error) {
		this.last_error = last_error;
	}

	public Date getLast_send_attempt() {
		return last_send_attempt;
	}

	public void setLast_send_attempt(Date last_send_attempt) {
		this.last_send_attempt = last_send_attempt;
	}

	public String getSystem_name() {
		return system_name;
	}

	public void setSystem_name(String system_name) {
		this.system_name = system_name;
	}

	public String getSend_as_email() {
		return send_as_email;
	}

	public void setSend_as_email(String send_as_email) {
		this.send_as_email = send_as_email;
	}

	public String getSend_as_name() {
		return send_as_name;
	}

	public void setSend_as_name(String send_as_name) {
		this.send_as_name = send_as_name;
	}

	public String getCreated_by_name() {
		return created_by_name;
	}

	public void setCreated_by_name(String created_by_name) {
		this.created_by_name = created_by_name;
	}

	public String getCreated_by_initials() {
		return created_by_initials;
	}

	public void setCreated_by_initials(String created_by_initials) {
		this.created_by_initials = created_by_initials;
	}

}
