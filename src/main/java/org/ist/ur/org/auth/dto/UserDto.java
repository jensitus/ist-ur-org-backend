package org.ist.ur.org.auth.dto;

import org.ist.ur.org.auth.model.User;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.util.List;
import java.util.UUID;

public class UserDto {

  private Long id;

  private UUID slug;

  private String username;

  private String email;

  private String accessToken;

  private List<UserDto> follower;

  private List<UserDto> followed;

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

  public List<UserDto> getFollower() {
    return follower;
  }

  public void setFollower(List<UserDto> follower) {
    this.follower = follower;
  }

  public List<UserDto> getFollowed() {
    return followed;
  }

  public void setFollowed(List<UserDto> followed) {
    this.followed = followed;
  }

  @Override
  public String toString() {
    return "UserDto{" +
            "id=" + id +
            ", slug=" + slug +
            ", username='" + username + '\'' +
            ", email='" + email + '\'' +
            ", accessToken='" + accessToken + '\'' +
            ", follower=" + follower +
            ", followed=" + followed +
            '}';
  }
}
