package org.ist.ur.org.auth.services;

import org.ist.ur.org.auth.dto.UserDto;
import org.ist.ur.org.auth.message.LoginForm;

public interface AuthService {

  UserDto getUserDtoWithJwt(LoginForm loginForm);

}
