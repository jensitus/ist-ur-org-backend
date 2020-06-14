package org.ist.ur.org.auth.services;

import org.ist.ur.org.auth.dto.UserDto;
import org.ist.ur.org.auth.message.LoginForm;
import org.ist.ur.org.auth.message.SignUpForm;
import org.ist.ur.org.common.message.MessageOrg;

public interface AuthService {

  UserDto getUserDtoWithJwt(LoginForm loginForm);

  MessageOrg signUp(SignUpForm signUpForm);

}
