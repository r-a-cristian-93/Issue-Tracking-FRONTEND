package rest.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.*;

import rest.db.repositories.*;
import rest.db.models.*;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	private UsersRepository usersRepo;

	public UserDetailsServiceImpl(UsersRepository usersRepo) {
		this.usersRepo = usersRepo;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		UserModel userModel = usersRepo.findByEmail(email);

		if (userModel == null) {
			throw new UsernameNotFoundException(email);
		}
		List<SimpleGrantedAuthority> roles = new ArrayList<>();
		roles.add(new SimpleGrantedAuthority(userModel.getRole().getValue()));
		
		System.out.println("model found. creating Spring User with roles: " + roles);
		return new User(userModel.getEmail(), userModel.getPassword(), roles);
	}
}
