package com.petbuddy.api.model.pet;

import com.petbuddy.api.model.commons.BaseEntity;
import com.petbuddy.api.model.listener.RegisterPetListener;
import com.petbuddy.api.model.user.Gender;
import com.petbuddy.api.model.user.UserInfo;
import com.querydsl.core.annotations.PropertyType;
import com.querydsl.core.annotations.QueryProjection;
import com.querydsl.core.annotations.QueryType;
import lombok.*;

import javax.persistence.*;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static java.time.LocalDateTime.now;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;


@Data
@NoArgsConstructor
@Entity
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@EntityListeners(RegisterPetListener.class)
public class Pet extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long seq;
  private String petName;
  private int petAge;
  @Enumerated(value = EnumType.STRING)
  private Gender petGender;
  private boolean neuteringYn;
  private String petIntroduce;
  private int likes;
  private int status;
  @Transient
  private boolean likesOfMe;
  @ManyToOne(optional = false,fetch = FetchType.LAZY)
  @ToString.Exclude
  private UserInfo user;




  public Pet(UserInfo user,String petName, Gender petGender,int petAge,boolean neuteringYn,  String petIntroduce) {
    this(null, user,petName, petIntroduce, petGender, petAge, neuteringYn, 0, false);
  }
  @Builder
  public Pet(Long seq, UserInfo user, String petName, String petIntroduce, Gender petGender,int petAge,boolean neuteringYn, int likes, boolean likesOfMe) {
    System.out.println(petIntroduce);
    System.out.println(petIntroduce.length());
    checkNotNull(petName, "강아지 이름을 입력해주세요");
    checkArgument(isNotEmpty(petIntroduce), "contents must be provided.");
    checkArgument( petAge>=0, "강아지의 나이를 입력해주세요");
    checkArgument(petGender!= null && petGender.name().equalsIgnoreCase("male") || petGender.name().equalsIgnoreCase("female"), "contents must be provided and 'male' or 'female'");
    checkArgument(
            petIntroduce.length() >= 2 && petIntroduce.length() <= 500,
      "자기소개는 두 글자 이상 and 500자 이하로 적어주세요."
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
    this.status = 1;
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