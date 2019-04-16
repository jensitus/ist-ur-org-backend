package org.ist.ur.org.auth.impl;

import io.jsonwebtoken.impl.Base64Codec;
import org.ist.ur.org.auth.model.PasswordResetToken;
import org.ist.ur.org.auth.model.User;
import org.ist.ur.org.auth.repository.PasswordResetTokenRepo;
import org.ist.ur.org.auth.repository.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

  private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

  @Autowired
  private PasswordResetTokenRepo passwordResetTokenRepo;

  @Override
  public void createPasswordResetTokenForUser(User user) {
    String token = UUID.randomUUID().toString();
    String base64token = Base64Codec.BASE64.encode(token);
    logger.info(base64token);
    LocalDateTime localDateTime = LocalDateTime.now();
    PasswordResetToken passwordResetToken = new PasswordResetToken(user, token, localDateTime);
    logger.info(passwordResetToken.toString());
    passwordResetTokenRepo.save(passwordResetToken);
  }
}
