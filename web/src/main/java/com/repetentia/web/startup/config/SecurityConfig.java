package com.repetentia.web.startup.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.RoleHierarchyVoter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

import com.repetentia.component.security.DatabaseSecurityMetadataSource;
import com.repetentia.component.security.JwtAuthenticationProvider;
import com.repetentia.component.security.RtaAuthenticationManager;
import com.repetentia.component.security.RtaAuthenticationProvider;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	public AuthenticationManager authenticationManager() {
		AuthenticationManager am = new RtaAuthenticationManager(); 
		return am;
	}

	public DatabaseSecurityMetadataSource databaseSecurityMetadataSource() {
		DatabaseSecurityMetadataSource dsms = new DatabaseSecurityMetadataSource(); 
		return dsms;
	}

	public AuthenticationManager getAuthenticationManager() {
		AuthenticationManager authenticationManager = new ProviderManager(Arrays.asList(authenticationProvider(), jwtAuthenticationProvider()));
		return authenticationManager;
	}

	public AuthenticationProvider authenticationProvider() {
		RtaAuthenticationProvider authenticationProvider = new RtaAuthenticationProvider();
		return authenticationProvider;
	}

	public JwtAuthenticationProvider jwtAuthenticationProvider() {
		JwtAuthenticationProvider authenticationProvider = new JwtAuthenticationProvider();
		return authenticationProvider;
	}
	
	public FilterSecurityInterceptor filterSecurityInterceptor() {
	  FilterSecurityInterceptor filterSecurityInterceptor = new FilterSecurityInterceptor();
	  filterSecurityInterceptor.setAuthenticationManager(getAuthenticationManager());
	  filterSecurityInterceptor.setSecurityMetadataSource(databaseSecurityMetadataSource());
	  filterSecurityInterceptor.setAccessDecisionManager(affirmativeBased());
	  return filterSecurityInterceptor;
	}

	public AffirmativeBased affirmativeBased() {
	  List<AccessDecisionVoter<? extends Object>> accessDecisionVoters = new ArrayList<>();
	  accessDecisionVoters.add(roleVoter());
	  AffirmativeBased affirmativeBased = new AffirmativeBased(accessDecisionVoters);
	  return affirmativeBased;
	}

	public RoleHierarchyVoter roleVoter() {
	  RoleHierarchyVoter roleHierarchyVoter = new RoleHierarchyVoter(roleHierarchy());
	  roleHierarchyVoter.setRolePrefix("ROLE_");
	  return roleHierarchyVoter;
	}

	public RoleHierarchy roleHierarchy() {
	  RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
	  roleHierarchy.setHierarchy("ROLE_ADMIN > ROLE_USER");
	  return roleHierarchy;
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/static/**");
		web.ignoring().antMatchers("/system/**");
//		web.ignoring().antMatchers("/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.addFilterBefore(filterSecurityInterceptor(), FilterSecurityInterceptor.class);
		http.authorizeRequests().anyRequest().authenticated();
		http.formLogin().loginPage("/system/login").loginProcessingUrl("/system/processlogin").usernameParameter("username")
				.passwordParameter("password").permitAll()
//				.successHandler(loginSuccessHandler())
//				.failureHandler(loginFailureHandler())
				;
		http.logout().logoutUrl("/system/logout").logoutSuccessUrl("/system/login").invalidateHttpSession(true)
				.deleteCookies("JSESSIONID").permitAll()
//				.logoutSuccessHandler(logoutHandler())
				;
		http.csrf().disable();
	}

}
