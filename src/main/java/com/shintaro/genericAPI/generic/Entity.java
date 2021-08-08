package com.shintaro.genericAPI.generic;

/*
 * By: Juliano Lira(ShintaroBRL) 
 * 
 */

import java.util.UUID;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
public class Entity {

	@Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
	@Type(type="org.hibernate.type.UUIDCharType")
	@Getter
	@Setter
    public UUID id;
	
	@Getter
	@Setter
	@NotNull
	private boolean active;

}
