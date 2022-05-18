package com.petbuddy.api.model.notification;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;
import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;

@Entity
@Data
@NoArgsConstructor
public class Subscription {

    @javax.persistence.Id
    @GeneratedValue
    private Long id;

    private String notificationEndPoint;

    private String publicKey;

    private String auth;

    private Long userId;

    private LocalDateTime createAt;

    public Subscription(Long id, String notificationEndPoint, String publicKey, String auth,  Long userId) {
        this.id = id;
        this.notificationEndPoint = notificationEndPoint;
        this.publicKey = publicKey;
        this.auth = auth;
        this.userId = userId;
        this.createAt = defaultIfNull(createAt, now());
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
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
        private Long id;
        private String notificationEndPoint;
        private String publicKey;
        private String auth;
        private Long userId;
        private LocalDateTime createAt;

        public SubscriptionBuilder() {
        }

        public SubscriptionBuilder(Subscription subscription) {
            this.id = subscription.id;
            this.notificationEndPoint = subscription.notificationEndPoint;
            this.publicKey = subscription.publicKey;
            this.auth = subscription.auth;
            this.userId = subscription.userId;
            this.createAt = subscription.createAt;
        }

        public SubscriptionBuilder id(Long id) {
            this.id = id;
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

        public SubscriptionBuilder userId( Long userId) {
            this.userId = userId;
            return this;
        }

        public SubscriptionBuilder createAt(LocalDateTime createAt) {
            this.createAt = createAt;
            return this;
        }

        public Subscription build() {
            Subscription subscription = new Subscription(id, notificationEndPoint, publicKey, auth, userId);
            subscription.createAt = this.createAt;
            return subscription;
        }
    }

    @Override
    public String toString() {
        return "Subscription{" +
                "id=" + id +
                ", notificationEndPoint='" + notificationEndPoint + '\'' +
                ", publicKey='" + publicKey + '\'' +
                ", auth='" + auth + '\'' +
                ", userId=" + userId +
                ", createAt=" + createAt +
                '}';
    }

}
