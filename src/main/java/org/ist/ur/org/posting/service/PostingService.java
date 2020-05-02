package org.ist.ur.org.posting.service;

import org.ist.ur.org.posting.dto.CreatePostingDto;
import org.ist.ur.org.posting.dto.PostingDto;

public interface PostingService {

  public PostingDto getPostingById(Long postingId);

  public PostingDto createPosting(CreatePostingDto createPostingDto);

}
