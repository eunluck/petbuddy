package com.petbuddy.api.controller.notification;

import com.petbuddy.api.controller.ApiResult;
import com.petbuddy.api.model.notification.Subscription;
import com.petbuddy.api.security.JwtAuthentication;
import com.petbuddy.api.service.notification.NotificationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
@Api(tags = "Push 구독 APIs")
public class SubscribeController {

  private final NotificationService notificationService;

  public SubscribeController(NotificationService notificationService) {
    this.notificationService = notificationService;
  }

  @PostMapping("/subscribe")
  @ApiOperation(value = "Push 구독")
  public ApiResult<Subscription> subscribe(@AuthenticationPrincipal JwtAuthentication authentication,
                                           @RequestBody Subscription subscription) {
    Subscription.SubscriptionBuilder subscriptionBuilder = new Subscription.SubscriptionBuilder(subscription);
    subscriptionBuilder.userId(authentication.id);

    Subscription subscribe = notificationService.subscribe(subscriptionBuilder.build());
    return ApiResult.OK(subscribe);
  }

}
