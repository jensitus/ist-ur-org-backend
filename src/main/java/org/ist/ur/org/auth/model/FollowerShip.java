package org.ist.ur.org.auth.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "followership")
public class FollowerShip {

  @Id
  @GeneratedValue(generator = "UUID")
  @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
  @Column(name = "id", length = 16, unique = true, nullable = false)
  private UUID id;

  @Column(name = "follower_id")
  private Long followerId;

  @Column(name = "followed_id")
  private Long followedId;

  @Column(name = "created_at")
  private LocalDateTime createdAt;

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
