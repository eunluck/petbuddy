package com.petbuddy.api.repository.user;

import com.petbuddy.api.model.commons.Id;
import com.petbuddy.api.model.user.ConnectedUser;
import com.petbuddy.api.model.user.Email;
import com.petbuddy.api.model.user.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository {

    User insert(User user);

    void update(User user);

    Optional<User> findById(Id<User, Long> userId);

    Optional<User> findByEmail(Email email);

    List<ConnectedUser> findAllConnectedUser(Id<User, Long> userId);

    List<Id<User, Long>> findConnectedIds(Id<User, Long> userId);

}
