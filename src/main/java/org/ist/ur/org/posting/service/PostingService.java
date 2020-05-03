package org.ist.ur.org.posting.service;

import org.ist.ur.org.posting.dto.CreatePostingDto;
import org.ist.ur.org.posting.dto.PostingDto;

import java.util.List;

public interface PostingService {

  public PostingDto getPostingById(Long postingId);

  public PostingDto createPosting(CreatePostingDto createPostingDto);

  List<PostingDto> getAllPostings();

}
