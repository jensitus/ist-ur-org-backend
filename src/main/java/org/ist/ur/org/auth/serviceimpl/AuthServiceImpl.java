package org.ist.ur.org.auth.serviceimpl;

import org.ist.ur.org.auth.dto.UserDto;
import org.ist.ur.org.auth.message.LoginForm;
import org.ist.ur.org.auth.message.SignUpForm;
import org.ist.ur.org.auth.model.Role;
import org.ist.ur.org.auth.model.RoleName;
import org.ist.ur.org.auth.model.User;
import org.ist.ur.org.auth.repository.RoleRepo;
import org.ist.ur.org.auth.repository.UserRepo;
import org.ist.ur.org.auth.security.JwtProvider;
import org.ist.ur.org.auth.services.AuthService;
import org.ist.ur.org.common.message.MessageOrg;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {

  private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private JwtProvider jwtProvider;

  @Autowired
  private PasswordEncoder encoder;

  @Autowired
  private RoleRepo roleRepo;

  @Autowired
  private UserRepo userRepo;

  @Autowired
  private ModelMapper modelMapper;

  @Override
  public UserDto getUserDtoWithJwt(LoginForm loginForm) {
    Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginForm.getUsername(), loginForm.getPassword()));
    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt = jwtProvider.generateJwtToken(authentication);
    User user = userRepo.findByUsername(loginForm.getUsername()).orElseThrow(() -> new UsernameNotFoundException("User not found with -> username or email: " + loginForm.getUsername()));
    UserDto userDto = modelMapper.map(user, UserDto.class);
    userDto.setAccessToken(jwt);
    logger.info("user: " + userDto.getUsername() + " has successfully logged in");
    return userDto;
  }

  @Override
  public MessageOrg signUp(SignUpForm signUpRequest) {
    // check if user or email already present
    if (userRepo.existsByUsername(signUpRequest.getUsername())) {
      return new MessageOrg("Too Bad -> Username is already taken", false);
    }
    if (userRepo.existsByEmail(signUpRequest.getEmail())) {
      return new MessageOrg("It's a pity -> but this Email is already in use!", false);
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
    return new MessageOrg("User successfully created", true);
  }
}
