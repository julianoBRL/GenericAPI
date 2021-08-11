package com.shintaro.genericAPI.generic;

/*
 * By: Juliano Lira(ShintaroBRL) 
 * 
 */

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

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
	
	@DeleteMapping("/cache")
	default ResponseEntity<?> cache() {
		getSingletron().clearCache();
		return ResponseEntity.status(HttpStatus.OK).body(getSingletron().getCache());
	}
	
	@GetMapping("/filter")
	default ResponseEntity<?> filter(@RequestParam Map<String, String> params){
		verifyCache();
		
		boolean reverseFilter = (params.containsKey("reverse"))? params.get("reverse") != null : false ;
		
		List<entity> cachedDupData = new ArrayList<entity>(getSingletron().getCache());
		
		params.forEach((key, value) -> {
				cachedDupData.removeIf(data -> {
					try {
						Field field = data.getClass().getDeclaredField(key);
						field.setAccessible(true);
						
						if(reverseFilter)
							return field.get(data).equals(value);
						
						return !field.get(data).equals(value);
					} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
						return false;
					}
					
				}); 
		});
		
		return ResponseEntity.status(HttpStatus.OK).body(cachedDupData);
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