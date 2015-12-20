package com.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;



@JsonInclude(Include.NON_NULL)
public class Email {


	private Long comp_id;

	public Long getComp_id() {
		return comp_id;
	}

	public void setComp_id(Long comp_id) {
		this.comp_id = comp_id;
	}
	
}
