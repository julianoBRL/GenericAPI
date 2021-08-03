package com.shintaro.genericAPI.entitys.user;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.shintaro.genericAPI.generic.Service;

@org.springframework.stereotype.Service
public class UserService extends Service<UserEntity, UserRepository>  implements UserDetailsService {
	
	@Autowired
	private UserRepository repository;

	protected UserService(UserRepository repository) {
		super(repository);
		this.repository = repository;
	}
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<UserEntity> usuarioOptional = repository.findByUsername(email);
		UserEntity usuario = usuarioOptional.orElseThrow(() -> new UsernameNotFoundException("Usuário e/ou senha incorretos"));
		return new User(email, usuario.getPassword(), getPermissoes(usuario));
	}

	private Collection<? extends GrantedAuthority> getPermissoes(UserEntity usuario) {
		Set<SimpleGrantedAuthority> authorities = new HashSet<>();
		authorities.add(new SimpleGrantedAuthority(usuario.getType().toString().toUpperCase()));
		return authorities;
	}

}
