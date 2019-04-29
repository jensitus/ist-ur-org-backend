package org.ist.ur.org.auth.controller;

import org.ist.ur.org.auth.model.User;
import org.ist.ur.org.auth.repository.UserRepo;
import org.ist.ur.org.auth.serviceimpl.UserDetailsServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/users")
public class UserRestApi {

  private static final Logger logger = LoggerFactory.getLogger(UserRestApi.class);

  @Autowired
  private UserRepo userRepo;

  @Autowired
  private UserDetailsServiceImpl userDetailsService;

  @GetMapping("/all")
  public List<User> getAllUsers() {
    return userRepo.findAll();
  }

  @GetMapping("/principle/{username}")
  public UserDetails getSpecialUser(@PathVariable String username) {
    return userDetailsService.loadUserByUsername(username);
  }

}
