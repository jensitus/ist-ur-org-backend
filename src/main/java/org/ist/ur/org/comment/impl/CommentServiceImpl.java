package org.ist.ur.org.comment.impl;

import org.ist.ur.org.comment.dto.CommentDto;
import org.ist.ur.org.comment.dto.CreateCommentDto;
import org.ist.ur.org.comment.model.Comment;
import org.ist.ur.org.comment.repo.CommentRepo;
import org.ist.ur.org.comment.service.CommentService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class CommentServiceImpl implements CommentService {

  private static final Logger logger = LoggerFactory.getLogger(CommentServiceImpl.class);

  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  private CommentRepo commentRepo;

  @Override
  public CommentDto createComment(CreateCommentDto createCommentDto) {
    logger.info(createCommentDto.toString());
    Comment comment = modelMapper.map(createCommentDto, Comment.class);
    comment.setCreatedAt(LocalDateTime.now());
    Comment c = commentRepo.save(comment);
    CommentDto cDto = modelMapper.map(c, CommentDto.class);
    logger.info(cDto.toString());
    return cDto;
  }

  @Override
  public List<CommentDto> commentsPerPosting(UUID postingId) {
    List<Comment> commentList = commentRepo.findByPostingIdOrderByCreatedAt(postingId);
    Type dtoListType = new TypeToken<List<CommentDto>>() {}.getType();
    return modelMapper.map(commentList, dtoListType);
  }
}
