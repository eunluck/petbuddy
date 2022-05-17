package com.petbuddy.api.model.user;


import com.google.common.base.Strings;

public enum Gender {
    MALE,
    FEMALE;


    public static Gender of(String name) {
        if (Strings.isNullOrEmpty(name)) {
            return null;
        }
        if (name.equalsIgnoreCase(MALE.name())) {

            return MALE;
        } else if (name.equalsIgnoreCase(FEMALE.name())) {
            return FEMALE;
        }
        return null;
    }
}
