package com.shintaro.genericAPI.generic;

import java.util.UUID;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

public interface Controller<
	entity extends Object,
	repository extends Repository<entity>,
	service extends Service<entity,repository>
	> {
	
	service getService();
	
	@SuppressWarnings("rawtypes")
	@GetMapping
	default ResponseEntity<?> getAll(@RequestBody(required = false) entity params) {
		Specification spec = new GSpecification<entity>().setSpecs(params);
		return ResponseEntity.status(HttpStatus.OK).body((PageImpl<?>) getService().list(params,spec));
	}
	
	@GetMapping("/{id}")
	default ResponseEntity<?> getByID(@PathVariable("id") UUID ID) throws Exception {
		return ResponseEntity.status(HttpStatus.OK).body(getService().getByID(ID));
	}
	
	@PostMapping
	default ResponseEntity<?> register(@RequestBody entity model){
		return ResponseEntity.status(HttpStatus.OK).body(getService().save(model));
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(code = HttpStatus.OK)
	default void delete(@PathVariable("id") UUID ID){
		getService().delete(ID);
	}
	
	@PatchMapping("/{id}")
	default ResponseEntity<?> edit(@PathVariable("id") UUID ID, @RequestBody entity model){
		return ResponseEntity.status(HttpStatus.OK).body(getService().edit(ID,model));
	}

}