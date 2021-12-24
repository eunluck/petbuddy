package com.petbuddy.api.model.listener;


import com.petbuddy.api.model.card.UserSearchFilter;
import com.petbuddy.api.model.user.UserInfo;
import com.petbuddy.api.repository.user.UserSearchFilterRepository;
import com.petbuddy.api.util.BeanUtils;

import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;

public class UserSearchFilterListener {
    @PostPersist
    public void prePersistAndPreUpdate(Object o) {
         UserSearchFilterRepository userSearchFilterRepository = BeanUtils.getBean(UserSearchFilterRepository.class);

        UserInfo user = (UserInfo) o;

        userSearchFilterRepository.save(UserSearchFilter
                .builder()
                .user(user)
                .build());

    }
}
