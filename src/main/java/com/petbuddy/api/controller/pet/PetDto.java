package com.petbuddy.api.controller.pet;

import com.beust.jcommander.internal.Lists;
import com.petbuddy.api.model.commons.EnumMapperValue;
import com.petbuddy.api.model.pet.Pet;
import com.petbuddy.api.model.user.Gender;
import com.querydsl.core.annotations.QueryProjection;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class PetDto {


    @ApiModelProperty(value = "PK", required = true)
    private Long id;
    @ApiModelProperty(value = "펫 소개")
    private String petIntroduce;
    @ApiModelProperty(value = "펫 이름", required = true)
    private String petName;
    @ApiModelProperty(value = "좋아요 횟수")
    private int likes;
    @ApiModelProperty(value = "나의 좋아요 여부")
    private boolean likesOfMe;
    @ApiModelProperty(value = "펫 생일")
    private LocalDate petBirth;
    @ApiModelProperty(value = "펫 성별(male or female)")
    private Gender petGender;
    @ApiModelProperty(value = "중성화 여부(true or false)", required = true)
    private Boolean neuteringYn;
    @ApiModelProperty(value = "펫 성격들")
    private List<EnumMapperValue> personalities;

    @ApiModelProperty(value = "펫 상태", required = true)
    private int status;
    @ApiModelProperty(value = "등록일시", required = true)
    private LocalDateTime createdAt;
    @ApiModelProperty(value = "수정일시", required = true)
    private LocalDateTime updatedAt;
    @ApiModelProperty(value = "펫 이미지 리스트")
    private List<PetImageDto> petImages;



    @QueryProjection
    public PetDto(Long id,String petName,LocalDate petBirth, Gender petGender, boolean neuteringYn, String petIntroduce, int likes, int status, Long likesOfMe, LocalDateTime createAt){
        this.id =id;
        this.petName = petName;
        this.petBirth = petBirth;
        this. petGender = petGender;
        this.neuteringYn = neuteringYn;
        this.petIntroduce = petIntroduce;
        this.likes = likes;
        this. status = status;
        this.likesOfMe = likesOfMe != null;
        this.createdAt = createAt;

    }

    public PetDto(Pet pet){
        BeanUtils.copyProperties(pet,this);
        this.petImages = PetImageDto.listOf(pet.getPetImages());
        this.personalities =pet.getPersonalities() == null? Lists.newArrayList() : pet.getPersonalities().stream().map(EnumMapperValue::new).collect(Collectors.toList());

    }
}
