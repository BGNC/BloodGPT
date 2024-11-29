package com.bgnc.bloodgpt.controller;

import com.bgnc.bloodgpt.dto.request.ProfileRequest;
import com.bgnc.bloodgpt.dto.response.ProfileResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.bgnc.bloodgpt.utils.ProfileControllerApis.*;

@Tag(name = "User Profile", description = "Endpoints for managing user profiles. Accessible to patients and doctors.")
@RequestMapping(PROFILE_CONTROLLER)
public interface UserProfileControllerApi {

    /**
     * Retrieves the profile details of a user.
     *
     * @param tcNumber The TC number of the user.
     * @return The profile details of the user.
     */
    @GetMapping(GET_PROFILE)
    @Operation(summary = "Get User Profile", description = "Retrieves the profile details of a user.")
    ResponseEntity<ProfileResponse> getProfile(@PathVariable(TC_NUMBER) String tcNumber);

    /**
     * Updates the profile details of a user.
     *
     * @param tcNumber The TC number of the user.
     * @param request  The profile update request containing new details.
     * @return The updated profile details.
     */
    @PutMapping(UPDATE_PROFILE)
    @Operation(summary = "Update User Profile", description = "Updates the profile details of a user.")
    ResponseEntity<ProfileResponse> updateProfile(
            @PathVariable(TC_NUMBER) String tcNumber,
            @Valid @RequestBody ProfileRequest request
    );
}