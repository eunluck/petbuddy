package com.petbuddy.api.model.card;

import com.petbuddy.api.model.user.Gender;
import com.petbuddy.api.model.user.UserInfo;
import lombok.Data;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
@Data
public class SearchFilterEntity {

    @Id
    private Long seq;
    @OneToOne
    @ToString.Exclude
    private UserInfo user;
    private Gender gender;
    private Boolean neuteringYn;
    private Integer minAge;
    private Integer maxAge;
    private Gender petGender;
    private String petBreed;
    private String petSize;

}
