package org.ist.ur.org.auth.controller;

import org.ist.ur.org.auth.dto.FollowerShipDto;
import org.ist.ur.org.auth.dto.UserDto;
import org.ist.ur.org.auth.model.User;
import org.ist.ur.org.auth.repository.UserRepo;
import org.ist.ur.org.auth.serviceimpl.UserDetailsServiceImpl;
import org.ist.ur.org.auth.services.UserService;
import org.ist.ur.org.common.message.MessageOrg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/users")
public class UserRestApi {

  private static final Logger logger = LoggerFactory.getLogger(UserRestApi.class);

  @Autowired
  private UserRepo userRepo;

  @Autowired
  private UserDetailsServiceImpl userDetailsService;

  @Autowired
  private UserService userService;

  @GetMapping("/all")
  public List<User> getAllUsers() {
    return userRepo.findAll();
  }

  @GetMapping("/{id}")
  public ResponseEntity getUserById(@PathVariable("id") Long id) {
    UserDto u = userService.getUserById(id);
    return new ResponseEntity<>(u, HttpStatus.OK);
  }

  @GetMapping("/principle/{username}")
  public UserDetails getSpecialUser(@PathVariable String username) {
    return userDetailsService.loadUserByUsername(username);
  }

  @PostMapping("/followship/create/{follower}")
  public ResponseEntity<MessageOrg> createFollowerShip(@PathVariable("follower") Long followerId, @RequestBody Long followedId) {
    FollowerShipDto fDto = userService.createFollowerShip(followerId, followedId);
    if (fDto != null) {
      return new ResponseEntity<>(new MessageOrg("yeah, now you follow", true), HttpStatus.ACCEPTED);
    } else {
      return new ResponseEntity<>(new MessageOrg("Oh no, that didn't work", false), HttpStatus.CONFLICT);
    }
  }

  @DeleteMapping("/followship/delete/{id}")
  public void deleteFollowShip(@PathVariable("id") UUID id) {
    userService.deleteFollowerShip(id);
  }

  @GetMapping("/followership/follower/{follower_id}/followed/{followed_id}/")
  public ResponseEntity<FollowerShipDto> checkIfOneIsFollowingTheOther(@PathVariable("follower_id") Long followerId, @PathVariable("followed_id") Long followedId) {
    FollowerShipDto fs = userService.checkIfOneIsFollowingTheOther(followerId, followedId);
    if (fs != null) {
      return new ResponseEntity<>(fs, HttpStatus.OK);
    } else {
      return null;
    }
  }

}
