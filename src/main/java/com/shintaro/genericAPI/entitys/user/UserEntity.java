package com.shintaro.genericAPI.entitys.user;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.shintaro.genericAPI.generic.Entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@javax.persistence.Entity
@Table(name = "Users")
public class UserEntity extends Entity{
	
	@NotNull
	private String username;
	
	@NotNull
	@JsonProperty(access = Access.WRITE_ONLY)
	private String password;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private UserType type;
	
}
