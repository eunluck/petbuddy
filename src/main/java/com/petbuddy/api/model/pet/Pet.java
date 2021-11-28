package com.petbuddy.api.model.pet;

import com.petbuddy.api.model.commons.Id;
import com.petbuddy.api.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static java.time.LocalDateTime.now;
import static java.util.Optional.ofNullable;
import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

@Entity
@NoArgsConstructor
public class Pet {
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private  Long seq;
    @ManyToOne
    private User userId;
    private String petIntroduce;
    private int likes;
    @Transient
    private boolean likesOfMe;
    //private final Writer writer;
    private LocalDateTime createAt;
    private LocalDateTime modifyAt;

    public Pet(User userId, String contents) {
        this(null, userId, contents, 0, false, null);
    }


    public Pet(Long seq, User userId, String petIntroduce, int likes, boolean likesOfMe, LocalDateTime createAt) {
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
        this.createAt = defaultIfNull(createAt, now());
    }

    public void petIntroduceModify(String petIntroduce) {
        checkArgument(isNotEmpty(petIntroduce), "contents must be provided.");
        checkArgument(
                petIntroduce.length() >= 4 && petIntroduce.length() <= 500,
                "post contents length must be between 4 and 500 characters."
        );
        this.modifyAt = now();
        this.petIntroduce = petIntroduce;
    }

    public int incrementAndGetLikes() {
        likesOfMe = true;
        return ++likes;
    }
/*
  public int incrementAndGetComments() {
    return ++comments;
  }*/

    public Long getSeq() {
        return seq;
    }

    public User getUserId() {
        return userId;
    }


    public int getLikes() {
        return likes;
    }

    public boolean isLikesOfMe() {
        return likesOfMe;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public String getPetIntroduce() {
        return petIntroduce;
    }

    public void setPetIntroduce(String petIntroduce) {
        this.petIntroduce = petIntroduce;
    }

    public LocalDateTime getModifyAt() {
        return modifyAt;
    }

    public void setModifyAt(LocalDateTime modifyAt) {
        this.modifyAt = modifyAt;
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

    static public class Builder {
        private Long seq;
        private User userId;
        private String petIntroduce;
        private int likes;
        private boolean likesOfMe;
        private LocalDateTime createAt;

        public Builder() {
        }


        public Builder(Pet pet) {
            this.seq = pet.seq;
            this.userId = pet.userId;
            this.petIntroduce = pet.petIntroduce;
            this.likes = pet.likes;
            this.likesOfMe = pet.likesOfMe;
            this.createAt = pet.createAt;
        }

        public Builder seq(Long seq) {
            this.seq = seq;
            return this;
        }

        public Builder userId(User userId) {
            this.userId = userId;
            return this;
        }

        public Builder petIntroduce(String petIntroduce) {
            this.petIntroduce = petIntroduce;
            return this;
        }

        public Builder likes(int likes) {
            this.likes = likes;
            return this;
        }

        public Builder likesOfMe(boolean likesOfMe) {
            this.likesOfMe = likesOfMe;
            return this;
        }


        public Builder createAt(LocalDateTime createAt) {
            this.createAt = createAt;
            return this;
        }

        public Pet build() {
            return new Pet(seq, userId, petIntroduce, likes, likesOfMe, createAt);
        }
    }


}