package com.trends.college;

import java.net.HttpCookie;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class LoginCookies {
    static final String JWT_ACCESS_COOKIE_NAME = "access_token_cookie";
    static final String JWT_REFRESH_COOKIE_NAME = "refresh_token_cookie";
    static final String JWT_ACCESS_CSRF_COOKIE_NAME = "csrf_access_token";
    static final String JWT_REFRESH_CSRF_COOKIE_NAME = "csrf_refresh_token";

    private String access_cookie;
    private String refresh_cookie;
    private String csrf_access_cookie;
    private String csrf_refresh_cookie;

    LoginCookies(List<HttpCookie> cookieList) throws NullPointerException {
        boolean result = this.setCookies(cookieList);
        if (!result) throw new NullPointerException("Invalid cookie keys");
    }

    LoginCookies(Map<String, String> cookieList) {
        this.setCookies(cookieList);
    }

    private boolean setCookies(List<HttpCookie> cookieList) {
        Map<String, String> jwtCookies = new HashMap<>();
        for (HttpCookie cookie : cookieList) {
            jwtCookies.put(cookie.getName(), cookie.getValue());
        }
        if (jwtCookies.containsKey(LoginCookies.JWT_ACCESS_COOKIE_NAME) && jwtCookies.containsKey(LoginCookies.JWT_REFRESH_COOKIE_NAME)
                && jwtCookies.containsKey(LoginCookies.JWT_ACCESS_CSRF_COOKIE_NAME) && jwtCookies.containsKey(LoginCookies.JWT_REFRESH_CSRF_COOKIE_NAME)) {
            this.access_cookie = jwtCookies.get(LoginCookies.JWT_ACCESS_COOKIE_NAME);
            this.refresh_cookie = jwtCookies.get(LoginCookies.JWT_REFRESH_COOKIE_NAME);
            this.csrf_access_cookie = jwtCookies.get(LoginCookies.JWT_ACCESS_CSRF_COOKIE_NAME);
            this.csrf_refresh_cookie = jwtCookies.get(LoginCookies.JWT_REFRESH_CSRF_COOKIE_NAME);
            return true;
        } else {
            return false;
        }
    }

    private boolean setCookies(Map<String, String> jwtCookies) {
        if (jwtCookies.containsKey(LoginCookies.JWT_ACCESS_COOKIE_NAME) && jwtCookies.containsKey(LoginCookies.JWT_REFRESH_COOKIE_NAME)
                && jwtCookies.containsKey(LoginCookies.JWT_ACCESS_CSRF_COOKIE_NAME) && jwtCookies.containsKey(LoginCookies.JWT_REFRESH_CSRF_COOKIE_NAME)) {
            this.access_cookie = jwtCookies.get(LoginCookies.JWT_ACCESS_COOKIE_NAME);
            this.refresh_cookie = jwtCookies.get(LoginCookies.JWT_REFRESH_COOKIE_NAME);
            this.csrf_access_cookie = jwtCookies.get(LoginCookies.JWT_ACCESS_CSRF_COOKIE_NAME);
            this.csrf_refresh_cookie = jwtCookies.get(LoginCookies.JWT_REFRESH_CSRF_COOKIE_NAME);
            return true;
        } else {
            return false;
        }
    }

    String getAccessCookie() {
        return this.access_cookie;
    }

    String getRefreshCookie() {
        return this.refresh_cookie;
    }

    String getCsrfAccessCookie() {
        return this.csrf_access_cookie;
    }

    String getCsrfRefreshCookie() {
        return this.csrf_refresh_cookie;
    }
}