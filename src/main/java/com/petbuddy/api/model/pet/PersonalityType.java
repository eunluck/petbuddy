package com.petbuddy.api.model.pet;

import com.petbuddy.api.model.commons.EnumMapperType;
import lombok.ToString;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@ToString
public enum PersonalityType implements EnumMapperType {

    SNACKER("간식쟁이"),
    NEUTERING("중성화"),
    WALKING_RUBBER("산책러버"),
    POTTY_TRAINING("배변훈련"),
    EATER("먹보"),
    I_LIKE_TO_PLAY_BALL("공놀이 좋아"),
    VACCINATION_COMPLETED("예방접종 완료"),
    FROM_STREET("스트릿출신"),
    I_LIKE_TOYS("장난감 좋아"),
    GOOD_FRIENDLINESS("친화력 굿"),
    I_HAVE_A_BITE("입질이 있어요"),
    I_COVER_MY_FACE("낯을 가려요"),
    TROUBLEMAKER("말썽쟁이"),
    I_LIKE_PEOPLE("사람 좋아");

    private String title;

    PersonalityType(String title){
        this.title = title;
    }

    @Override
    public String getCode() {
        return name();
    }

    @Override
    public String getTitle() {
        return title;
    }

    public static PersonalityType of(String personality){
        return Arrays.stream(PersonalityType.values())
                .filter(personalityType -> personalityType.getCode().equalsIgnoreCase(personality))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 코드입니다."));
    }

    public static List<PersonalityType> of(List<String> values) {
        if (CollectionUtils.isEmpty(values)){
            return null;
        } else{
            return values.stream().distinct().map(s -> of(s)).collect(Collectors.toList());
        }
    }


    public void setTitle(String title) {
        this.title = title;
    }
}
