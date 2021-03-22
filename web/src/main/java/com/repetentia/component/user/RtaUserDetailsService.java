package com.repetentia.component.user;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.binding.BindingException;
import org.apache.ibatis.session.SqlSession;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.repetentia.web.user.model.RtaUserDetails;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RtaUserDetailsService implements UserDetailsService {
    private SqlSession sqlSession;

    public RtaUserDetailsService(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    @Override
    public RtaUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("USER DETAILS - {}", username);
        RtaUserDetails userDetails = null;
        try {
            userDetails = sqlSession.getMapper(RtaUserDetailsMapper.class).find(username);
        } catch (BindingException e) {
            log.info("REGISTERING RtaUserDetailsMapper to MyBatis !!!");
            sqlSession.getConfiguration().addMapper(RtaUserDetailsMapper.class);
            loadUserByUsername(username);
        }

        if (userDetails == null)
            throw new UsernameNotFoundException(String.format("%s not in database", username));
        List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();
        list.add(new SimpleGrantedAuthority("ROLE_USER"));
        userDetails.setAuthorities(list);
        return userDetails;
    }

    interface RtaUserDetailsMapper {
        @Select({
                "SELECT",
                "   uid as username, pwd as password, name, alias, email, mobile, usergroup,",
                "   account_non_expired, account_non_locked, credentials_non_expired, enabled,",
                "   crdt, crid, updt, upid ",
                "  FROM user_details ",
                " WHERE uid = #{uid}"
        })
        RtaUserDetails find(String uid);
    }
}
