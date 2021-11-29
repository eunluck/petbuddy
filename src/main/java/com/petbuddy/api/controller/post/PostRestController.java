package com.petbuddy.api.controller.post;

import com.petbuddy.api.configure.support.Pageable;
import com.petbuddy.api.controller.ApiResult;
import com.petbuddy.api.error.NotFoundException;
import com.petbuddy.api.model.commons.Id;
import com.petbuddy.api.model.pet.Pet;
import com.petbuddy.api.model.pet.Writer;
import com.petbuddy.api.model.user.User;
import com.petbuddy.api.security.JwtAuthentication;
import com.petbuddy.api.service.post.CommentService;
import com.petbuddy.api.service.post.PostService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("api")
public class PostRestController {

  private final PostService postService;

  private final CommentService commentService;

  public PostRestController(PostService postService, CommentService commentService) {
    this.postService = postService;
    this.commentService = commentService;
  }

  @PostMapping(path = "post")
  public ApiResult<PetDto> posting(
    @AuthenticationPrincipal JwtAuthentication authentication,
    @RequestBody PostingRequest request
  ) {
    return ApiResult.OK(
      new PetDto(
        postService.write(
          request.newPost(authentication.id)
        )
      )
    );
  }

  @GetMapping(path = "user/{userId}/post/list")
  @ApiOperation(value = "포스트 목록 조회")
  @ApiImplicitParams({
    @ApiImplicitParam(name = "offset", dataType = "integer", paramType = "query", defaultValue = "0", value = "페이징 offset"),
    @ApiImplicitParam(name = "limit", dataType = "integer", paramType = "query", defaultValue = "20", value = "최대 조회 갯수")
  })
  public ApiResult<List<PetDto>> posts(
    @AuthenticationPrincipal JwtAuthentication authentication,
    @PathVariable @ApiParam(value = "조회대상자 PK (본인 또는 친구)", example = "1") Long userId,
    Pageable pageable
  ) {
    return ApiResult.OK(
      postService.findAll(Id.of(User.class, userId), authentication.id, pageable.offset(), pageable.limit()).stream()
        .map(PetDto::new)
        .collect(toList())
    );
  }

  @PatchMapping(path = "user/{userId}/post/{postId}/like")
  @ApiOperation(value = "포스트 좋아요")
  public ApiResult<PetDto> like(
    @AuthenticationPrincipal JwtAuthentication authentication,
    @PathVariable @ApiParam(value = "조회대상자 PK (본인 또는 친구)", example = "1") Long userId,
    @PathVariable @ApiParam(value = "대상 포스트 PK", example = "1") Long postId
  ) {
    return ApiResult.OK(
      postService.like(Id.of(Pet.class, postId), Id.of(User.class, userId), authentication.id)
        .map(PetDto::new)
        .orElseThrow(() -> new NotFoundException(Pet.class, Id.of(Pet.class, postId), Id.of(User.class, userId)))
    );
  }

  @PostMapping(path = "user/{userId}/post/{postId}/comment")
  public ApiResult<CommentDto> comment(
    @AuthenticationPrincipal JwtAuthentication authentication,
    @PathVariable @ApiParam(value = "조회 대상자 PK (본인 또는 친구)", example = "1") Long userId,
    @PathVariable @ApiParam(value = "대상 포스트 PK", example = "1") Long postId,
    @RequestBody CommentRequest request
  ) {
    return ApiResult.OK(
      new CommentDto(
        commentService.write(
          Id.of(Pet.class, postId),
          Id.of(User.class, userId),
          authentication.id,
          request.newComment(
            authentication.id, Id.of(Pet.class, postId),
            new Writer(authentication.email, authentication.name)
          )
        )
      )
    );
  }

  @GetMapping(path = "user/{userId}/post/{postId}/comment/list")
  public ApiResult<List<CommentDto>> comments(
    @AuthenticationPrincipal JwtAuthentication authentication,
    @PathVariable @ApiParam(value = "조회대상자 PK (본인 또는 친구)", example = "1") Long userId,
    @PathVariable @ApiParam(value = "대상 포스트 PK", example = "1") Long postId
  ) {
    return ApiResult.OK(
      commentService.findAll(Id.of(Pet.class, postId), Id.of(User.class, userId), authentication.id).stream()
        .map(CommentDto::new)
        .collect(toList())
    );
  }

}