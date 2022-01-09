package com.petbuddy.api.controller.pet;

import com.petbuddy.api.controller.ApiResult;
import com.petbuddy.api.error.NotFoundException;
import com.petbuddy.api.model.user.UserInfo;
import com.petbuddy.api.security.JwtAuthentication;
import com.petbuddy.api.service.pet.PetService;
import com.petbuddy.api.service.user.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

@RestController
@RequiredArgsConstructor
@RequestMapping("api")
public class PetRestController {

  private final PetService petService;
  private final UserService userService;

  @PostMapping(path = "pet")
  @ApiOperation(value = "강아지 등록")
  public ApiResult<PetDto> posting(
    @AuthenticationPrincipal JwtAuthentication authentication,
    @RequestBody RegisterPetRequest request
  ) {

    return ApiResult.OK(
      new PetDto(
        petService.register(
          request.newPet(userService.findById(authentication.id).orElseThrow(() -> new NotFoundException(Long.class,authentication.id)))
        )
      )
    );
  }

  @GetMapping(path = "user/pet/list")
  @ApiOperation(value = "강아지 목록 조회")
  public ApiResult<List<PetDto>> pets(
    @AuthenticationPrincipal JwtAuthentication authentication
  ) {
    return ApiResult.OK(
      petService.findAll( authentication.id).stream()
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

    UserInfo userInfo = userService.findById(authentication.id).orElseThrow(() -> new NotFoundException(UserInfo.class,authentication.id));

    return ApiResult.OK(
      petService.like(petId, userInfo.getRepresentativePetSeq())
        .map(PetDto::new)
        .orElseThrow(() -> new NotFoundException("petId:"+petId.toString(),"getRepresentativePetSeq:"+  userInfo.getRepresentativePetSeq().toString()))
    );
  }

}