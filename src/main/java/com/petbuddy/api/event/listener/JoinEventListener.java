package com.petbuddy.api.event.listener;

import com.petbuddy.api.event.JoinEvent;
import com.petbuddy.api.model.commons.Id;
import com.petbuddy.api.model.notification.PushMessage;
import com.petbuddy.api.model.user.User;
import com.petbuddy.api.service.notification.NotificationService;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;

public class JoinEventListener implements AutoCloseable {

  private Logger log = LoggerFactory.getLogger(JoinEventListener.class);

  private EventBus eventBus;

  private final NotificationService notificationService;

  private final KafkaTemplate<String, String> kafkaTemplate;

  public JoinEventListener(EventBus eventBus, NotificationService notificationService, KafkaTemplate<String, String> kafkaTemplate) {
    this.eventBus = eventBus;
    this.notificationService = notificationService;
    this.kafkaTemplate = kafkaTemplate;
    eventBus.register(this);
  }

  @Subscribe
  public void handleJoinEvent(JoinEvent event) {
    String name = event.getName();
    Id<User, Long> userId = event.getUserId();
    log.info("user {}, userId {} joined!", name, userId);

    try {
      log.info("Try to send push for {}", event);
      notificationService.notifyAll(new PushMessage(
          name + " Joined!",
          "/friends/" + userId.value(),
          "Please send welcome message"
      ));
    } catch (Exception e) {
      log.error("Got error while handling event JoinEvent " + event.toString(), e);
      e.printStackTrace();
    }
  }

  @Override
  public void close() throws Exception {
    eventBus.unregister(this);
  }

}
