package org.ist.ur.org.comment.repo;

import org.ist.ur.org.comment.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CommentRepo extends JpaRepository<Comment, UUID> {

  List<Comment> findByPostingIdOrderByCreatedAt(UUID postingId);

}
