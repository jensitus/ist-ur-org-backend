package org.ist.ur.org.posting.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public class PostingDto {

  private UUID id;

  private Long slug;

  private Long userId;

  private String content;

  private LocalDateTime createdAt;

  private LocalDateTime updatedAt;

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public Long getSlug() {
    return slug;
  }

  public void setSlug(Long slug) {
    this.slug = slug;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
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
    return "PostingDto{" +
            "id=" + id +
            ", slug=" + slug +
            ", userId=" + userId +
            ", content='" + content + '\'' +
            ", createdAt=" + createdAt +
            ", updatedAt=" + updatedAt +
            '}';
  }
}
