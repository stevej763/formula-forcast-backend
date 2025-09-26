package com.steve.formulaforecast.service.authentication;

import com.steve.formulaforecast.persistence.entity.account.AccountAuthenticationUserDetailsEntity;
import com.steve.formulaforecast.service.Account.model.Account;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticatedAccountProvider {

    public Account getAuthenticatedAccount() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof AccountAuthenticationUserDetailsEntity userDetails) {
            return new Account(
                    userDetails.accountUid(),
                    userDetails.firstName(),
                    userDetails.lastName(),
                    userDetails.email()
            );
        }
        throw new RuntimeException("No authenticated account found when authenticated endpoint was accessed");
    }
}
