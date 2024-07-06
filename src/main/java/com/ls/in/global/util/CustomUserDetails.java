//package com.ls.in.global.util;
//
//import com.ls.in.global.emp.domain.model.Emp;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import java.util.Collection;
//
//public class CustomUserDetails implements UserDetails {
//
//	private final Emp emp;
//
//	public CustomUserDetails(Emp emp) {
//		this.emp = emp;
//	}
//
//	public Integer getEmpId() {
//		return emp.getEmpId();
//	}
//
//	@Override
//	public Collection<? extends GrantedAuthority> getAuthorities() {
//		// 권한 로직 구현
//		return null;
//	}
//
//	@Override
//	public String getPassword() {
//		return emp.getAccountPw();
//	}
//
//	@Override
//	public String getUsername() {
//		return emp.getAccountId();
//	}
//
//}
