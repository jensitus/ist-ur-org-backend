package org.ist.ur.org.posting.controller;

import org.ist.ur.org.posting.dto.CreatePostingDto;
import org.ist.ur.org.posting.dto.PostingDto;
import org.ist.ur.org.posting.service.PostingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/postings")
public class PostingRestApi {

  @Autowired
  private PostingService postingService;

  @GetMapping("/get/{id}")
  public ResponseEntity getPostingById(@PathVariable("id") UUID postingId) {
    return new ResponseEntity<>(postingService.getPostingById(postingId), HttpStatus.OK);
  }

  @PostMapping("/create/")
  public ResponseEntity createPosting(@RequestBody CreatePostingDto createPostingDto) {
    return new ResponseEntity<>(postingService.createPosting(createPostingDto), HttpStatus.CREATED);
  }

  @GetMapping("/all/")
  public ResponseEntity getAllPostings() {
    return new ResponseEntity<>(postingService.getAllPostings(), HttpStatus.OK);
  }

}
