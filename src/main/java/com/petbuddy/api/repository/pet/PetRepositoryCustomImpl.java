package com.petbuddy.api.repository.pet;

import static com.petbuddy.api.model.pet.QPet.pet;
import static com.petbuddy.api.model.user.QUserInfo.userInfo;

import com.petbuddy.api.model.pet.Pet;
import com.petbuddy.api.model.user.Gender;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PetRepositoryCustomImpl implements PetRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    @Override
    public List<Pet> findFilteringMatchingPets(Gender gender, Boolean neuteringYn, Integer minAge, Integer maxAge, Gender petGender, String petBreed, String petSize) {
        return queryFactory.selectFrom(pet).join(pet.user,userInfo)
                .where(
                        eqUserGender(gender),
                        eqPetGender(petGender),
                        eqNeuteringYn(neuteringYn)
                )
                .fetch();
    }


    private BooleanExpression eqUserGender(Gender gender) {
        return gender != null ? userInfo.gender.eq(gender) : null;
    }

    private BooleanExpression eqPetGender(Gender petGender) {
        return petGender != null ? pet.petGender.eq(petGender) : null;
    }

    private BooleanExpression eqNeuteringYn(Boolean neuteringYn) {

        return neuteringYn != null? pet.neuteringYn.eq(neuteringYn):null;
    }
/*

    private BooleanExpression eqMinAge(LocalDate minBirth) {

        return minBirth != null? userInfo.birth.eq(minBirth):null;
    }




    private Predicate ageEq(Integer minAge, Integer maxAge){
        return
    }

 */
}
