package com.petbuddy.api.controller.matching;


import com.petbuddy.api.controller.ApiResult;
import com.petbuddy.api.controller.pet.PetDto;
import com.petbuddy.api.security.JwtAuthentication;
import com.petbuddy.api.service.matching.MatchingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api")
@Api(tags = "메인화면 펫 매칭 APIs")
public class MatchingController {


    private final MatchingService matchingService;

    @GetMapping("/pets")
    @ApiOperation(value = "추천 강아지 리스트")
    public ApiResult<List<PetDto>> searchingPets(@AuthenticationPrincipal JwtAuthentication authentication) {

        return ApiResult.OK(matchingService.findMatchingPets(authentication.id));
    }

}
