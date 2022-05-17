package com.petbuddy.api.service.notification;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.petbuddy.api.model.notification.PushMessage;
import com.petbuddy.api.model.notification.Subscription;
import com.petbuddy.api.model.user.UserInfo;
import com.petbuddy.api.repository.subscription.SubscriptionRepository;
import nl.martijndwars.webpush.Notification;
import nl.martijndwars.webpush.PushService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotificationService {

    private final Logger log = LoggerFactory.getLogger(NotificationService.class);

    private final PushService pushService;

    private final ObjectMapper objectMapper;

    private final SubscriptionRepository subscriptionRepository;

    public NotificationService(
            PushService pushService,
            ObjectMapper objectMapper,
            SubscriptionRepository subscriptionRepository
    ) {
        this.pushService = pushService;
        this.objectMapper = objectMapper;
        this.subscriptionRepository = subscriptionRepository;
    }


    public Subscription subscribe(Subscription subscription) {
        return subscriptionRepository.save(subscription);
    }

    public PushMessage notifyUser(UserInfo userInfo, PushMessage message) throws Exception {
        Optional<Subscription> maybeSubscription = subscriptionRepository.findByUserId(userInfo.getSeq());

        if (!maybeSubscription.isPresent()) {
            log.info("Can not send message to user {} because not found any subscription", userInfo);
            return null;
        }

        Subscription subscription = maybeSubscription.get();
        log.info("TRY TO SEND {} to SUBSCRIPTION {}", message, subscription);

        Notification notification = new Notification(
                subscription.getNotificationEndPoint(),
                subscription.getPublicKey(),
                subscription.getAuth(),
                objectMapper.writeValueAsBytes(message));

        pushService.send(notification);

        return message;
    }

    public PushMessage notifyAll(PushMessage message) throws Exception {
        List<Subscription> subscriptions = subscriptionRepository.findAll();
        for (Subscription subscription : subscriptions) {
            Notification notification = new Notification(
                    subscription.getNotificationEndPoint(),
                    subscription.getPublicKey(),
                    subscription.getAuth(),
                    objectMapper.writeValueAsBytes(message));
            pushService.send(notification);
        }

        return message;
    }

}