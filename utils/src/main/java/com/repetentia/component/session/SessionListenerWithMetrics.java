package com.repetentia.component.session;

import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.logging.log4j.ThreadContext;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SessionListenerWithMetrics implements HttpSessionListener {

    private final AtomicInteger activeSessions;

    public SessionListenerWithMetrics() {
        super();
        activeSessions = new AtomicInteger();
    }

    public int getTotalActiveSession() {
        return activeSessions.get();
    }

    public void sessionCreated(final HttpSessionEvent event) {
        HttpSession session = event.getSession();
        activeSessions.incrementAndGet();
    }

    public void sessionDestroyed(final HttpSessionEvent event) {
        ThreadContext.clearAll();
        activeSessions.decrementAndGet();
    }
}
