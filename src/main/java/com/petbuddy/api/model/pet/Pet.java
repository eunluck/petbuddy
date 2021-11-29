package com.petbuddy.api.model.pet;

import com.petbuddy.api.model.Auditable;
import com.petbuddy.api.model.MyEntityListener;
import com.petbuddy.api.model.commons.Id;
import com.petbuddy.api.model.user.User;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EntityListeners;
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
public class Pet implements Auditable {

  private  Long seq;

  private  Id<User, Long> userId;

  private String petIntroduce;

  private int likes;

  private boolean likesOfMe;

  private  LocalDateTime createdAt;
  private  LocalDateTime updatedAt;

  public Pet(Id<User, Long> userId, String petIntroduce) {
    this(null, userId, petIntroduce, 0, false, null);
  }
  @Builder
  public Pet(Long seq, Id<User, Long> userId, String petIntroduce, int likes, boolean likesOfMe, LocalDateTime createdAt) {
    checkNotNull(userId, "userId must be provided.");
    checkArgument(isNotEmpty(petIntroduce), "contents must be provided.");
    checkArgument(
            petIntroduce.length() >= 4 && petIntroduce.length() <= 500,
      "post contents length must be between 4 and 500 characters."
    );

    this.seq = seq;
    this.userId = userId;
    this.petIntroduce = petIntroduce;
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