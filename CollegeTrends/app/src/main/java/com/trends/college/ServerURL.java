package com.trends.college;

final class ServerURL {
    private static final String BASE_URL = "http://";
    static final String LOGIN_URL_STUDENT = BASE_URL + "/token/login/student";
    static final String LOGIN_URL_FACULTY = BASE_URL + "/token/login/faculty";
    static final String REGISTER_URL_FACULTY = BASE_URL + "/api/register/faculty";
    static final String REGISTER_URL_STUDENTS = BASE_URL + "/api/register/students";
    static final String LOGOUT_URL = BASE_URL + "/token/remove";
    static final String VERIFY_TOKEN_URL = BASE_URL + "/token/verify";
    static final String REFRESH_TOKEN_URL = BASE_URL + "/token/refresh";
}