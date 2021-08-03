package com.shintaro.genericAPI.entitys.user;

import java.util.Optional;

import com.shintaro.genericAPI.generic.Repository;

@org.springframework.stereotype.Repository
public interface UserRepository extends Repository<UserEntity> {
	
	Optional<UserEntity> findByUsername(String username);
	
}
