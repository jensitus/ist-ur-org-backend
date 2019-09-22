package org.ist.ur.org.auth.serviceimpl;

import org.ist.ur.org.auth.dto.UserDto;
import org.ist.ur.org.auth.message.LoginForm;
import org.ist.ur.org.auth.model.User;
import org.ist.ur.org.auth.repository.UserRepo;
import org.ist.ur.org.auth.security.JwtProvider;
import org.ist.ur.org.auth.services.AuthService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

  private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  JwtProvider jwtProvider;

  @Autowired
  private UserRepo userRepo;

  @Autowired
  private ModelMapper modelMapper;

  @Override
  public UserDto getUserDtoWithJwt(LoginForm loginForm) {
    Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginForm.getUsername(), loginForm.getPassword()));
    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt = jwtProvider.generateJwtToken(authentication);
    User user = userRepo.findByUsername(loginForm.getUsername()).orElseThrow(() -> new UsernameNotFoundException("User not found with -> username or email: " + loginForm.getUsername()));
    UserDto userDto = modelMapper.map(user, UserDto.class);
    userDto.setAccessToken(jwt);
    logger.info("user: " + userDto.getUsername() + " has successfully logged in");
    return userDto;
  }

}
