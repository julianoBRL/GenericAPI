package com.shintaro.genericAPI.generic;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.BeanUtils;

import com.shintaro.genericAPI.filters.PropertyFilter;

public class Service<entity extends Object ,repository extends Repository<entity>> extends PropertyFilter {
	
	private repository repository;
	
	protected Service(repository repository) {
		this.repository = repository;
	}

	public List<entity> list() {
		return repository.findAll();
	}

	public entity getByID(UUID iD){
		//System.out.println(repository.findById(iD).orElseThrow(() -> new IllegalArgumentException(String.format("missing user:%s", iD))));
		return repository.findById(iD).get();
	}

	public entity save(entity model) {
		return repository.saveAndFlush(model);
	}
	
	public void delete(UUID iD) {
		repository.deleteById(iD);
	}

	public entity edit(UUID iD, entity model) {
		entity savedData = getByID(iD);
		BeanUtils.copyProperties(model, savedData, getNullPropertyNames(model));
		return repository.save(savedData);
	}

}
