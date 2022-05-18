package com.petbuddy.api.model.pet;

import com.petbuddy.api.model.user.UserInfo;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
public class Likes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long likedPetId;
    private Long targetPetId;

    public Likes(Long likedPetId, Long targetPetId) {
        this.likedPetId=likedPetId;
        this.targetPetId=targetPetId;
    }
}
