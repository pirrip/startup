package com.repetentia.component.security;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.session.SqlSession;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.util.matcher.RequestMatcher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public class UrlSecuritySource {
    private SqlSession sqlSession;

    public UrlSecuritySource(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
        sqlSession.getConfiguration().addMapper(UrlSecurityMapper.class);
    }

    public Map<RequestMatcher, List<ConfigAttribute>> loadUrlSecuritySource() {
        Map<RequestMatcher, List<ConfigAttribute>> requestMap = new HashMap<RequestMatcher, List<ConfigAttribute>>();
        List<UrlSecurity> list = sqlSession.getMapper(UrlSecurityMapper.class).findAll();
        System.out.println(list);
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        try {
            String json = ow.writeValueAsString(list);
            System.out.println(json);
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        for (UrlSecurity urlSecurity:list) {
            if (UrlSe.PAGE.equals(urlSecurity.getMenuSe()) || UrlSe.SERVICE.equals(urlSecurity.getMenuSe())) {
//                RequestMatcher requestMatcher = new AntPathRequestMatcher(null, null, false);


            }
        }
        return requestMap;
    }

    interface UrlSecurityMapper {
        @Select({ "SELECT"
                , " user_groups.usergroup,"
                , " user_groups.usergroup_nm,"
                , " user_groups.usergroup_desc,"
                , " user_groups.enabled,"
                , " user_auths_url.sqno,"
                , " user_auths_url.pqno,"
                , " user_auths_url.depth,"
                , " user_auths_url.site,"
                , " user_auths_url.menu_se,"
                , " user_auths_url.menu_nm,"
                , " user_auths_url.url,"
                , " user_auths_url.method"
            , " FROM"
                , " user_auths_url,"
                , " user_groups,"
                , " user_groups_auths"
            , " WHERE"
                , " user_groups.usergroup = user_groups_auths.usergroup AND"
                , " user_auths_url.sqno = user_groups_auths.sqno"
            , " ORDER BY user_auths_url.sqno"
        })
        List<UrlSecurity> findAll();
    }

}
