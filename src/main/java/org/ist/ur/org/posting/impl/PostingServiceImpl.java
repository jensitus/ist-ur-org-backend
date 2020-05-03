package org.ist.ur.org.posting.impl;

import org.ist.ur.org.auth.dto.UserDto;
import org.ist.ur.org.auth.model.User;
import org.ist.ur.org.auth.services.UserService;
import org.ist.ur.org.posting.dto.CreatePostingDto;
import org.ist.ur.org.posting.dto.PostingDto;
import org.ist.ur.org.posting.model.Posting;
import org.ist.ur.org.posting.repo.PostingRepo;
import org.ist.ur.org.posting.service.PostingService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class PostingServiceImpl implements PostingService {

  @Autowired
  private PostingRepo postingRepo;

  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  private UserService userService;

  @Override
  public PostingDto getPostingById(Long postingId) {
    return modelMapper.map(postingRepo.getOne(postingId), PostingDto.class);
  }

  @Override
  public PostingDto createPosting(CreatePostingDto createPostingDto) {
    UserDto userDto = userService.getCurrentUser();
    Posting posting = new Posting();
    posting.setContent(createPostingDto.getContent());
    posting.setUser(modelMapper.map(userDto, User.class));
    posting.setCreatedAt(LocalDateTime.now());
    Posting p = postingRepo.save(posting);
    return modelMapper.map(p, PostingDto.class);
  }

  @Override
  public List<PostingDto> getAllPostings() {
    List<Posting> postings = postingRepo.findAll();
    List<PostingDto> postingDtos = new ArrayList<>();
    for (Posting posting : postings) {
      postingDtos.add(modelMapper.map(posting, PostingDto.class));
    }
    return postingDtos;
  }
}
