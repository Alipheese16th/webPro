package com.kist.portal.common.login.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.kist.portal.common.login.dto.UserDetailDto;
import com.kist.portal.common.login.mapper.LoginMapper;

@Service
public class CustomUserDetailsServiceImpl implements UserDetailsService {

  @Autowired
  private LoginMapper loginMapper;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    UserDetailDto user = loginMapper.selectUser(username);

    return user;
  }
  
}
