package com.petbuddy.api.model.pet;

import com.beust.jcommander.internal.Lists;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.petbuddy.api.model.commons.BaseEntity;
import com.petbuddy.api.model.listener.RegisterPetListener;
import com.petbuddy.api.model.user.Gender;
import com.petbuddy.api.model.user.UserInfo;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static javax.persistence.FetchType.LAZY;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;


@Data
@NoArgsConstructor
@Entity
@EqualsAndHashCode(callSuper = true)
@SQLDelete(sql = "UPDATE pet SET deleted_at = NOW() WHERE id = ?")
@Where(clause = "deleted_at IS NULL")
@EntityListeners(RegisterPetListener.class)
public class Pet extends BaseEntity {

  private String petName;
  private int petAge;
  @Enumerated(value = EnumType.STRING)
  private Gender petGender;
  private boolean neuteringYn;
  @Lob
  private String petIntroduce;
  private int likes;
  private int status;
  @Transient
  private boolean likesOfMe;
  @JsonBackReference
  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "user_id")
  private UserInfo user;
  @OneToMany(mappedBy = "pet", cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonManagedReference
  private List<PetImage> petImages = Lists.newArrayList();


  public Pet(UserInfo user,String petName, Gender petGender,int petAge,boolean neuteringYn,  String petIntroduce) {
    this( user,petName, petIntroduce, petGender, petAge, neuteringYn, 0, false);
  }
  @Builder
  public Pet( UserInfo user, String petName, String petIntroduce, Gender petGender,int petAge,boolean neuteringYn, int likes, boolean likesOfMe) {
    checkNotNull(petName, "강아지 이름을 입력해주세요");
    checkArgument(isNotEmpty(petIntroduce), "contents must be provided.");
    checkArgument( petAge>=0, "강아지의 나이를 입력해주세요");
    checkArgument(petGender!= null &&petGender.name().equalsIgnoreCase("male") || petGender.name().equalsIgnoreCase("female"), "contents must be provided and 'male' or 'female'");
    checkArgument(
            petIntroduce.length() >= 2 && petIntroduce.length() <= 500,
      "자기소개는 두 글자 이상 and 500자 이하로 적어주세요."
    );

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


  public void addImages(List<PetImage> petImages){
    petImages.forEach(petImage -> petImage.setPet(this));
    this.petImages.addAll(petImages);
  }
}