package com.bgnc.bloodgpt.service.impl;

import com.bgnc.bloodgpt.dto.request.ProfileRequest;
import com.bgnc.bloodgpt.dto.response.ProfileResponse;
import com.bgnc.bloodgpt.enums.MessageType;
import com.bgnc.bloodgpt.exception.BaseException;
import com.bgnc.bloodgpt.exception.ErrorMessage;
import com.bgnc.bloodgpt.model.User;
import com.bgnc.bloodgpt.model.UserProfile;
import com.bgnc.bloodgpt.repository.UserProfileRepository;
import com.bgnc.bloodgpt.repository.UserRepository;
import com.bgnc.bloodgpt.service.UserProfileService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserProfileServiceImpl implements UserProfileService {

    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;

    private static final Logger logger = LoggerFactory.getLogger(UserProfileServiceImpl.class);


    /**
     * Kullanıcı profilini döndürür.
     */
    public ProfileResponse getProfile(String tcNumber) {
        UserProfile userProfile = userProfileRepository.findByUser_TcNumber(tcNumber)
                .orElseThrow(() -> new BaseException(
                        new ErrorMessage(MessageType.PROFILE_NOT_FOUND, "Profile not found for TC: " + tcNumber)
                ));

        return mapToResponse(userProfile);
    }

    @Override
    public void createProfileUser(User user) {
        logger.info("Creating profile for user with TC Number: {}", user.getTcNumber());
        UserProfile profile = UserProfile.builder()
                .user(user)
                .build();
        userProfileRepository.save(profile);
        logger.info("UserProfile created for user with TC Number: {}", user.getTcNumber());
    }



    /**
     * Kullanıcı profilini günceller.
     */
    @Transactional
    public ProfileResponse updateProfile(String tcNumber, ProfileRequest request) {
        User user = userRepository.findByTcNumber(tcNumber)
                .orElseThrow(() -> new BaseException(
                        new ErrorMessage(MessageType.USERNAME_NOT_FOUND_EXCEPTION, "User not found: " + tcNumber)
                ));

        UserProfile profile = userProfileRepository.findByUser_TcNumber(tcNumber).orElse(new UserProfile());
        profile.setUser(user);
        profile.setAge(request.getAge());
        profile.setHeight(request.getHeight());
        profile.setWeight(request.getWeight());
        profile.setEmail(request.getEmail());
        profile.setPhoneNumber(request.getPhoneNumber());
        // BMI hesaplaması
        if (profile.getHeight() != null && profile.getWeight() != null && profile.getHeight() > 0) {
            profile.setBmi(profile.getWeight() / (profile.getHeight() * profile.getHeight()));
        } else {
            profile.setBmi(null);
        }

        userProfileRepository.save(profile);

        return mapToResponse(profile);
    }

    /**
     * ProfileResponse map metodu
     */
    private ProfileResponse mapToResponse(UserProfile profile) {
        ProfileResponse response = new ProfileResponse();
        response.setFirstName(profile.getUser().getFirstName());
        response.setLastName(profile.getUser().getLastName());
        response.setAge(profile.getAge());
        response.setHeight(profile.getHeight());
        response.setWeight(profile.getWeight());
        response.setEmail(profile.getEmail());
        response.setPhoneNumber(profile.getPhoneNumber());
        response.setBmi(profile.getBmi());
        return response;
    }
}