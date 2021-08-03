package com.shintaro.genericAPI.generic;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface Controller<
	entity extends Object,
	repository extends Repository<entity>,
	service extends Service<entity,repository>
> {

	service getSetvice();
	
	@GetMapping
	default ResponseEntity<?> listAll() {
		return ResponseEntity.status(HttpStatus.OK).body(getSetvice().list());
	}
	
	@GetMapping("/{id}")
	default ResponseEntity<?> getByID(@PathVariable("id") UUID ID) throws Exception {
		return ResponseEntity.status(HttpStatus.OK).body(getSetvice().getByID(ID));
	}
	
	@PostMapping
	default ResponseEntity<?> save(@RequestBody entity model){
		return ResponseEntity.status(HttpStatus.OK).body(getSetvice().save(model));
	}

}
