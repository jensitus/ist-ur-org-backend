package org.ist.ur.org.auth.dto;

import org.ist.ur.org.auth.model.User;

import java.time.LocalDateTime;
import java.util.UUID;

public class FollowerShipDto {

  private UUID id;

  private Long followerId;

  private Long followedId;

  private LocalDateTime createdAt;

  public FollowerShipDto() {
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public Long getFollowerId() {
    return followerId;
  }

  public void setFollowerId(Long followerId) {
    this.followerId = followerId;
  }

  public Long getFollowedId() {
    return followedId;
  }

  public void setFollowedId(Long followedId) {
    this.followedId = followedId;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }



}
