package com.petbuddy.api.controller.post;

import com.petbuddy.api.configure.support.Pageable;
import com.petbuddy.api.controller.ApiResult;
import com.petbuddy.api.error.NotFoundException;
import com.petbuddy.api.security.JwtAuthentication;
import com.petbuddy.api.service.post.PostService;
import com.petbuddy.api.service.user.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequiredArgsConstructor
@RequestMapping("api")
public class PostRestController {

  private final PostService postService;
  private final UserService userService;

  @PostMapping(path = "pet")
  @ApiOperation(value = "강아지 등록")
  public ApiResult<PetDto> posting(
    @AuthenticationPrincipal JwtAuthentication authentication,
    @RequestBody RegisterPetRequest request
  ) {

    return ApiResult.OK(
      new PetDto(
        postService.write(
          request.newPet(userService.findById(authentication.id).orElseThrow(RuntimeException::new))
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
      postService.findAll( authentication.id).stream()
        .map(PetDto::new)
        .collect(toList())
    );
  }

  @PatchMapping(path = "user/{userId}/pet/{petId}/like")
  @ApiOperation(value = "강아지 좋아요")
  public ApiResult<PetDto> like(
    @AuthenticationPrincipal JwtAuthentication authentication,
    @PathVariable @ApiParam(value = "조회대상자 PK (본인 )", example = "1") Long userId,
    @PathVariable @ApiParam(value = "대상 펫 PK", example = "1") Long petId
  ) {
    return ApiResult.OK(
      postService.like(petId, userId, authentication.id)
        .map(PetDto::new)
        .orElseThrow(() -> new NotFoundException("petId:"+petId.toString(),"userId:"+  userId.toString()))
    );
  }

}