package rubberduck.org.sportinksystemalt.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rubberduck.org.sportinksystemalt.shared.common.annotation.CurrentUser;
import rubberduck.org.sportinksystemalt.shared.domain.ApiResponse;
import rubberduck.org.sportinksystemalt.user.domain.dto.*;
import rubberduck.org.sportinksystemalt.user.service.IUserService;

@RestController
@RequestMapping("/api/users")
@Tag(name = "User", description = "User REST API")
public class UserController {
    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    @Operation(
            summary = "Get User Profile REST API",
            description = "Get User Profile REST API is used to fetch the user profile."
    )
    public ResponseEntity<ApiResponse<UserProfileResponse>> getUserProfile(@CurrentUser String username) {
        return ResponseEntity.ok(
                ApiResponse.<UserProfileResponse>builder()
                        .code(200)
                        .message("User profile fetched successfully")
                        .data(userService.getUserProfile(username))
                        .build()
        );
    }

    @PostMapping("/create-player-profile")
    @Operation(
            summary = "Create Player Profile REST API",
            description = "Create Player Profile REST API is used to create a player profile."
    )
    public ResponseEntity<ApiResponse<UserWithTokenResponse>> createPlayerProfile(@CurrentUser String username, @RequestBody @Valid CreatePlayerProfileRequest request) {
        return ResponseEntity.ok(
                ApiResponse.<UserWithTokenResponse>builder()
                        .code(201)
                        .message("Player profile created successfully")
                        .data(userService.createPlayerProfile(username, request))
                        .build()
        );
    }

    @PostMapping("/create-venue-owner-profile")
    @Operation(
            summary = "Create Venue Owner Profile REST API",
            description = "Create Venue Owner Profile REST API is used to create a venue owner profile."
    )
    public ResponseEntity<ApiResponse<UserWithTokenResponse>> createVenueOwnerProfile(@CurrentUser String username, @RequestBody @Valid CreateVenueOwnerProfileRequest request) {
        return ResponseEntity.ok(
                ApiResponse.<UserWithTokenResponse>builder()
                        .code(201)
                        .message("Venue owner profile created successfully")
                        .data(userService.createVenueOwnerProfile(username, request))
                        .build()
        );
    }

    @PatchMapping("/update-user-profile")
    @Operation(
            summary = "Update User Profile REST API",
            description = "Update User Profile REST API is used to update a user profile."
    )
    public ResponseEntity<ApiResponse<UserProfileResponse>> updateUserProfile(
            @CurrentUser String username,
            @RequestBody @Valid UpdateUserProfileRequest request) {
        return ResponseEntity.ok(
                ApiResponse.<UserProfileResponse>builder()
                        .code(200)
                        .message("User profile updated successfully")
                        .data(userService.updateUserProfile(username, request))
                        .build()
        );
    }


}
