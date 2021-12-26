package com.petbuddy.api.controller.user;

import com.petbuddy.api.model.BaseEntity;
import com.petbuddy.api.model.user.Gender;
import com.petbuddy.api.model.user.UserInfo;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.util.Optional;

@Data
@NoArgsConstructor
public class UserSearchFilterUpdateRequest {

    private String gender;
    private Boolean neuteringYn;
    private Integer minAge;
    private Integer maxAge;
    private Gender petGender;
    private String petBreed;
    private String petSize;

}
