package com.bgnc.bloodgpt.controller.impl;

import com.bgnc.bloodgpt.controller.UserProfileControllerApi;
import com.bgnc.bloodgpt.dto.request.ProfileRequest;
import com.bgnc.bloodgpt.dto.response.ProfileResponse;
import com.bgnc.bloodgpt.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserProfileController implements UserProfileControllerApi {

    private final UserProfileService userProfileService;

    @Override
    public ResponseEntity<ProfileResponse> getProfile(String tcNumber) {
        ProfileResponse profile = userProfileService.getProfile(tcNumber);
        return ResponseEntity.ok(profile);
    }

    @Override
    public ResponseEntity<ProfileResponse> updateProfile(String tcNumber, ProfileRequest request) {
        ProfileResponse updatedProfile = userProfileService.updateProfile(tcNumber, request);
        return ResponseEntity.ok(updatedProfile);
    }
}