package org.ist.ur.org.auth.message;

import javax.validation.constraints.NotBlank;

public class PasswordResetForm {

  @NotBlank
  private String email;

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }
}
