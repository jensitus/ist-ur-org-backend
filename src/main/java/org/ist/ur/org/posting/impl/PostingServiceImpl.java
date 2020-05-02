package org.ist.ur.org.posting.impl;

import org.ist.ur.org.posting.dto.PostingDto;
import org.ist.ur.org.posting.repo.PostingRepo;
import org.ist.ur.org.posting.service.PostingService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostingServiceImpl implements PostingService {

  @Autowired
  private PostingRepo postingRepo;

  @Autowired
  private ModelMapper modelMapper;

  @Override
  public PostingDto getPostingById(Long postingId) {
    return modelMapper.map(postingRepo.getOne(postingId), PostingDto.class);
  }
}
