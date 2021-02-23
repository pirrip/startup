package com.repetentia.component.user;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.repetentia.web.user.model.RtaUserDetails;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RtaUserDetailsService implements UserDetailsService {
    private SqlSession sqlSession;

    public void setSqlSession(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("USER DETAILS - {}", username);

        // TODO Auto-generated method stub
        RtaUserDetails rtaUserDetail = new RtaUserDetails();
        rtaUserDetail.setUsername(username);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encoded = passwordEncoder.encode("1111");
        rtaUserDetail.setPassword(encoded);
        rtaUserDetail.setAccountNonExpired(true);
        rtaUserDetail.setAccountNonLocked(true);
        rtaUserDetail.setCredentialsNonExpired(true);
        rtaUserDetail.setEnabled(true);
        List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();
        list.add(new SimpleGrantedAuthority("ROLE_USER"));
        rtaUserDetail.setAuthorities(list);
        return rtaUserDetail;
    }

}
