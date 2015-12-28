package com.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Properties {


	@Value("${smtp.username}")
	private String smtpUsername;

	@Value("${smtp.password}")
	private String smtpPassword;

	@Value("${smtp.port}")
	private String smtpPort;

	@Value("${smtp.host}")
	private String smtpHost;
	
	@Value("${smtp.auth}")
	private String smtpAuth;
	
	@Value("${smtp.socketFactory.port}")
	private String smtpSocketFactoryPort;
	
	@Value("${smtp.socketFactory.class}")
	private String smtpSocketFactoryClass;


	public String getSmtpAuth() {
		return smtpAuth;
	}

	public void setSmtpAuth(String smtpAuth) {
		this.smtpAuth = smtpAuth;
	}

	public String getSmtpSocketFactoryPort() {
		return smtpSocketFactoryPort;
	}

	public void setSmtpSocketFactoryPort(String smtpSocketFactoryPort) {
		this.smtpSocketFactoryPort = smtpSocketFactoryPort;
	}

	public String getSmtpSocketFactoryClass() {
		return smtpSocketFactoryClass;
	}

	public void setSmtpSocketFactoryClass(String smtpSocketFactoryClass) {
		this.smtpSocketFactoryClass = smtpSocketFactoryClass;
	}

	public String getSmtpPassword() {
		return smtpPassword;
	}

	public void setSmtpPassword(String smtpPassword) {
		this.smtpPassword = smtpPassword;
	}

	public String getSmtpPort() {
		return smtpPort;
	}

	public void setSmtpPort(String smtpPort) {
		this.smtpPort = smtpPort;
	}

	public String getSmtpHost() {
		return smtpHost;
	}

	public void setSmtpHost(String smtpHost) {
		this.smtpHost = smtpHost;
	}

	public String getSmtpUsername() {
		return smtpUsername;
	}

	public void setSmtpUsername(String smtpUsername) {
		this.smtpUsername = smtpUsername;
	}


}
