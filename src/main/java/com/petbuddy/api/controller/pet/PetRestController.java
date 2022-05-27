package com.petbuddy.api.controller.pet;

import com.petbuddy.api.controller.ApiResult;
import com.petbuddy.api.error.NotFoundException;
import com.petbuddy.api.model.commons.AttachedFile;
import com.petbuddy.api.model.commons.EnumMapper;
import com.petbuddy.api.model.commons.EnumMapperValue;
import com.petbuddy.api.model.user.UserInfo;
import com.petbuddy.api.security.JwtAuthentication;
import com.petbuddy.api.service.pet.PetService;
import com.petbuddy.api.service.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequiredArgsConstructor
@RequestMapping("api")
@Api(tags = "펫 APIs")
public class PetRestController {

    private final PetService petService;
    private final UserService userService;
    private final EnumMapper enumMapper;

    @GetMapping("/personality")
    @ApiOperation(value = "강아지 성격 코드 조회")
    public List<EnumMapperValue> getPersonality(){
        return enumMapper.get("PersonalityType");
    }



    @PutMapping(path = "pet/{petId}")
    @ApiOperation(value = "강아지 정보 수정")
    public ApiResult<PetDto> updatePet(
            @AuthenticationPrincipal JwtAuthentication authentication,
            @RequestBody RegisterPetRequest request,
            @PathVariable Long petId
    ) {

        UserInfo loginUser = userService.findById(authentication.id).orElseThrow(() -> new NotFoundException(Long.class, authentication.id));

        return ApiResult.OK(
                new PetDto(
                        petService.updatePet(petId,
                                request,
                                loginUser.getId()
                        )
                )
        );
    }

    @PostMapping(path = "pet")
    @ApiOperation(value = "강아지 등록")
    public ApiResult<PetDto> posting(
            @AuthenticationPrincipal JwtAuthentication authentication,
            @RequestBody RegisterPetRequest request
    ) {

        UserInfo loginUser = userService.findById(authentication.id).orElseThrow(() -> new NotFoundException(Long.class, authentication.id));

        return ApiResult.OK(
                new PetDto(
                        petService.register(
                                request,loginUser
                        )
                )
        );
    }

    @PostMapping(path = "pet/images",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiOperation(value = "이미지 등록")
    public ApiResult<List<PetImageDto>> petImageUpload(
            @AuthenticationPrincipal JwtAuthentication authentication,
            @RequestPart List<MultipartFile> files
    ) {
        return ApiResult.OK(PetImageDto.listOf(
                petService.uploadPetImages(
                        userService.findById(authentication.id).orElseThrow(() -> new NotFoundException(Long.class, authentication.id)),
                        AttachedFile.toAttachedFile(files))
        ));
    }

    @GetMapping(path = "user/pet/list")
    @ApiOperation(value = "강아지 목록 조회")
    public ApiResult<List<PetDto>> pets(
            @AuthenticationPrincipal JwtAuthentication authentication
    ) {
        return ApiResult.OK(
                petService.findAll(authentication.id).stream()
                        .map(PetDto::new)
                        .collect(toList())
        );
    }

    @PatchMapping(path = "pet/{petId}/like")
    @ApiOperation(value = "강아지 좋아요")
    public ApiResult<PetDto> like(
            @AuthenticationPrincipal JwtAuthentication authentication,
            @PathVariable @ApiParam(value = "대상 펫 PK", example = "1") Long petId
    ) {

        UserInfo userInfo = userService.findById(authentication.id).orElseThrow(() -> new NotFoundException(UserInfo.class, authentication.id));

        return ApiResult.OK(
                petService.like(petId, userInfo.getRepresentativePetId())
                        .orElseThrow(() -> new NotFoundException("petId:" + petId.toString(), "getRepresentativePetId:" + userInfo.getRepresentativePetId().toString()))
        );
    }

}