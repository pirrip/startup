package com.repetentia.component.message;

import java.text.MessageFormat;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.ibatis.session.SqlSession;
import org.springframework.util.StringUtils;

public class LocaleMessageHolder {
    public final static Locale DEFAULT_LOCALE = Locale.KOREAN;
    private SqlSession sqlSession;
    // locale, code
    private final ConcurrentMap<String, ConcurrentMap<String, MessageFormat>> localeMessagesHolder = new ConcurrentHashMap<>();

    LocaleMessageHolder(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public MessageFormat resolveCode(String code, Locale locale) {
        final ConcurrentMap<String, MessageFormat> messagesHolder = resolveLocale(locale);
        MessageFormat messageFormat = resolveCode(code, messagesHolder);
        if (messageFormat == null)
            return new MessageFormat("빈칸");
        return messageFormat;
    }

    public MessageFormat resolveCodeDefaultLocale(String code) {
        final ConcurrentMap<String, MessageFormat> messagesHolder = localeMessagesHolder.get(DEFAULT_LOCALE.toLanguageTag());
        MessageFormat messageFormat = resolveCode(code, messagesHolder);
        if (messageFormat == null)
            return new MessageFormat("");
        return messageFormat;
    }

    private MessageFormat resolveCode(final String code, final ConcurrentMap<String, MessageFormat> messagesHolder) {
        if (messagesHolder == null)
            return null;
        return messagesHolder.get(code);
    }

    private ConcurrentMap<String, MessageFormat> resolveLocale(final Locale locale) {
        if (locale == null) {
            final ConcurrentMap<String, MessageFormat> defaultMessagesHolder = localeMessagesHolder.get(DEFAULT_LOCALE.toLanguageTag());
            return defaultMessagesHolder;
        }
        final ConcurrentMap<String, MessageFormat> messagesHolder = localeMessagesHolder.get(locale.toLanguageTag());

        if (messagesHolder == null) {
            String languageTag = StringUtils.hasText(locale.getCountry()) ? locale.getLanguage() : locale.getLanguage() + "-" + locale.getCountry();
            final ConcurrentMap<String, MessageFormat> alternateMessagesHolder = localeMessagesHolder.get(languageTag);
            if (alternateMessagesHolder == null) {
                final ConcurrentMap<String, MessageFormat> defaultMessagesHolder = localeMessagesHolder.get(DEFAULT_LOCALE.toLanguageTag());
                return defaultMessagesHolder;
            } else {
                return alternateMessagesHolder;
            }
        }

        return messagesHolder;
    }

    public void load() {
        List<DatabaseMessageSource> list = sqlSession.getMapper(DatabaseMessageMapper.class).findAll();
        localeMessagesHolder.clear();
        for (DatabaseMessageSource message : list) {
            final String locale = message.getLocale().substring(0, 2);
            final ConcurrentMap<String, MessageFormat> messagesHolder = getMessagesHolderByLocale(locale);
            MessageFormat messageFormat = new MessageFormat(message.getMessage());
            messagesHolder.put(message.getCode(), messageFormat);
        }
    }

    private ConcurrentMap<String, MessageFormat> getMessagesHolderByLocale(final String locale) {
        final ConcurrentMap<String, MessageFormat> messagesHolder = localeMessagesHolder.get(locale);
        if (messagesHolder == null) {
            final ConcurrentMap<String, MessageFormat> newMessagesHolder = createMessagesHolder();
            localeMessagesHolder.put(locale, newMessagesHolder);
            return newMessagesHolder;
        }
        return messagesHolder;
    }

    private ConcurrentMap<String, MessageFormat> createMessagesHolder() {
        final ConcurrentMap<String, MessageFormat> messagesHolder = new ConcurrentHashMap<>();
        return messagesHolder;
    }
}
