package org.ist.ur.org.auth.services;

import org.ist.ur.org.auth.dto.FollowerShipDto;
import org.ist.ur.org.auth.dto.UserDto;
import org.ist.ur.org.auth.model.User;

import java.util.List;
import java.util.UUID;

public interface UserService {

  void createPasswordResetTokenForUser(User user);

  boolean checkResetToken(String base64Token, String email);

  UserDto getCurrentUser();

  FollowerShipDto createFollowerShip(Long followerId, Long followedId);

  void deleteFollowerShip(UUID uuid);

  UserDto getUserById(Long id);

  List<UserDto> getFollowers(Long followedId);

  List<UserDto> getFollowing(Long followerId);

  FollowerShipDto checkIfOneIsFollowingTheOther(Long followerId, Long followedId);

}
