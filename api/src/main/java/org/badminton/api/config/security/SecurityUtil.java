package org.badminton.api.config.security;

import jakarta.servlet.http.HttpServletRequest;

public class SecurityUtil {
	public static boolean isPublicPath(HttpServletRequest request) {
		String path = request.getRequestURI();
		return path.equals("/") || path.equals("/groups") || path.startsWith("/oauth2") ||
			path.startsWith("/login") || path.startsWith("/api") || path.startsWith("/swagger-ui") ||
			path.startsWith("/v3/api-docs");
	}

	public static String extractClubTokenFromRequest(HttpServletRequest request) {
		String path = request.getRequestURI();
		String[] parts = path.split("/");
		if (parts.length > 2 && parts[1].equals("v1") && parts[2].equals("club")) {
			return parts.length > 3 ? parts[3] : null;
		}
		return null;
	}
}
