package com.steve.formulaforecast.api;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "auth.cookies")
public class AuthCookieProperties {

    private final String domain;
    private final String sameSite;
    private final boolean httpOnly;
    private final boolean secure;

    public AuthCookieProperties(String domain, String sameSite, boolean httpOnly, boolean secure) {
        this.domain = domain;
        this.sameSite = sameSite;
        this.httpOnly = httpOnly;
        this.secure = secure;
    }

    public String getDomain() {
        return domain;
    }

    public boolean httpOnly() {
        return httpOnly;
    }

    public boolean isSecure() {
        return secure;
    }

    public String sameSite() {
        return sameSite;
    }
}
