package com.petbuddy.api.controller.post;

import com.petbuddy.api.controller.user.UserDto;
import com.petbuddy.api.model.pet.Pet;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static org.springframework.beans.BeanUtils.copyProperties;

@Data
@NoArgsConstructor
public class PetDto {

  @ApiModelProperty(value = "PK", required = true)
  private Long seq;
  @ApiModelProperty(value = "펫 소개", required = true)
  private String petIntroduce;
  @ApiModelProperty(value = "펫 이름", required = true)
  private String petName;
  @ApiModelProperty(value = "좋아요 횟수", required = true)
  private int likes;
  @ApiModelProperty(value = "나의 좋아요 여부", required = true)
  private boolean likesOfMe;
  @ApiModelProperty(value = "펫 나이", required = true)
  private int petAge;
  @ApiModelProperty(value = "펫 성별(male or female)", required = true)
  private String petGender;
  @ApiModelProperty(value = "중성화 여부(true or false)", required = true)
  private boolean neuteringYn;

  @ApiModelProperty(value = "작성자")
  private UserDto owner;




  @ApiModelProperty(value = "등록일시", required = true)
  private LocalDateTime createAt;



  public PetDto(Pet source) {
    copyProperties(source, this);

  }


}