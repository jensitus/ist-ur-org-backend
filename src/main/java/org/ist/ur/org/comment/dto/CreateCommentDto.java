package org.ist.ur.org.comment.dto;

import java.util.UUID;

public class CreateCommentDto {

  private String body;
  private UUID postingId;
  private Long userId;

  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
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

  @Override
  public String toString() {
    return "CreateCommentDto{" +
            "body='" + body + '\'' +
            ", postingId=" + postingId +
            ", userId=" + userId +
            '}';
  }
}
