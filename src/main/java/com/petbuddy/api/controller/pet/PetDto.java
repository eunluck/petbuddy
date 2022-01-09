package com.petbuddy.api.controller.pet;

import com.petbuddy.api.model.pet.Pet;
import com.petbuddy.api.model.user.Gender;
import com.petbuddy.api.model.user.UserInfo;
import com.querydsl.core.annotations.QueryProjection;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;

@Data
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
    private Gender petGender;
    @ApiModelProperty(value = "중성화 여부(true or false)", required = true)
    private boolean neuteringYn;
    @ApiModelProperty(value = "펫 상태", required = true)
    private int status;
    @ApiModelProperty(value = "등록일시", required = true)
    private LocalDateTime createAt;
    @ApiModelProperty(value = "펫 소유자", required = true)
    private UserInfo petOwner;

    @QueryProjection
    public PetDto(Long seq,String petName,int petAge, Gender petGender, boolean neuteringYn, String petIntroduce, int likes, int status, Long likesOfMe, LocalDateTime createAt, UserInfo userInfo){
        this.seq =seq;
        this.petName = petName;
        this.petAge = petAge;
        this. petGender = petGender;
        this.neuteringYn = neuteringYn;
        this.petIntroduce = petIntroduce;
        this.likes = likes;
        this. status = status;
        this.likesOfMe = likesOfMe != null;
        this.petOwner = userInfo;
        this.createAt = createAt;

    }

    public PetDto(Pet pet){
        BeanUtils.copyProperties(pet,this);

    }
}
