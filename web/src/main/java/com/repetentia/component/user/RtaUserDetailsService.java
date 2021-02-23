package com.repetentia.web.startup.config;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RtaUserDetailsService implements UserDetailsService {

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.info("USER DETAILS - {}", username);
		// TODO Auto-generated method stub
		RtaUserDetails rtaUserDetail = new RtaUserDetails();
		rtaUserDetail.setUsername(username);
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encoded = passwordEncoder.encode("qkqkfka2qnsek");
		rtaUserDetail.setPassword(encoded);

		return rtaUserDetail;
	}

}
