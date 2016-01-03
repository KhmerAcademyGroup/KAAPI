package org.kaapi.app.services.impl;

import org.kaapi.app.entities.APIUser;
import org.kaapi.app.services.APIUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Repository("APIUserCustomUserDetialSerivce")
public class APIUserCustomUserDetialSerivce implements UserDetailsService{

	@Autowired
	@Qualifier("APIUserService")
	private APIUserService apiUserService;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		APIUser user = apiUserService.findUserByUsername(username);
		if (user == null) {
			System.out.println("User not found");
			throw new UsernameNotFoundException("User not found");
		}
		System.out.println("User ID : " + user.getId());
//		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
//				user.getStatus().equals("1"), true, true, true, getGrantedAuthorities(user));
		return user;
	}
	
	
	/*private List<GrantedAuthority> getGrantedAuthorities(User user) {
	List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
	try{
		for (Role role : user.getRoles()) {
			authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
			System.out.println(role.getName());
		}
		//authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		System.out.print("authorities :" + authorities);
	}catch(Exception ex){
		ex.printStackTrace();
	}
		return authorities;
	}*/
	
}
