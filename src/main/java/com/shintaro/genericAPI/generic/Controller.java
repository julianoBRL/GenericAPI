package com.shintaro.genericAPI.generic;

/*
 * By: Juliano Lira(ShintaroBRL) 
 * 
 */

import java.lang.reflect.Field;
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
import org.springframework.web.bind.annotation.ResponseStatus;

public interface Controller<
	entity extends Object,
	repository extends Repository<entity>,
	service extends Service<entity,repository>
> {

	service getService();
	
	@GetMapping
	default ResponseEntity<?> getAll() {
		return ResponseEntity.status(HttpStatus.OK).body(getService().list());
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
	
	@GetMapping("/filter")
	default ResponseEntity<?> filter(@RequestParam Map<String, String> params){
		
		boolean reverseFilter = (params.containsKey("reverse"))? params.get("reverse") != null : false ;
		
		List<entity> dataList = getService().list();
		
		params.forEach((key, value) -> {
			dataList.removeIf(data -> {
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
		
		return ResponseEntity.status(HttpStatus.OK).body(dataList);
	}

}