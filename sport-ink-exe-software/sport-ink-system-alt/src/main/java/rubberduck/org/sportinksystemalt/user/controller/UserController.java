package rubberduck.org.sportinksystemalt.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rubberduck.org.sportinksystemalt.shared.common.annotation.CurrentUser;
import rubberduck.org.sportinksystemalt.shared.domain.ApiResponse;
import rubberduck.org.sportinksystemalt.user.domain.dto.CreatePlayerProfileRequest;
import rubberduck.org.sportinksystemalt.user.domain.dto.CreateVenueOwnerProfileRequest;
import rubberduck.org.sportinksystemalt.user.domain.dto.UpdateUserProfileRequest;
import rubberduck.org.sportinksystemalt.user.domain.dto.UserWithTokenResponse;
import rubberduck.org.sportinksystemalt.user.service.IUserService;

@RestController
@RequestMapping("/api/user")
@Tag(name = "User", description = "User REST API")
public class UserController {
    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @PostMapping("/createPlayerProfile")
    @Operation(
            summary = "Create Player Profile REST API",
            description = "Create Player Profile REST API is used to create a player profile."
    )
    public ResponseEntity<ApiResponse<UserWithTokenResponse>> createPlayerProfile(@CurrentUser String username, @RequestBody CreatePlayerProfileRequest request) {
        return ResponseEntity.ok(
                ApiResponse.<UserWithTokenResponse>builder()
                        .code(201)
                        .message("Player profile created successfully")
                        .data(userService.createPlayerProfile(username, request))
                        .build()
        );
    }

    @PostMapping("/createVenueOwnerProfile")
    @Operation(
            summary = "Create Venue Owner Profile REST API",
            description = "Create Venue Owner Profile REST API is used to create a venue owner profile."
    )
    public ResponseEntity<ApiResponse<UserWithTokenResponse>> createVenueOwnerProfile(@CurrentUser String username, @RequestBody CreateVenueOwnerProfileRequest request) {
        return ResponseEntity.ok(
                ApiResponse.<UserWithTokenResponse>builder()
                        .code(201)
                        .message("Venue owner profile created successfully")
                        .data(userService.createVenueOwnerProfile(username, request))
                        .build()
        );
    }

    @PostMapping("/updateUserProfile")
    @Operation(
            summary = "Update User Profile REST API",
            description = "Update User Profile REST API is used to update a user profile."
    )
    public ResponseEntity<ApiResponse<UserWithTokenResponse>> updateUserProfile(
            @CurrentUser String username,
            @RequestBody UpdateUserProfileRequest request){
        return ResponseEntity.ok(
                ApiResponse.<UserWithTokenResponse>builder()
                        .code(200)
                        .message("User profile updated successfully")
                        .data(userService.updateUserProfile(username, request))
                        .build()
        );
    }


}
