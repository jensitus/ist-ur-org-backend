package org.ist.ur.org.auth.serviceimpl;

import io.jsonwebtoken.impl.Base64Codec;
import org.ist.ur.org.auth.dto.UserDto;
import org.ist.ur.org.auth.model.PasswordResetToken;
import org.ist.ur.org.auth.model.User;
import org.ist.ur.org.auth.repository.PasswordResetTokenRepo;
import org.ist.ur.org.auth.repository.UserRepo;
import org.ist.ur.org.auth.services.UserService;
import org.ist.ur.org.auth.security.JwtProvider;
import org.ist.ur.org.common.util.EmailStuff;
import org.ist.ur.org.mailer.service.UrOrgMailer;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

  private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

  @Autowired
  private PasswordResetTokenRepo passwordResetTokenRepo;

  @Autowired
  private UrOrgMailer urOrgMailer;

  @Autowired
  private JwtProvider jwtProvider;

  @Autowired
  private UserRepo userRepo;

  @Autowired
  private ModelMapper modelMapper;

  @Override
  public void createPasswordResetTokenForUser(User user) {
    String token = UUID.randomUUID().toString();
    String base64token = Base64Codec.BASE64.encode(token);
    LocalDateTime localDateTime = LocalDateTime.now();
    PasswordResetToken passwordResetToken = new PasswordResetToken(user, token, localDateTime);
    passwordResetTokenRepo.save(passwordResetToken);
    String url = EmailStuff.DOMAIN_FOR_URL + "/reset-password/" + base64token + "/edit?email=" + user.getEmail();
    String subject = EmailStuff.SUBJECT_PREFIX + " reset instructions";
    String text = "click the fucking link below";
    urOrgMailer.getTheMailDetails(user.getEmail(), subject, text, user.getUsername(), url);
  }

  @Override
  public boolean checkResetToken(String base64Token, String email) {
    return checkIfResetTokenExpired(base64Token, email);
  }

  private boolean checkIfResetTokenExpired(String base64Token, String email) {
    String token = Base64Codec.BASE64.decodeToString(base64Token);
    User user = userRepo.findByEmail(email);
    PasswordResetToken prt = passwordResetTokenRepo.findByTokenAndUserId(token, user.getId());
    logger.info("token and id " + prt.toString());
    LocalDateTime exp = prt.getExpiryDate();
    if (exp.plusHours(2).isBefore(LocalDateTime.now())) {
      return false;
    }
    return true;
  }

  @Override
  public UserDto getCurrentUser() {
    UserDto userDto;
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    UserPrinciple userPrinciple = (UserPrinciple) auth.getPrincipal();
    userDto = modelMapper.map(userPrinciple, UserDto.class);
    return userDto;
  }

}