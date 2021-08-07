package com.shintaro.genericAPI.generic;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;


public class Singletron<T extends Object> {
	
	private static Singletron<?> singletron;
	
	@Getter
	@Setter
	private List<T> cache = new ArrayList<>();
	
	public static <T> Singletron<?> getInstance() {
		if(singletron == null) {
			singletron = new Singletron<T>();
		}
		return singletron;
	}
	
	public List<T> clearCache() {
		cache = new ArrayList<>();
		return cache;
	}

}