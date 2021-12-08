package com.petbuddy.api.model.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor
@Data
public class Address {

    @ApiModelProperty(value = "지번 주소", required = true)
    private String addressJb;
    @ApiModelProperty(value = "도로명 주소", required = true)
    private String addressSt;
    @ApiModelProperty(value = "기타")
    private String addressEtc;


}
