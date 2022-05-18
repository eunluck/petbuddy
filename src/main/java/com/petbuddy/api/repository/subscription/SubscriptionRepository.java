package com.petbuddy.api.repository.subscription;

import com.petbuddy.api.model.notification.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription,Long> {

    Optional<Subscription> findById(Long id);

    Subscription save(Subscription user);

    Optional<Subscription> findByUserId(Long userId);

    List<Subscription> findAll();

}
