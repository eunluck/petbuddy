package com.petbuddy.api.controller.post;

import com.petbuddy.api.model.commons.Id;
import com.petbuddy.api.model.pet.Comment;
import com.petbuddy.api.model.pet.Pet;
import com.petbuddy.api.model.pet.PetOwner;
import com.petbuddy.api.model.user.UserInfo;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class CommentRequest {

  @ApiModelProperty(value = "내용", required = true)
  private String contents;

  protected CommentRequest() {}

  public String getContents() {
    return contents;
  }

  public Comment newComment(Id<UserInfo, Long> userId, Id<Pet, Long> postId, PetOwner petOwner) {
    return new Comment(userId, postId, petOwner, contents);
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
      .append("contents", contents)
      .toString();
  }

}