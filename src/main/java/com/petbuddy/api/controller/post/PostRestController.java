package com.petbuddy.api.controller.post;

import com.petbuddy.api.configure.support.Pageable;
import com.petbuddy.api.controller.ApiResult;
import com.petbuddy.api.error.NotFoundException;
import com.petbuddy.api.model.commons.Id;
import com.petbuddy.api.model.pet.Pet;
import com.petbuddy.api.model.user.UserInfo;
import com.petbuddy.api.security.JwtAuthentication;
import com.petbuddy.api.service.post.PostService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("api")
public class PostRestController {

  private final PostService postService;

  public PostRestController(PostService postService) {
    this.postService = postService;
  }

  @PostMapping(path = "pet")
  @ApiOperation(value = "강아지 등록")
  public ApiResult<PetDto> posting(
    @AuthenticationPrincipal JwtAuthentication authentication,
    @RequestBody RegisterPetRequest request
  ) {
    return ApiResult.OK(
      new PetDto(
        postService.write(
          request.newPet(authentication.id)
        )
      )
    );
  }

  @GetMapping(path = "user/pet/list")
  @ApiOperation(value = "강아지 목록 조회")
  @ApiImplicitParams({
    @ApiImplicitParam(name = "offset", dataType = "integer", paramType = "query", defaultValue = "0", value = "페이징 offset"),
    @ApiImplicitParam(name = "limit", dataType = "integer", paramType = "query", defaultValue = "20", value = "최대 조회 갯수")
  })
  public ApiResult<List<PetDto>> pets(
    @AuthenticationPrincipal JwtAuthentication authentication,
    Pageable pageable
  ) {
    return ApiResult.OK(
      postService.findAll( authentication.id, PageRequest.of(Long.valueOf(pageable.offset()).intValue(),pageable.limit())).stream()
        .map(PetDto::new)
        .collect(toList())
    );
  }

  @PatchMapping(path = "user/{userId}/pet/{petId}/like")
  @ApiOperation(value = "강아지 좋아요")
  public ApiResult<PetDto> like(
    @AuthenticationPrincipal JwtAuthentication authentication,
    @PathVariable @ApiParam(value = "조회대상자 PK (본인 )", example = "1") Long userId,
    @PathVariable @ApiParam(value = "대상 포스트 PK", example = "1") Long petId
  ) {
    return ApiResult.OK(
      postService.like(Id.of(Pet.class, petId), Id.of(UserInfo.class, userId), authentication.id)
        .map(PetDto::new)
        .orElseThrow(() -> new NotFoundException(Pet.class, Id.of(Pet.class, petId), Id.of(UserInfo.class, userId)))
    );
  }

}