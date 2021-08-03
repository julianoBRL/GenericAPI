package com.shintaro.genericAPI.entitys.user;

public enum UserType {
	DEFAULT("default"),
	ADMIN("admin");
	
	String definition;
	
	UserType(String definition){
		this.definition = definition;
	}
	
	@Override
	public String toString() {
		return definition;
	}
	
}
