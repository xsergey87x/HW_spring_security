package com.cursor.demo.model;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.HashSet;
import java.util.Set;

public enum Role {
  USER("user:read"),
  ADMIN("user:write");

  Role(String s) {
  }

  public Set<SimpleGrantedAuthority> getAuthorities() {
    Set<SimpleGrantedAuthority> authorities = new HashSet<>();
    authorities.add(new SimpleGrantedAuthority(Role.ADMIN.name()));
    authorities.add(new SimpleGrantedAuthority(Role.USER.name()));
    return authorities;
  }
}
