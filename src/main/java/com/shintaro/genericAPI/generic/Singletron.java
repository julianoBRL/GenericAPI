package com.shintaro.genericAPI.generic;

import org.springframework.data.domain.PageImpl;

import lombok.Getter;
import lombok.Setter;

public class Singletron<T extends Object> {
	
	private static Singletron<?> singletron;
	
	@Getter
	@Setter
	private PageImpl<T> cache = new PageImpl<T>(null);
	
	public static <T> Singletron<?> getInstance() {
		if(singletron == null) {
			singletron = new Singletron<T>();
		}
		return singletron;
	}
	
	public PageImpl<T> clearCache() {
		cache = new PageImpl<T>(null);
		return cache;
	}

}