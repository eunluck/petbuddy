/*
package com.petbuddy.api.repository.post;

import com.petbuddy.api.model.commons.Id;
import com.petbuddy.api.model.pet.Comment;
import com.petbuddy.api.model.pet.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Co> {

  Comment insert(Comment comment);

  void update(Comment comment);

  Optional<Comment> findById(Id<Comment, Long> commentId);

  List<Comment> findAll(Id<Pet, Long> postId);

}*/
