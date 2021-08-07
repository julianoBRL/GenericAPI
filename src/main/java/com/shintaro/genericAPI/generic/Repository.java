package com.shintaro.genericAPI.generic;

/*
 * By: Juliano Lira(ShintaroBRL) 
 * 
 */

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface Repository<T extends Object> extends JpaRepository<T, UUID>, JpaSpecificationExecutor<T> {

}
