package com.repetentia.web.startup.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.access.vote.RoleHierarchyVoter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.repetentia.component.security.AjaxAuthenticationEntryPoint;
import com.repetentia.component.security.DatabaseSecurityMetadataSource;
import com.repetentia.component.security.RtaAffirmativeBased;
import com.repetentia.component.security.RtaAuthenticationProvider;
import com.repetentia.component.security.UrlSecuritySource;
import com.repetentia.component.security.jwt.JwtAuthenticationFilter;
import com.repetentia.component.security.jwt.JwtAuthenticationProvider;
import com.repetentia.component.security.jwt.RtaJwtAuthenticationFilter;
import com.repetentia.component.user.RtaUserDetailsService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private SqlSession sqlSession;

    public DatabaseSecurityMetadataSource databaseSecurityMetadataSource() {
        UrlSecuritySource urlSecuritySource = new UrlSecuritySource(sqlSession);
        DatabaseSecurityMetadataSource dsms = new DatabaseSecurityMetadataSource(urlSecuritySource);
        return dsms;
    }

    @Bean
    public AuthenticationManager getAuthenticationManager() {
        AuthenticationManager authenticationManager = new ProviderManager(Arrays.asList(jwtAuthenticationProvider(), rtaAuthenticationProvider()));
        return authenticationManager;
    }

    public AuthenticationProvider rtaAuthenticationProvider() {
        RtaAuthenticationProvider authenticationProvider = new RtaAuthenticationProvider();
        UserDetailsService userDetailsService = rtaUserDetailsService();
        authenticationProvider.setUserDetailsService(userDetailsService);
        return authenticationProvider;
    }

    public JwtAuthenticationProvider jwtAuthenticationProvider() {
        JwtAuthenticationProvider authenticationProvider = new JwtAuthenticationProvider();
        return authenticationProvider;
    }

    public UserDetailsService rtaUserDetailsService() {
        RtaUserDetailsService userDetailsService = new RtaUserDetailsService(sqlSession);
        return userDetailsService;
    }

    public FilterSecurityInterceptor filterSecurityInterceptor() {
        FilterSecurityInterceptor filterSecurityInterceptor = new FilterSecurityInterceptor();
        filterSecurityInterceptor.setSecurityMetadataSource(databaseSecurityMetadataSource());
        filterSecurityInterceptor.setAccessDecisionManager(rtaAffirmativeBased());
        return filterSecurityInterceptor;
    }

    public RtaAffirmativeBased rtaAffirmativeBased() {
        List<AccessDecisionVoter<? extends Object>> accessDecisionVoters = new ArrayList<>();
        accessDecisionVoters.add(roleVoter());
        RtaAffirmativeBased affirmativeBased = new RtaAffirmativeBased(accessDecisionVoters);
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

    public RtaJwtAuthenticationFilter jwtAuthenticationFilter() {
        RtaJwtAuthenticationFilter rtaJwtAuthenticationFilter = new RtaJwtAuthenticationFilter();
        return rtaJwtAuthenticationFilter;
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        String loginUrl = "/system/login";
        String loginProcessingUrl = "/system/processlogin";
        http.exceptionHandling()
                .authenticationEntryPoint(new AjaxAuthenticationEntryPoint(loginUrl));
        http.addFilterBefore(filterSecurityInterceptor(), FilterSecurityInterceptor.class);
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        http.authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .usernameParameter("username")
                .passwordParameter("password")
                .loginPage(loginUrl)
                .loginProcessingUrl(loginProcessingUrl)
//              .successHandler(loginSuccessHandler())
//              .failureHandler(loginFailureHandler())
                .and()
                .csrf().disable();
        http.logout()
                .logoutUrl("/system/logout")
                .logoutSuccessUrl("/system/login")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID").permitAll()
//                .logoutSuccessHandler(logoutHandler())
        ;
    }

}
