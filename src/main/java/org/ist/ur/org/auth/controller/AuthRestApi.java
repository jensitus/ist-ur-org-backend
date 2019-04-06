package org.ist.ur.org.auth.controller;

import org.ist.ur.org.auth.message.JwtResponse;
import org.ist.ur.org.auth.message.LoginForm;
import org.ist.ur.org.auth.message.SignUpForm;
import org.ist.ur.org.auth.model.Role;
import org.ist.ur.org.auth.model.RoleName;
import org.ist.ur.org.auth.model.User;
import org.ist.ur.org.auth.repository.RoleRepo;
import org.ist.ur.org.auth.repository.UserRepo;
import org.ist.ur.org.auth.security.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
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

  @PostMapping("/signin")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginForm loginRequest) {
    Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt = jwtProvider.generateJwtToken(authentication);
    return ResponseEntity.ok(new JwtResponse(jwt));
  }

  @PostMapping("/signup")
  public ResponseEntity<String> registerUser(@Valid @RequestBody SignUpForm signUpRequest) {

    // check if user or email already present
    if (userRepo.existsByUsername(signUpRequest.getUsername())) {
      return new ResponseEntity<String>("Too Bad -> Username is already taken", HttpStatus.BAD_REQUEST);
    }
    if (userRepo.existsByEmail(signUpRequest.getEmail())) {
      return new ResponseEntity<String>("It's a pity -> but this Email is already in use!", HttpStatus.BAD_REQUEST);
    }

    // create the new user:
    User user = new User(signUpRequest.getUsername(), signUpRequest.getEmail(), encoder.encode(signUpRequest.getPassword()));
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

    return ResponseEntity.ok().body("It is a god damn pretty cool");
  }

}
