package com.petbuddy.api.controller.user;

import com.petbuddy.api.model.user.Address;
import com.petbuddy.api.model.user.Gender;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class UserMoreInformationUpdateRequest {

    @ApiModelProperty(value = "생일")
    private LocalDate birth;

    @ApiModelProperty(value = "주소")
    private Address address;

}
