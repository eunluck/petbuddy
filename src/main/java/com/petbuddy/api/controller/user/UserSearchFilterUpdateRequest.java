package com.petbuddy.api.controller.user;

import com.petbuddy.api.model.user.Gender;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserSearchFilterUpdateRequest {

    @ApiModelProperty(value = "성별")
    private Gender gender;

    @ApiModelProperty(value = "중성화 여부")
    private Boolean neuteringYn;

    @ApiModelProperty(value = "최소 나이")
    private Integer minAge;

    @ApiModelProperty(value = "최대 나이")
    private Integer maxAge;

    @ApiModelProperty(value = "펫 성별")
    private Gender petGender;

    @ApiModelProperty(value = "펫 종류")
    private String petBreed;

    @ApiModelProperty(value = "펫 사이즈 ")
    private String petSize;

}
