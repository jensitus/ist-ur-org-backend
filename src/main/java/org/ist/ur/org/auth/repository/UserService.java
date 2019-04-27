package org.ist.ur.org.auth.repository;

import org.ist.ur.org.auth.model.User;
import org.springframework.http.ResponseEntity;

public interface UserService {

  void createPasswordResetTokenForUser(User user);

  boolean checkResetToken(String base64Token, String email);
}
