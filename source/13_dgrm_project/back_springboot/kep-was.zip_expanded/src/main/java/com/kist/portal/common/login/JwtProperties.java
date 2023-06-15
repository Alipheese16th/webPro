package com.kist.portal.common.login;

public class JwtProperties {
	public static final String DOMAIN_NAME = "localhost";
	public static final String COOKIE_NAME = "DGRM-COOKIE";
    public static final String SECRET = "donggrami-ehs-project-key";
    public static final int EXPIRATION_TIME = 36000000; // 10 Hour
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
}
