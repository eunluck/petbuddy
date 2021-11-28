package com.petbuddy.api.controller.post;

import com.petbuddy.api.model.commons.Id;
import com.petbuddy.api.model.pet.Pet;
import com.petbuddy.api.model.pet.Writer;
import com.petbuddy.api.model.user.User;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class PostingRequest {

  @ApiModelProperty(value = "내용", required = true)
  private String contents;

  protected PostingRequest() {}

  public String getContents() {
    return contents;
  }

  public Pet netPet(Id<User, Long> userId) {
    return new Pet(userId,  contents);
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
      .append("contents", contents)
      .toString();
  }

}