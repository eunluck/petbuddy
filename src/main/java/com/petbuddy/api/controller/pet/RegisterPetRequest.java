package com.petbuddy.api.controller.pet;

import com.petbuddy.api.model.pet.PersonalityType;
import com.petbuddy.api.model.pet.Pet;
import com.petbuddy.api.model.user.Gender;
import com.petbuddy.api.model.user.UserInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor
public class RegisterPetRequest {

  @ApiModelProperty(value = "강아지 소개")
  private String petIntroduce;
  @ApiModelProperty(value = "강아지 이름", required = true)
  private String petName;
  @ApiModelProperty(value = "강아지 나이")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate petBirth;
  @ApiModelProperty(value = "강아지 성별")
  private String petGender;
  @ApiModelProperty(value = "중성화 여부")
  private Boolean neuteringYn;
  @ApiModelProperty(value = "이미지 id 목록")
  private List<Long> imageIds;
  @ApiModelProperty(value = "강아지 성격 목록")
  private List<String> personalities;




  public Pet newPet(UserInfo user) {

    return new Pet(user,petName, Gender.of(petGender),petBirth,neuteringYn,  petIntroduce, PersonalityType.of(personalities));
  }


}