package org.ist.ur.org.auth.services;

import org.ist.ur.org.auth.dto.UserDto;
import org.ist.ur.org.auth.model.User;

public interface UserService {

  void createPasswordResetTokenForUser(User user);

  boolean checkResetToken(String base64Token, String email);

  UserDto getCurrentUser();

}
