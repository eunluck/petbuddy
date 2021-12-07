package com.petbuddy.api.model.pet;

import com.petbuddy.api.model.MyEntityListener;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static java.time.LocalDateTime.now;
import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;


@Data
@NoArgsConstructor
@EntityListeners(MyEntityListener.class)
@Entity
public class Pet {

  @javax.persistence.Id
  @GeneratedValue
  private Long seq;
  private String petName;
  private int petAge;
  private String petGender;
  private boolean neuteringYn;
  private String petIntroduce;
  private int likes;
  @Transient
  private boolean likesOfMe;
  private Long userId;
  @CreatedDate
  @Column(updatable = false)
  private  LocalDateTime createdAt;
  @LastModifiedDate
  private  LocalDateTime updatedAt;

  public Pet(Long userId,String petName, String petGender,int petAge,boolean neuteringYn,  String petIntroduce) {
    this(null, userId, petIntroduce,petName, petGender, petAge, neuteringYn, 0, false,null);
  }
  @Builder
  public Pet(Long seq, Long userId, String petName, String petIntroduce, String petGender,int petAge,boolean neuteringYn, int likes, boolean likesOfMe, LocalDateTime createdAt) {
    checkNotNull(userId, "userId must be provided.");
    checkNotNull(petName, "강아지 이름을 입력해주세요");
    checkArgument(isNotEmpty(petIntroduce), "contents must be provided.");
    checkArgument( petAge>=0, "강아지의 나이를 입력해주세요");
    checkArgument(isNotEmpty(petGender) && petGender.equals("male") || petGender.equals("female"), "contents must be provided and 'male' or 'female'");
    checkArgument(
            petIntroduce.length() >= 4 && petIntroduce.length() <= 500,
      "post contents length must be between 4 and 500 characters."
    );

    this.seq = seq;
    this.userId = userId;
    this.petIntroduce = petIntroduce;
    this.petGender = petGender;
    this.petName = petName;
    this.petAge = petAge;
    this.neuteringYn = neuteringYn;
    this.likes = likes;
    this.likesOfMe = likesOfMe;
    this.createdAt = defaultIfNull(createdAt, now());
  }

  public void modifyPetIntroduce(String petIntroduce) {
    checkArgument(isNotEmpty(petIntroduce), "petIntroduce must be provided.");
    checkArgument(
            petIntroduce.length() >= 4 && petIntroduce.length() <= 500,
      "post petIntroduce length must be between 4 and 500 characters."
    );

    this.petIntroduce = petIntroduce;
  }

  public int incrementAndGetLikes() {
    likesOfMe = true;
    return ++likes;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Pet pet = (Pet) o;
    return Objects.equals(seq, pet.seq);
  }

  @Override
  public int hashCode() {
    return Objects.hash(seq);
  }


}