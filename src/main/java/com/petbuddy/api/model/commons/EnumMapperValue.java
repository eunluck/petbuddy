package com.petbuddy.api.model.commons;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class EnumMapperValue {
    private String code;
    private String title;

    public EnumMapperValue(EnumMapperType enumMapperType){
        code = enumMapperType.getCode();
        title = enumMapperType.getTitle();
    }
}
