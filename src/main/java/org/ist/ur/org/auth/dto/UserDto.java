package org.ist.ur.org.auth.dto;

import java.util.UUID;

public class UserDto {

  private Long id;

  private UUID slug;

  private String username;

  private String email;

  private String accessToken;

  public UserDto() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public UUID getSlug() {
    return slug;
  }

  public void setSlug(UUID slug) {
    this.slug = slug;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getAccessToken() {
    return accessToken;
  }

  public void setAccessToken(String accessToken) {
    this.accessToken = accessToken;
  }

  @Override
  public String toString() {
    return "UserDto{" +
            "id=" + id +
            ", slug=" + slug +
            ", username='" + username + '\'' +
            ", email='" + email + '\'' +
            ", accessToken='" + accessToken + '\'' +
            '}';
  }
}
