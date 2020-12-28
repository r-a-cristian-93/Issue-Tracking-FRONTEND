package rest.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import lombok.*;

import java.util.*;
import java.util.stream.*;

import rest.db.repositories.*;
import rest.db.models.*;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
	private UsersRepository usersRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserModel userModel = usersRepo.findByEmail(username);

		if (userModel == null) {
			throw new UsernameNotFoundException(username);
		}
		List<SimpleGrantedAuthority> roles = new ArrayList<>();
		roles.add(new SimpleGrantedAuthority(userModel.getRole().getValue()));
		
		System.out.println("model found. creating Spring User with roles: " + roles);
		return new User(userModel.getEmail(), userModel.getPassword(), roles);
	}
}
