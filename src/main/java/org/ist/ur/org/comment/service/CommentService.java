package org.ist.ur.org.comment.service;

import org.ist.ur.org.comment.dto.CommentDto;
import org.ist.ur.org.comment.dto.CreateCommentDto;

import java.util.List;

public interface CommentService {

  CommentDto createComment(CreateCommentDto createCommentDto);

  List<CommentDto> commentsPerPosting(Long postingId);

}
