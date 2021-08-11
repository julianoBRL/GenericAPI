package com.shintaro.genericAPI.generic;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

@SuppressWarnings("serial")
public class GSpecification<entity extends Object> implements Specification<entity> {
	
	private entity criteria;

    public GSpecification<entity> setSpecs(entity criteria) {
        this.criteria = criteria;
        return this;
    }

    @Override
    public Predicate toPredicate(Root<entity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

        final List<Predicate> predicates = new ArrayList<Predicate>();        
        
        if(criteria != null) {
	        for (Field field : criteria.getClass().getDeclaredFields()) {
	    		field.setAccessible(true);
	    		try {
					if(field.get(criteria)!=null) 
					    predicates.add(cb.like( root.get(field.getName()), "%"+field.get(criteria)+"%"));
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
			}
        }
        
        return cb.and(predicates.toArray(new Predicate[predicates.size()]));

    }

}
