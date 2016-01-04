package org.kaapi.app.entities;

import org.springframework.security.core.GrantedAuthority;

public class Role implements GrantedAuthority{


	private static final long serialVersionUID = 1L;
	
	private String id;
	private String name;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	@Override
	public String getAuthority() {
		return name;
	}
		
}

