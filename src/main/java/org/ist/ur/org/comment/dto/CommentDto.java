package org.ist.ur.org.comment.dto;

import org.ist.ur.org.posting.model.Posting;

import java.time.LocalDateTime;
import java.util.UUID;

public class CommentDto {

  private UUID id;

  private String body;

  private UUID postingId;

  private Long userId;

  private LocalDateTime createdAt;

  private LocalDateTime updatedAt;

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public UUID getPostingId() {
    return postingId;
  }

  public void setPostingId(UUID postingId) {
    this.postingId = postingId;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(LocalDateTime updatedAt) {
    this.updatedAt = updatedAt;
  }

  @Override
  public String toString() {
    return "CommentDto{" +
            "id=" + id +
            ", body='" + body + '\'' +
            ", postingId=" + postingId +
            ", userId=" + userId +
            ", createdAt=" + createdAt +
            ", updatedAt=" + updatedAt +
            '}';
  }
}
