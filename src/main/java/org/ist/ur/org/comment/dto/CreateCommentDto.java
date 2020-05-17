package org.ist.ur.org.comment.dto;

public class CreateCommentDto {

  private String body;
  private Long postingId;
  private Long userId;

  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }

  public Long getPostingId() {
    return postingId;
  }

  public void setPostingId(Long postingId) {
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
