package com.shintaro.genericAPI.generic;

import java.lang.reflect.Field;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

public interface CachedController<
	entity extends Object,
	repository extends Repository<entity>,
	service extends Service<entity,repository>,
	singletron extends Singletron<entity>
> {
	
	service getService();
	singletron getSingletron();
	
	@GetMapping
	default ResponseEntity<?> getAll() {
		verifyCache();
		return ResponseEntity.status(HttpStatus.OK).body(getSingletron().getCache());
	}
	
	@GetMapping("/{id}")
	default ResponseEntity<?> getByID(@PathVariable("id") UUID ID) throws Exception {
		verifyCache();
		return ResponseEntity.status(HttpStatus.OK).body(getSingletron().getCache().stream().filter(data -> {
			try {
				Field idField = data.getClass().getSuperclass().getDeclaredField("id");
				idField.setAccessible(true);
				return idField.get(data).equals(ID);
			} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
			return false;
		}));
	}
	
	@PostMapping
	default ResponseEntity<?> register(@RequestBody entity model){
		updateCache();
		return ResponseEntity.status(HttpStatus.OK).body(getService().save(model));
	}
	
	@DeleteMapping("/{id}")
	default ResponseEntity<?> delete(@PathVariable("id") UUID ID){
		updateCache();
		getService().delete(ID);
		return ResponseEntity.status(HttpStatus.OK).body(getSingletron().getCache());
	}
	
	@PatchMapping("/{id}")
	default ResponseEntity<?> edit(@PathVariable("id") UUID ID, @RequestBody entity model){
		updateCache();
		return ResponseEntity.status(HttpStatus.OK).body(getService().edit(ID,model));
	}
	
	default void updateCache() {
		if(getSingletron().getCache().isEmpty()) {
			verifyCache();
		}
		getSingletron().setCache(getService().list());
	}
	
	default void verifyCache() {
		if(getSingletron().getCache().isEmpty()) {
			getSingletron().setCache(getService().list());
		}
	}

}