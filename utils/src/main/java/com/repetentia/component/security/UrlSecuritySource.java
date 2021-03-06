package com.repetentia.component.security;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import com.repetentia.component.code.HttpMethod;
import com.repetentia.component.code.UrlSe;
import com.repetentia.component.log.RtaLogFactory;
import com.repetentia.support.log.Marker;

public class UrlSecuritySource {
    private static final Logger log = RtaLogFactory.getLogger(UrlSecuritySource.class);

    private SqlSession sqlSession;

    public UrlSecuritySource(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
        sqlSession.getConfiguration().addMapper(UrlSecurityMapper.class);
    }

    public Map<RequestMatcher, List<ConfigAttribute>> loadUrlSecuritySource() {
        Map<RequestMatcher, List<ConfigAttribute>> requestMap = new HashMap<RequestMatcher, List<ConfigAttribute>>();
        List<UrlSecurity> urlSecurities = sqlSession.getMapper(UrlSecurityMapper.class).findAll();

        for (UrlSecurity urlSecurity : urlSecurities) {
            if (urlSecurity == null)
                continue;
            if (UrlSe.P.equals(urlSecurity.getMenuSe()) || UrlSe.S.equals(urlSecurity.getMenuSe())) {
                String pattern = String.format("%s%s", urlSecurity.getUrl(), "**");
                HttpMethod httpMethod = urlSecurity.getMethod();

                List<HttpMethod> methodList = new ArrayList<HttpMethod>();
                if (HttpMethod.ALL.equals(httpMethod)) {
                    methodList.add(HttpMethod.HEAD);
                    methodList.add(HttpMethod.OPTIONS);
                    methodList.add(HttpMethod.TRACE);
                    methodList.add(HttpMethod.GET);
                    methodList.add(HttpMethod.POST);
                    methodList.add(HttpMethod.PUT);
                    methodList.add(HttpMethod.DELETE);
                    methodList.add(HttpMethod.PATCH);
                } else if (HttpMethod.CRUD.equals(httpMethod)) {
                    methodList.add(HttpMethod.GET);
                    methodList.add(HttpMethod.POST);
                    methodList.add(HttpMethod.PUT);
                    methodList.add(HttpMethod.DELETE);
                    methodList.add(HttpMethod.PATCH);
                } else if (HttpMethod.GNP.equals(httpMethod)) {
                    methodList.add(HttpMethod.GET);
                    methodList.add(HttpMethod.POST);
                } else {
                    methodList.add(httpMethod);
                }

                String role = String.format("ROLE_%s", urlSecurity.getAuth());

                for (HttpMethod method : methodList) {
                    RequestMatcher requestMatcher = new AntPathRequestMatcher(pattern, method.code(), false);
                    log.info(Marker.AUTH, "# URL pattern [{}][{}] for [{}]", pattern, method.code(), role);
                    List<ConfigAttribute> list = SecurityConfig.createList(role);
                    requestMap.put(requestMatcher, list);
                }

            }
        }
        return requestMap;
    }

    interface UrlSecurityMapper {
        @Select({ "SELECT sqno, pqno, depth, site, menu_se, menu_nm, url, method, auth, crdt, crid, updt, upid FROM usr_menu_auth" })
        List<UrlSecurity> findAll();
    }
}
