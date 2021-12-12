package com.petbuddy.api.model.pet;

import com.petbuddy.api.model.BaseEntity;
import com.petbuddy.api.model.MyEntityListener;
import com.petbuddy.api.model.user.UserInfo;
import lombok.*;
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
@Entity
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Pet extends BaseEntity {

  @Id
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
  @ManyToOne(optional = false,fetch = FetchType.LAZY)
  private UserInfo user;

  public Pet(UserInfo user,String petName, String petGender,int petAge,boolean neuteringYn,  String petIntroduce) {
    this(null, user, petIntroduce,petName, petGender, petAge, neuteringYn, 0, false);
  }
  @Builder
  public Pet(Long seq, UserInfo user, String petName, String petIntroduce, String petGender,int petAge,boolean neuteringYn, int likes, boolean likesOfMe) {
    checkNotNull(petName, "강아지 이름을 입력해주세요");
    checkArgument(isNotEmpty(petIntroduce), "contents must be provided.");
    checkArgument( petAge>=0, "강아지의 나이를 입력해주세요");
    checkArgument(isNotEmpty(petGender) && petGender.equals("male") || petGender.equals("female"), "contents must be provided and 'male' or 'female'");
    checkArgument(
            petIntroduce.length() >= 4 && petIntroduce.length() <= 500,
      "post contents length must be between 4 and 500 characters."
    );

    this.seq = seq;
    this.user = user;
    this.petIntroduce = petIntroduce;
    this.petGender = petGender;
    this.petName = petName;
    this.petAge = petAge;
    this.neuteringYn = neuteringYn;
    this.likes = likes;
    this.likesOfMe = likesOfMe;
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

}