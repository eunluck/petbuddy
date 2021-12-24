package com.petbuddy.api.model.card;

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
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.util.Optional;

@Entity
@Data
@Builder
@NoArgsConstructor
@DynamicUpdate
public class UserSearchFilter extends BaseEntity {

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

    public Optional<Gender> getGender() {
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
}
