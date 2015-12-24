package com.model;

public class Attachment {


	private Long id ;
	private Long email_id;
	private byte[] content ;
	private String fileName;
	private String ext ;
	public String getExt() {
		return ext;
	}
	public void setExt(String ext) {
		this.ext = ext;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getEmail_id() {
		return email_id;
	}
	public void setEmail_id(Long email_id) {
		this.email_id = email_id;
	}
	public byte[] getContent() {
		return content;
	}
	public void setContent(byte[] content) {
		this.content = content;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
}
