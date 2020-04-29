package com.nfinity.reporting.reportingapp1.security;

public class SecurityConstants {
    public static final String SECRET = "SecretKeyToGenJWTs"; // We should place this in a secure location or an encrypted file
    public static final long EXPIRATION_TIME = 864_000_000; // 10 days
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String OKTA_SECRET="mxtaQ-fYsd-Z0jVu5ZjSV6NQzYjuxaRz8wrF4hY0HoU";
    public static final String HEADER_STRING = "Authorization";
    public static final String REGISTER = "/users/register";
    public static final String CONFIRM = "/users/confirm";
    public static final String COOKIE_NAME = "FLOWABLE_REMEMBER_ME";

}