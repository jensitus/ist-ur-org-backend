package org.ist.ur.org.auth.controller;

import org.ist.ur.org.auth.dto.UserDto;
import org.ist.ur.org.auth.message.*;
import org.ist.ur.org.auth.model.Role;
import org.ist.ur.org.auth.model.RoleName;
import org.ist.ur.org.auth.model.User;
import org.ist.ur.org.auth.repository.RoleRepo;
import org.ist.ur.org.auth.repository.UserRepo;
import org.ist.ur.org.auth.services.AuthService;
import org.ist.ur.org.auth.services.UserService;
import org.ist.ur.org.auth.security.JwtProvider;
import org.ist.ur.org.common.message.MessageOrg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthRestApi {

  private static final Logger logger = LoggerFactory.getLogger(AuthRestApi.class);

  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  UserRepo userRepo;

  @Autowired
  RoleRepo roleRepo;

  @Autowired
  PasswordEncoder encoder;

  @Autowired
  JwtProvider jwtProvider;

  @Autowired
  private UserService userService;

  @Autowired
  private AuthService authService;

  @PostMapping("/signin")
  public ResponseEntity<JwtResponse> authenticateUser(@Valid @RequestBody LoginForm loginForm) {
    UserDto userDto = authService.getUserDtoWithJwt(loginForm);
    return new ResponseEntity<>(new JwtResponse(userDto), HttpStatus.OK);
  }

  @PostMapping(value = "/signup", consumes = {})
  public ResponseEntity<MessageOrg> registerUser(@Valid @RequestBody SignUpForm signUpRequest) {
    MessageOrg m = authService.signUp(signUpRequest);
    if (m.getBool()) {
      return new ResponseEntity<>(m, HttpStatus.CREATED);
    } else {
      return new ResponseEntity<>(m, HttpStatus.BAD_REQUEST);
    }
  }

  @GetMapping("/mist")
  public String mist() {
    return "Hi du verdammter Mistkerl";
  }

  @PostMapping("/reset_password")
  public ResponseEntity<String> resetPassword(@RequestBody PasswordResetForm passwordResetForm) {
    User user = userRepo.findByEmail(passwordResetForm.getEmail());
    if (user == null) {
      logger.info("no user with email: " + passwordResetForm.getEmail() + " found");
      return new ResponseEntity<>("Die Emailadresse gibt es nicht", HttpStatus.NOT_FOUND);
    }
    logger.info("user found: " + user.getUsername() + " " + user.getEmail());
    userService.createPasswordResetTokenForUser(user);
    return new ResponseEntity<>("Schauns ins Postfach", HttpStatus.OK);
  }

  @GetMapping(value = "/reset_password/{token}")
  @ResponseStatus
  public ResponseEntity resetPassword(@PathVariable("token") String base64Token, @RequestParam("email") String email) {
    boolean tokenNotExpired = userService.checkResetToken(base64Token, email);
    HttpHeaders headers = new HttpHeaders();
    headers.add("checked", "AuthRestApi");
    if (tokenNotExpired == false) {
      return ResponseEntity.unprocessableEntity().headers(headers).body("tja, abjeloofen");
    } else if (tokenNotExpired) {
      return ResponseEntity.accepted().headers(headers).body("perfekt");
    } else {
      return ResponseEntity.badRequest().headers(headers).body("mann");
    }
  }

  @PutMapping("/reset_password/{token}")
  @ResponseStatus
  public ResponseEntity resetPassword(@Valid @RequestBody PasswordResetForm passwordResetForm, @PathVariable("token") String base64Token, @RequestParam("email") String email) {
    logger.info("RESET_PASSWORD");
    logger.info("form " + passwordResetForm.toString());
    logger.info(passwordResetForm.getEmail());
    logger.info("token " + base64Token);
    logger.info("email " + email);
    if (passwordResetForm.getPassword().equals(passwordResetForm.getPassword_confirmation())) {
      User user = userRepo.findByEmail(passwordResetForm.getEmail());
      user.setPassword(encoder.encode(passwordResetForm.getPassword()));
      userRepo.save(user);
      return ResponseEntity.status(HttpStatus.OK).body("passt alles");
    } else {
      logger.warn("password confirmation doesn't match");
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("password confirmation does not match");
    }
  }

  @PostMapping("/auth/check_auth_token")
  public ResponseEntity checkTheAuthToken(@RequestBody String token) {
    logger.info("we are checking the Token");
    MessageOrg m = new MessageOrg();
    boolean validToken = jwtProvider.validateJwtToken(token);
    if (validToken) {
      m.setText("Zum Donner, das funktioniert ja wirklich");
      m.setBool(true);
      return new ResponseEntity<>(m, HttpStatus.OK);
    } else {
      m.setText("Damn, your AuthToken is not valid anymore");
      m.setBool(false);
      return new ResponseEntity<>(m, HttpStatus.FORBIDDEN);
    }
  }

}
