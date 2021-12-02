/*
package com.petbuddy.api.service.post;

import com.petbuddy.api.error.NotFoundException;
import com.petbuddy.api.model.commons.Id;
import com.petbuddy.api.model.pet.Comment;
import com.petbuddy.api.model.pet.Pet;
import com.petbuddy.api.model.user.UserInfo;
import com.petbuddy.api.repository.post.CommentRepository;
import com.petbuddy.api.repository.post.PetRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static java.util.Collections.emptyList;

@Service
public class CommentService {

  private final PetRepository petRepository;

  private final CommentRepository commentRepository;

  public CommentService(PetRepository petRepository, CommentRepository commentRepository) {
    this.petRepository = petRepository;
    this.commentRepository = commentRepository;
  }

  @Transactional
  public Comment write(Id<Pet, Long> postId, Id<UserInfo, Long> postWriterId, Id<UserInfo, Long> userId, Comment comment) {
    checkArgument(comment.getPostId().equals(postId), "comment.postId must equals postId");
    checkArgument(comment.getUserId().equals(userId), "comment.userId must equals userId");
    checkNotNull(comment, "comment must be provided.");

    return findPost(postId, postWriterId, userId)
      .map(post -> {
        petRepository.update(post);
        return insert(comment);
      })
      .orElseThrow(() -> new NotFoundException(Pet.class, postId, userId));
  }

  @Transactional(readOnly = true)
  public List<Comment> findAll(Id<Pet, Long> postId, Id<UserInfo, Long> postWriterId, Id<UserInfo, Long> userId) {
    return findPost(postId, postWriterId, userId)
      .map(post -> commentRepository.findAll(postId))
      .orElse(emptyList());
  }

  private Optional<Pet> findPost(Id<Pet, Long> postId, Id<UserInfo, Long> postWriterId, Id<UserInfo, Long> userId) {
    checkNotNull(postId, "postId must be provided.");
    checkNotNull(postWriterId, "postWriterId must be provided.");
    checkNotNull(userId, "userId must be provided.");

    return petRepository.findById(postId, postWriterId, userId);
  }

  private Comment insert(Comment comment) {
    return commentRepository.insert(comment);
  }

  private void update(Comment comment) {
    commentRepository.update(comment);
  }

}*/
