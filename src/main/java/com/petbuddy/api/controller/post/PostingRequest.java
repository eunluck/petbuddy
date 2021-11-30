package com.petbuddy.api.controller.post;

import com.petbuddy.api.model.commons.Id;
import com.petbuddy.api.model.pet.Pet;
import com.petbuddy.api.model.user.User;
import io.swagger.annotations.ApiModelProperty;

public class PostingRequest {

  @ApiModelProperty(value = "내용", required = true)
  private String petIntroduce;
  private String petName;

  protected PostingRequest() {}


  public Pet newPost(Id<User, Long> userId) {
    return new Pet(userId,  petIntroduce);
  }


}