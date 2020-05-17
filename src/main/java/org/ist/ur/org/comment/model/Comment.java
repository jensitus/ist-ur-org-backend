package org.ist.ur.org.comment.model;

import org.hibernate.annotations.GenericGenerator;
import org.ist.ur.org.posting.model.Posting;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "comments")
public class Comment {

  @Id
  @GeneratedValue(generator = "UUID")
  @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
  @Column(name = "id", length = 16, unique = true, nullable = false)
  private UUID id;

  @Column(name = "body")
  private String body;

  @ManyToOne
  @JoinColumn(name = "posting_id")
  private Posting posting;

  @Column(name = "user_id")
  private Long userId;

  @Column(name = "created_at")
  private LocalDateTime createdAt;

  @Column(name = "updated_at")
  private LocalDateTime updatedAt;

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }

  public Posting getPosting() {
    return posting;
  }

  public void setPosting(Posting posting) {
    this.posting = posting;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
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
    return "Comment{" +
            "id=" + id +
            ", body='" + body + '\'' +
            ", posting=" + posting +
            ", userId=" + userId +
            ", createdAt=" + createdAt +
            ", updatedAt=" + updatedAt +
            '}';
  }
}
