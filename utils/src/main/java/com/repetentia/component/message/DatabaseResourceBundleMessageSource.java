package com.repetentia.component.message;

import java.text.MessageFormat;
import java.util.Locale;

import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.support.AbstractMessageSource;

import com.repetentia.support.log.Marker;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DatabaseResourceBundleMessageSource extends AbstractMessageSource implements InitializingBean {
    private SqlSession sqlSession;
    private LocaleMessageHolder localeMessageHolder;

    @Override
    protected MessageFormat resolveCode(String code, Locale locale) {
        MessageFormat messageFormat = localeMessageHolder.resolveCode(code, locale);
        log.debug(Marker.MESSAGE, "message [{} : {}] - [{}]", code, locale, messageFormat.format(null));
        return messageFormat;
    }

    public void setSqlSession(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
        Configuration configuration = sqlSession.getConfiguration();
        configuration.addMapper(DatabaseMessageMapper.class);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        load();
    }

    private void load() {
        log.info(Marker.DATA_LOAD, "- Loading Messages from Database !!!");
        LocaleMessageHolder localeMessageHolder = new LocaleMessageHolder(sqlSession);
        localeMessageHolder.load();
        this.localeMessageHolder = localeMessageHolder;
    }
}