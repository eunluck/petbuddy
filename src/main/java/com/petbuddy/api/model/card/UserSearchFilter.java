package com.petbuddy.api.model.card;

import com.petbuddy.api.controller.user.UserSearchFilterUpdateRequest;
import com.petbuddy.api.model.commons.BaseEntity;
import com.petbuddy.api.model.user.Gender;
import com.petbuddy.api.model.user.UserInfo;
import com.petbuddy.api.util.DateTimeUtils;
import lombok.*;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class UserSearchFilter extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;
    @OneToOne
    @ToString.Exclude
    private UserInfo userInfo;
    @Enumerated(value = EnumType.STRING)
    private Gender gender;
    private Boolean neuteringYn;
    private Integer minAge;
    private Integer maxAge;
    @Enumerated(value = EnumType.STRING)
    private Gender petGender;
    private String petBreed;
    private String petSize;

 /*   public Optional<Gender> getGender() {
        return Optional.of(gender);
    }

    public Optional<Boolean> getNeuteringYn() {
        return Optional.of(neuteringYn);
    }

    public Optional<Integer> getMinAge() {
        return Optional.of(minAge);
    }

    public Optional<Integer> getMaxAge() {
        return Optional.of(maxAge);
    }

    public Optional<Gender> getPetGender() {
        return Optional.of(petGender);
    }

    public Optional<String> getPetBreed() {
        return Optional.of(petBreed);
    }

    public Optional<String> getPetSize() {
        return Optional.of(petSize);
    }
*/
    public void update(UserSearchFilterUpdateRequest userSearchFilterUpdateRequest){
        BeanUtils.copyProperties(userSearchFilterUpdateRequest,this);
    }

    public Integer minAgeOfBirth(){

        return DateTimeUtils.ageYearCalculation(this.minAge);
    }
    public Integer maxAgeOfBirth(){

        return DateTimeUtils.ageYearCalculation(this.maxAge);
    }
}
