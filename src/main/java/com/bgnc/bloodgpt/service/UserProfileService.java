package com.bgnc.bloodgpt.service;

import com.bgnc.bloodgpt.dto.request.ProfileRequest;
import com.bgnc.bloodgpt.dto.response.ProfileResponse;

import com.bgnc.bloodgpt.model.User;
import jakarta.transaction.Transactional;

public interface UserProfileService {


    void createProfileUser(User user);

    @Transactional
    public ProfileResponse updateProfile(String tcNumber, ProfileRequest request);

    ProfileResponse getProfile(String tcNumber);
}
