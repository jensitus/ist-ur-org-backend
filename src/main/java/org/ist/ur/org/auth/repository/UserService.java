package org.ist.ur.org.auth.repository;

import org.ist.ur.org.auth.model.User;

public interface UserService {

  void createPasswordResetTokenForUser(User user);

  void checkResetToken(String base64Token);
}
