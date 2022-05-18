package com.petbuddy.api.controller.user;

import com.petbuddy.api.controller.ApiResult;
import com.petbuddy.api.error.NotFoundException;
import com.petbuddy.api.model.card.UserSearchFilter;
import com.petbuddy.api.model.user.Email;
import com.petbuddy.api.model.user.Gender;
import com.petbuddy.api.model.user.Role;
import com.petbuddy.api.model.user.UserInfo;
import com.petbuddy.api.security.Jwt;
import com.petbuddy.api.security.JwtAuthentication;
import com.petbuddy.api.service.user.UserService;
import com.petbuddy.api.util.ImageUploader;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

import static com.petbuddy.api.model.commons.AttachedFile.toAttachedFile;
import static java.util.concurrent.CompletableFuture.supplyAsync;

@RestController
@RequestMapping("api")
@Api(tags = "사용자 APIs")
public class UserRestController {

  private final Logger log = LoggerFactory.getLogger(getClass());

  private final Jwt jwt;

  private final ImageUploader imageUploader;

  private final UserService userService;

  public UserRestController(Jwt jwt, ImageUploader imageUploader, UserService userService) {
    this.jwt = jwt;
    this.imageUploader = imageUploader;
    this.userService = userService;
  }

  @PostMapping(path = "user/exists")
  @ApiOperation(value = "이메일 중복확인 (API 토큰 필요없음)")
  public ApiResult<Boolean> checkEmail(
    @RequestBody @ApiParam(value = "example: {\"email\": \"test00@gmail.com\",\"emailType\":\"user\"}") Map<String, String> request
  ) {
    Email email = new Email(request.get("email"),request.get("emailType"));
    return ApiResult.OK(
      userService.findByEmail(email).isPresent()
    );
  }



  @PostMapping(path = "user/join", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  @ApiOperation(value = "회원가입 (API 토큰 필요없음)")
  public ApiResult<JoinResult> join(
    @ModelAttribute JoinRequest joinRequest,
    @RequestPart(required = false) MultipartFile file
  ) {
    UserInfo userInfo = userService.join(
      joinRequest.getName(),
      new Email(joinRequest.getPrincipal(),joinRequest.getEmailType()),
      joinRequest.getCredentials(),
      Gender.of(joinRequest.getGender())
    );

    toAttachedFile(file).ifPresent(attachedFile ->
      supplyAsync(() ->
              imageUploader.uploadProfileImage(attachedFile)
      ).thenAccept(opt ->
        opt.ifPresent(profileImageUrl ->
          // 이미지가 정상적으로 업로드가 완료된 경우 (profileImageUrl != null)
          userService.updateProfileImage(userInfo.getId(), profileImageUrl)
        )
      )
    );

    // supplyAsync 실행 완료 여부와 관계 없이 리턴한다.
    String apiToken = userInfo.newApiToken(jwt, new String[]{Role.USER.value()});
    return ApiResult.OK(
      new JoinResult(apiToken, userInfo)
    );
  }

  @GetMapping(path = "user/me")
  @ApiOperation(value = "내 정보")
  public ApiResult<UserDto> me(@AuthenticationPrincipal JwtAuthentication authentication) {
    return ApiResult.OK(
      userService.findById(authentication.id)
        .map(UserDto::new)
        .orElseThrow(() -> new NotFoundException(UserInfo.class, authentication.id))
    );
  }

  @PutMapping(path = "user/phone")
  @ApiOperation(value = "휴대폰 번호 변경")
  public ApiResult<UserInfo> updatePhoneNumber(@AuthenticationPrincipal JwtAuthentication authentication,
                                                          @RequestBody String phoneNumber) {

    return ApiResult.OK(userService.updateUserPhoneNumber(authentication.id,phoneNumber));
  }




  @PutMapping(path = "user/more")
  @ApiOperation(value = "추가정보입력")
  public ApiResult<UserInfo> updateMoreInformation(@AuthenticationPrincipal JwtAuthentication authentication,
                                                       @RequestBody UserMoreInformationUpdateRequest request) {

    return ApiResult.OK(userService.updateUserMoreInformation(authentication.id,request));
  }



  @PutMapping(path = "user/filter")
  @ApiOperation(value = "매칭 필터 설정")
  public ApiResult<UserSearchFilter> updateMatchingFilter(@AuthenticationPrincipal JwtAuthentication authentication,
                                                          @RequestBody UserSearchFilterUpdateRequest request) {

    return ApiResult.OK(userService.updateUserFilter(authentication.id,request));
  }

}