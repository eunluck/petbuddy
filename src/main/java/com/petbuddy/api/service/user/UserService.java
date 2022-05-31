package com.petbuddy.api.service.user;

import com.google.common.eventbus.EventBus;
import com.petbuddy.api.controller.user.UserMoreInformationUpdateRequest;
import com.petbuddy.api.controller.user.UserSearchFilterUpdateRequest;
import com.petbuddy.api.error.NotFoundException;
import com.petbuddy.api.model.card.UserSearchFilter;
import com.petbuddy.api.model.user.Email;
import com.petbuddy.api.model.user.Gender;
import com.petbuddy.api.model.user.UserInfo;
import com.petbuddy.api.repository.user.UserRepository;
import com.petbuddy.api.repository.user.UserSearchFilterRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

@Service
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserSearchFilterRepository userSearchFilterRepository;
    private final UserRepository userRepository;

    private final EventBus eventBus;

    public UserService(PasswordEncoder passwordEncoder, UserSearchFilterRepository userSearchFilterRepository, UserRepository userRepository, EventBus eventBus) {
        this.passwordEncoder = passwordEncoder;
        this.userSearchFilterRepository = userSearchFilterRepository;
        this.userRepository = userRepository;
        this.eventBus = eventBus;
    }

    @Transactional
    public UserInfo join(String name, Email email, String password, Gender gender) {
        checkArgument(isNotEmpty(password), "password must be provided.");
        checkArgument(
                password.length() >= 4 && password.length() <= 15,
                "password length must be between 4 and 15 characters."
        );
        checkArgument(
                !findByEmail(email).isPresent(),
                "이메일이 중복됩니다."
        );

        UserInfo userInfo = new UserInfo(name, email, passwordEncoder.encode(password), gender);
        UserInfo saved = insert(userInfo);
        userSearchFilterRepository.save(UserSearchFilter.createUserFilter(saved));

        // raise event
        //eventBus.post(new JoinEvent(saved));
        return saved;
    }

    @Transactional
    public UserInfo login(Email email, String password) {
        checkNotNull(password, "password must be provided.");

        UserInfo userInfo = findByEmail(email)
                .orElseThrow(() -> new NotFoundException(UserInfo.class, email));
        userInfo.login(passwordEncoder, password);
        userInfo.afterLoginSuccess();
        update(userInfo);
        return userInfo;
    }

    @Transactional
    public UserInfo updateProfileImage(Long userId, String profileImageUrl) {
        UserInfo userInfo = findById(userId)
                .orElseThrow(() -> new NotFoundException(UserInfo.class, userId));
        userInfo.updateProfileImage(profileImageUrl);
        update(userInfo);
        return userInfo;
    }

    @Transactional(readOnly = true)
    public Optional<UserInfo> findById(Long userId) {
        checkNotNull(userId, "userId must be provided.");

        return userRepository.findById(userId);
    }

    @Transactional(readOnly = true)
    public Optional<UserInfo> findByEmail(Email email) {
        checkNotNull(email, "email must be provided.");

        return userRepository.findByEmail(email);
    }



    @Transactional(readOnly = true)
    public Optional<UserInfo> findByName(String name) {
        checkNotNull(name, "nickname must be provided.");

        return userRepository.findByName(name);
    }



    /*
     *  매칭 필터 설정을 수정한다
     */
    @Transactional
    public UserSearchFilter updateUserFilter(Long userId, UserSearchFilterUpdateRequest userSearchFilterUpdateRequest) {
        UserSearchFilter beforeFilter = userSearchFilterRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(UserInfo.class, userId));

        beforeFilter.update(userSearchFilterUpdateRequest);

        return userSearchFilterRepository.save(beforeFilter);
    }


    /*
     * 사용자 추가정보를 수정한다.
     * */
    @Transactional
    public UserInfo updateUserMoreInformation(Long userId, UserMoreInformationUpdateRequest userMoreInformationUpdateRequest) {

        UserInfo userInfo = userRepository.findById(userId).orElseThrow(() -> new NotFoundException(UserInfo.class, userId));

        userInfo.updateMoreInfo(userMoreInformationUpdateRequest);

        update(userInfo);

        return userInfo;
    }

    @Transactional
    public UserInfo updateUserPhoneNumber(Long userId, String phoneNumber) {

        UserInfo userInfo = userRepository.findById(userId).orElseThrow(() -> new NotFoundException(UserInfo.class, userId));

        userInfo.updatePhoneNumber(phoneNumber);

        update(userInfo);

        return userInfo;
    }


    private UserInfo insert(UserInfo userInfo) {
        return userRepository.save(userInfo);
    }

    private void update(UserInfo userInfo) {
        userRepository.save(userInfo);
    }

}