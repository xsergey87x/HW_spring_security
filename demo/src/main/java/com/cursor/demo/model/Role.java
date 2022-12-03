package com.cursor.demo.model;

import org.springframework.security.core.authority.SimpleGrantedAuthority;


import java.util.ArrayList;
import java.util.List;

public enum Role {
  USER("user:read"),
  ADMIN("user:write");

  Role(String s) {
  }

  public List<SimpleGrantedAuthority> getAuthorities() {
    List<SimpleGrantedAuthority> authorities = new ArrayList<>();
    authorities.add(new SimpleGrantedAuthority(Role.ADMIN.name()));
    authorities.add(new SimpleGrantedAuthority(Role.USER.name()));
    return authorities;
  }
}
