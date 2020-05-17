package org.ist.ur.org.comment.controller;

import net.bytebuddy.asm.Advice;
import org.ist.ur.org.comment.dto.CommentDto;
import org.ist.ur.org.comment.dto.CreateCommentDto;
import org.ist.ur.org.comment.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/comments")
public class CommentRestApi {

  @Autowired
  private CommentService commentService;

  @PostMapping("/create")
  public ResponseEntity createComment(@RequestBody CreateCommentDto createCommentDto){
    CommentDto commentDto = commentService.createComment(createCommentDto);
    return new ResponseEntity<>(commentDto, HttpStatus.CREATED);
  }

  @GetMapping("/get/{posting_id}")
  public ResponseEntity getCommentsPerPosting(@PathVariable("posting_id") Long postingId) {
    List<CommentDto> commentDtoList = commentService.commentsPerPosting(postingId);
    return new ResponseEntity<>(commentDtoList, HttpStatus.OK);
  }

}
