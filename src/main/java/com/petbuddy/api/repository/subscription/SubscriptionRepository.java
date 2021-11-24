package com.petbuddy.api.repository.subscription;

import com.petbuddy.api.model.notification.Subscription;

import java.util.List;
import java.util.Optional;

public interface SubscriptionRepository {

    Subscription findById(Long seq);

    Subscription save(Subscription user);

    Optional<Subscription> findByUserSeq(Long userSeq);

    List<Subscription> findAll();

}
