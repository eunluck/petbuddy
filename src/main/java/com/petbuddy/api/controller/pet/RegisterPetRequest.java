package com.petbuddy.api.controller.pet;

import com.petbuddy.api.model.pet.Pet;
import com.petbuddy.api.model.user.Gender;
import com.petbuddy.api.model.user.UserInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class RegisterPetRequest {

  @ApiModelProperty(value = "강아지 소개")
  private String petIntroduce;
  @ApiModelProperty(value = "강아지 이름", required = true)
  private String petName;
  @ApiModelProperty(value = "강아지 나이")
  private Integer petAge;
  @ApiModelProperty(value = "강아지 성별")
  private String petGender;
  @ApiModelProperty(value = "중성화 여부")
  private boolean neuteringYn;


  public Pet newPet(UserInfo user) {

    return new Pet(user,petName, Gender.of(petGender),petAge,neuteringYn,  petIntroduce);
  }


}