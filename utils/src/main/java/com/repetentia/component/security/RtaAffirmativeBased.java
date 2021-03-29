package com.repetentia.component.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.vote.AbstractAccessDecisionManager;
import org.springframework.security.core.Authentication;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RtaAffirmativeBased extends AbstractAccessDecisionManager {

    public RtaAffirmativeBased(List<AccessDecisionVoter<?>> decisionVoters) {
        super(decisionVoters);
    }

    @Override
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes)
            throws AccessDeniedException {
        int deny = 0;
        for (AccessDecisionVoter voter : getDecisionVoters()) {
            log.info("# VOTER {} - {}", authentication.getAuthorities(), configAttributes);
            int result = voter.vote(authentication, object, configAttributes);
            switch (result) {
            case AccessDecisionVoter.ACCESS_GRANTED:
                return;
            case AccessDecisionVoter.ACCESS_DENIED:
                deny++;
                break;
            default:
                break;
            }
        }
        if (deny > 0) {
            throw new AccessDeniedException(
                    this.messages.getMessage("AbstractAccessDecisionManager.accessDenied", "Access is denied"));
        }
        // To get this far, every AccessDecisionVoter abstained
        checkAllowIfAllAbstainDecisions();
    }

}