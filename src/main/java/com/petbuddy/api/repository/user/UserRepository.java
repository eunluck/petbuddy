package com.petbuddy.api.repository.user;

import com.petbuddy.api.model.user.Email;
import com.petbuddy.api.model.user.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserInfo,Long>{

    Optional<UserInfo> findById(Long userId);

    Optional<UserInfo> findByEmail(Email email);

}
