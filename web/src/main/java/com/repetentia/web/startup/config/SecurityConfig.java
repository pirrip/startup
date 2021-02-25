package com.repetentia.web.startup.config;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.RoleHierarchyVoter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

import com.repetentia.component.security.AjaxAuthenticationEntryPoint;
import com.repetentia.component.security.DatabaseSecurityMetadataSource;
import com.repetentia.component.security.UrlSecuritySource;
import com.repetentia.component.user.RtaUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private SqlSession sqlSession;

    @Bean
    public DatabaseSecurityMetadataSource databaseSecurityMetadataSource() {
        UrlSecuritySource urlSecuritySource = new UrlSecuritySource(sqlSession);
        DatabaseSecurityMetadataSource dsms = new DatabaseSecurityMetadataSource(urlSecuritySource);
        return dsms;
    }

//    @Bean
//    public AuthenticationManager getAuthenticationManager() {
//        AuthenticationManager authenticationManager = new ProviderManager(Arrays.asList(authenticationProvider(), jwtAuthenticationProvider()));
//        return authenticationManager;
//    }

//    public AuthenticationProvider authenticationProvider() {
//        RtaAuthenticationProvider authenticationProvider = new RtaAuthenticationProvider();
//        return authenticationProvider;
//    }
//
//    public JwtAuthenticationProvider jwtAuthenticationProvider() {
//        JwtAuthenticationProvider authenticationProvider = new JwtAuthenticationProvider();
//        return authenticationProvider;
//    }

    @Bean
    public RtaUserDetailsService userDetailsService() {
        RtaUserDetailsService userDetailsService = new RtaUserDetailsService(sqlSession);
        return userDetailsService;
    }

    public FilterSecurityInterceptor filterSecurityInterceptor() {
      FilterSecurityInterceptor filterSecurityInterceptor = new FilterSecurityInterceptor();
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
//		web.ignoring().antMatchers("/system/**");
//		web.ignoring().antMatchers("/error");
//		web.ignoring().antMatchers("/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        String loginUrl = "/system/login";
        http.exceptionHandling().authenticationEntryPoint(new AjaxAuthenticationEntryPoint(loginUrl));
        http.addFilterBefore(filterSecurityInterceptor(), FilterSecurityInterceptor.class);
//		http.authorizeRequests().anyRequest().authenticated();
//		http.authorizeRequests().antMatchers("/**").hasRole("USER").anyRequest().authenticated();
//		http.formLogin().loginPage("/system/login").permitAll()
//			.loginProcessingUrl("/system/processlogin").permitAll()
//			.usernameParameter("username")
//			.passwordParameter("password")
//			.successHandler(loginSuccessHandler())
//			.failureHandler(loginFailureHandler())
//			;
//		http.logout().logoutUrl("/system/logout").logoutSuccessUrl("/system/login").invalidateHttpSession(true)
//			.deleteCookies("JSESSIONID").permitAll()
//			.logoutSuccessHandler(logoutHandler())
            ;
        http.authorizeRequests().anyRequest().authenticated()
        .and()
            .formLogin().loginPage(loginUrl)
            .loginProcessingUrl("/system/processlogin")
        .and()
            .csrf().disable();
    }

}
