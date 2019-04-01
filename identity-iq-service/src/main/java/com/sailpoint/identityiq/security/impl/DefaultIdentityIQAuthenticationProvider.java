package com.sailpoint.identityiq.security.impl;

import com.sailpoint.identityiq.model.entity.IdentityIq;
import com.sailpoint.identityiq.model.repository.IdentityRepository;
import com.sailpoint.identityiq.security.IdentityIQAuthenticationProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Collections;

import static com.sailpoint.identityiq.security.SecurityDictionary.BAD_CREDENTIAL_ERROR;

/**
 * Default identity iq authentication provider
 */
@Slf4j
@Component
public class DefaultIdentityIQAuthenticationProvider implements IdentityIQAuthenticationProvider {

    /**
     * Repository with all identity
     */
    private final IdentityRepository identityRepository;

    /**
     * Constructor with parameters
     *
     * @param identityRepository - identity repository for getting identity
     */
    @Autowired
    public DefaultIdentityIQAuthenticationProvider(IdentityRepository identityRepository) {
        this.identityRepository = identityRepository;
    }

    /**
     * Try to authenticate by authentication data
     *
     * @param authentication - authentication data
     * @return authentication data after authentication
     */
    @Override
    public Authentication authenticate(Authentication authentication) {
        log.debug("Try to authenticate user");
        log.trace("Authentication:[{}]", authentication);
        if (authentication != null && authentication.getName() != null && authentication.getCredentials() != null) {
            String userName = authentication.getName();
            log.debug("User:[{}] try to login", userName);

            log.debug("Try to find identity by name:[{}]", userName);
            IdentityIq identityIq = identityRepository.findByName(userName);

            if (identityIq != null) {
                log.debug("Found identity:[{}] by name:[{}]", identityIq.getId(), userName);
                return new UsernamePasswordAuthenticationToken(userName, null, Collections.emptyList());
            }

        }
        log.debug("Can not to login with data:[{}]", authentication);
        throw new BadCredentialsException(BAD_CREDENTIAL_ERROR);

    }

    /**
     * Check support of authentication class
     *
     * @param authentication - class of credential data
     * @return is support
     */
    @Override
    public boolean supports(Class<?> authentication) {
        log.debug("Check supporting for [{}] authentication", authentication);
        return authentication != null && authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
