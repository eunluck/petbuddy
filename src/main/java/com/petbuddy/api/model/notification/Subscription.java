package com.petbuddy.api.model.notification;

import com.petbuddy.api.model.commons.Id;
import com.petbuddy.api.model.user.User;

import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;
import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;

public class Subscription {

    private Long seq;

    private String notificationEndPoint;

    private String publicKey;

    private String auth;

    private Id<User, Long> userId;

    private LocalDateTime createAt;

    protected Subscription() {
    }

    public Subscription(Long seq, String notificationEndPoint, String publicKey, String auth, Id<User, Long> userId) {
        this.seq = seq;
        this.notificationEndPoint = notificationEndPoint;
        this.publicKey = publicKey;
        this.auth = auth;
        this.userId = userId;
        this.createAt = defaultIfNull(createAt, now());
    }

    public Long getSeq() {
        return seq;
    }

    public Id<User, Long> getUserId() {
        return userId;
    }

    public String getNotificationEndPoint() {
        return notificationEndPoint;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public String getAuth() {
        return auth;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public static final class SubscriptionBuilder {
        private Long seq;
        private String notificationEndPoint;
        private String publicKey;
        private String auth;
        private Id<User, Long> userId;
        private LocalDateTime createAt;

        public SubscriptionBuilder() {
        }

        public SubscriptionBuilder(Subscription subscription) {
            this.seq = subscription.seq;
            this.notificationEndPoint = subscription.notificationEndPoint;
            this.publicKey = subscription.publicKey;
            this.auth = subscription.auth;
            this.userId = subscription.userId;
            this.createAt = subscription.createAt;
        }

        public SubscriptionBuilder seq(Long seq) {
            this.seq = seq;
            return this;
        }

        public SubscriptionBuilder notificationEndPoint(String notificationEndPoint) {
            this.notificationEndPoint = notificationEndPoint;
            return this;
        }

        public SubscriptionBuilder publicKey(String publicKey) {
            this.publicKey = publicKey;
            return this;
        }

        public SubscriptionBuilder auth(String auth) {
            this.auth = auth;
            return this;
        }

        public SubscriptionBuilder userId(Id<User, Long> userId) {
            this.userId = userId;
            return this;
        }

        public SubscriptionBuilder createAt(LocalDateTime createAt) {
            this.createAt = createAt;
            return this;
        }

        public Subscription build() {
            Subscription subscription = new Subscription(seq, notificationEndPoint, publicKey, auth, userId);
            subscription.createAt = this.createAt;
            return subscription;
        }
    }

    @Override
    public String toString() {
        return "Subscription{" +
                "seq=" + seq +
                ", notificationEndPoint='" + notificationEndPoint + '\'' +
                ", publicKey='" + publicKey + '\'' +
                ", auth='" + auth + '\'' +
                ", userId=" + userId +
                ", createAt=" + createAt +
                '}';
    }

}
