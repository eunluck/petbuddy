package com.petbuddy.api.repository.pet;

import static com.petbuddy.api.model.pet.QPet.pet;
import static com.petbuddy.api.model.user.QUserInfo.userInfo;
import static com.petbuddy.api.model.pet.QLikes.likes;

import com.petbuddy.api.controller.pet.QPetDto;
import com.petbuddy.api.model.card.UserSearchFilter;
import com.petbuddy.api.controller.pet.PetDto;
import com.petbuddy.api.model.user.Gender;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PetRepositoryCustomImpl implements PetRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<PetDto> findBySeq(Long petId, Long likedPetId) {
        return Optional.ofNullable(queryFactory.select(new QPetDto(pet.seq,pet.petName,pet.petAge,pet.petGender,pet.neuteringYn,pet.petIntroduce,pet.likes,pet.status,likes.seq,pet.createdAt,userInfo))
                .from(pet)
                .join(pet.user,userInfo)
                .leftJoin(likes)
                .on(pet.seq.eq(likes.targetPetId)
                        .and(likes.likedPetId.eq(likedPetId)))
                .where(pet.seq.eq(petId)).fetchOne());
    }

    @Override
    public List<PetDto> findFilteringMatchingPets(UserSearchFilter userSearchFilter,Long likedPetId) {

        //return queryFactory.select(pet).from(pet,likes)
        return queryFactory.select(new QPetDto(pet.seq,pet.petName,pet.petAge,pet.petGender,pet.neuteringYn,pet.petIntroduce,pet.likes,pet.status,likes.seq,pet.createdAt,userInfo))
                .from(pet)
                .join(pet.user,userInfo)
                .leftJoin(likes)
                .on(pet.seq.eq(likes.targetPetId)
                        .and(likes.likedPetId.eq(likedPetId)))
                .where(
                        eqUserGender(userSearchFilter.getGender()),
                        eqPetGender(userSearchFilter.getPetGender()),
                        eqNeuteringYn(userSearchFilter.getNeuteringYn()),
                        betweenAge(userSearchFilter.getMinBirth(),userSearchFilter.getMaxBirth())
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

    private BooleanExpression betweenAge(LocalDate minBirth,LocalDate maxBirth) {

        return minBirth != null? userInfo.birth.between(minBirth,maxBirth):null;
    }

}
