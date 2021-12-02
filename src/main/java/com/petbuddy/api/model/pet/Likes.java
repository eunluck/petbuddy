package com.petbuddy.api.model.pet;

import com.petbuddy.api.model.user.UserInfo;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
public class Likes {

    @Id
    @GeneratedValue
    private Long seq;
    private Long userId;
    private Long petId;

    public Likes(Long userId, Long petId) {
        this.userId=userId;
        this.petId=petId;
    }
}
