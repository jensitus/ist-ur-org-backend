package org.ist.ur.org.auth.controller;

import org.ist.ur.org.auth.message.JwtResponse;
import org.ist.ur.org.auth.message.LoginForm;
import org.ist.ur.org.auth.message.PasswordResetForm;
import org.ist.ur.org.auth.message.SignUpForm;
import org.ist.ur.org.auth.model.Role;
import org.ist.ur.org.auth.model.RoleName;
import org.ist.ur.org.auth.model.User;
import org.ist.ur.org.auth.repository.RoleRepo;
import org.ist.ur.org.auth.repository.UserRepo;
import org.ist.ur.org.auth.repository.UserService;
import org.ist.ur.org.auth.security.JwtProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

  @PostMapping("/signin")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginForm loginRequest) {
    Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt = jwtProvider.generateJwtToken(authentication);
    return ResponseEntity.ok(new JwtResponse(jwt));
  }

  @PostMapping(value = "/signup", consumes = {})
  public ResponseEntity<String> registerUser(@Valid @RequestBody SignUpForm signUpRequest) {

    // check if user or email already present
    if (userRepo.existsByUsername(signUpRequest.getUsername())) {
      return new ResponseEntity<String>("Too Bad -> Username is already taken", HttpStatus.BAD_REQUEST);
    }
    if (userRepo.existsByEmail(signUpRequest.getEmail())) {
      return new ResponseEntity<String>("It's a pity -> but this Email is already in use!", HttpStatus.BAD_REQUEST);
    }

    // create the new user:
    logger.info("signUpRequest " + signUpRequest.toString());
    User user = new User(signUpRequest.getUsername(), signUpRequest.getEmail(), encoder.encode(signUpRequest.getPassword()));
    Role uRole = new Role(RoleName.ROLE_USER);
    Set<String> roleSet = new HashSet<>();
    roleSet.add(uRole.getName().toString());
    signUpRequest.setRole(roleSet);
    Set<String> strRoles = signUpRequest.getRole();
    Set<Role> roles = new HashSet<>();
    strRoles.forEach(role -> {
      switch (role) {
        case "admin":
          Role adminRole = roleRepo.findByName(RoleName.ROLE_ADMIN).orElseThrow(() -> new RuntimeException("The Fucking Role couldn't be found, sorry"));
          roles.add(adminRole);
          break;
        default:
          Role userRole = roleRepo.findByName(RoleName.ROLE_USER).orElseThrow(() -> new RuntimeException("No Role Today my Love is gone away"));
          roles.add(userRole);
      }
    });
    user.setRoles(roles);
    userRepo.save(user);

    return new ResponseEntity<String>("It is a god damn pretty cool", HttpStatus.CREATED);
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

}
