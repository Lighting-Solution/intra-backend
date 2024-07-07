//package com.ls.in.messenger.util;
//
//import com.ls.in.global.util.CustomUserDetails;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//
//public class MessageUtil {
//	public static Integer getCurrentEmpId() {
//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
//		return userDetails.getEmpId();
//	}
//}
