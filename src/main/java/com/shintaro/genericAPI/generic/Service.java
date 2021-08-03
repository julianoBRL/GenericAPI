package com.shintaro.genericAPI.generic;

import java.util.List;
import java.util.UUID;

public class Service<entity extends Object ,repository extends Repository<entity>> {
	
	private repository repository;
	
	protected Service(repository repository) {
		this.repository = repository;
	}

	public List<entity> list() {
		return repository.findAll();
	}

	public entity getByID(UUID iD){
		System.out.println(repository.findById(iD).orElseThrow(() -> new IllegalArgumentException(String.format("missing user:%s", iD))));
		return repository.findById(iD).get();
	}

	public entity save(entity model) {
		return repository.saveAndFlush(model);
	}
	

}
