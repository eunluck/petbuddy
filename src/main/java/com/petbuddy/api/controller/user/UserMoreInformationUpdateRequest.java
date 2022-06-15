package com.petbuddy.api.controller.user;

import com.petbuddy.api.model.user.Address;
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
    @ApiModelProperty(value = "인증 완료된 휴대폰 번호")
    private String phone;

}
