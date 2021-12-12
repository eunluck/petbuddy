package com.petbuddy.api.controller.post;

import com.petbuddy.api.model.pet.Pet;
import com.petbuddy.api.model.user.UserInfo;
import io.swagger.annotations.ApiModelProperty;

public class RegisterPetRequest {

  @ApiModelProperty(value = "강아지 소개", required = true)
  private String petIntroduce;
  @ApiModelProperty(value = "강아지 이름", required = true)
  private String petName;
  @ApiModelProperty(value = "강아지 나이", required = true)
  private int petAge;
  @ApiModelProperty(value = "강아지 성별", required = true)
  private String petGender;
  @ApiModelProperty(value = "중성화 여부", required = true)
  private boolean neuteringYn;

  protected RegisterPetRequest() {}


  public Pet newPet(UserInfo user) {
    return new Pet(user,petName,petGender,petAge,neuteringYn,  petIntroduce);
  }


}