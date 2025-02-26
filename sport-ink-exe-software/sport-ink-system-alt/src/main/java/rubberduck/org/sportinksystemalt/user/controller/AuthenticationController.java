package rubberduck.org.sportinksystemalt.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import rubberduck.org.sportinksystemalt.shared.domain.ApiResponse;
import rubberduck.org.sportinksystemalt.user.domain.dto.LoginUserRequest;
import rubberduck.org.sportinksystemalt.user.domain.dto.LoginUserResponse;
import rubberduck.org.sportinksystemalt.user.domain.dto.RegisterUserRequest;
import rubberduck.org.sportinksystemalt.user.domain.dto.UserWithTokenResponse;
import rubberduck.org.sportinksystemalt.user.service.IAuthenticationService;

@Controller
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "Authentication REST API")
public class AuthenticationController {
    private final IAuthenticationService authenticationService;

    public AuthenticationController(IAuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    @Operation(
            summary = "Register REST API",
            description = "Register REST API is used to register a new user."
    )
    public ResponseEntity<ApiResponse<UserWithTokenResponse>> register(@RequestBody @Valid RegisterUserRequest registerUserRequest) {
        return new ResponseEntity<>(
                ApiResponse.<UserWithTokenResponse>builder()
                        .code(201)
                        .message("User registered successfully")
                        .data(authenticationService.register(registerUserRequest))
                        .build(),
                HttpStatus.CREATED);
    }

    @PostMapping("/login")
    @Operation(
            summary = "Login REST API",
            description = "Login REST API is used to login a user."
    )
    public ResponseEntity<ApiResponse<LoginUserResponse>> login(@RequestBody @Valid LoginUserRequest loginUserRequest) {
        return ResponseEntity.ok(
                ApiResponse.<LoginUserResponse>builder()
                        .code(200)
                        .message("User login successfully")
                        .data(authenticationService.login(loginUserRequest))
                        .build()
        );
    }

    @PostMapping("/login-with-Google")
    @Operation(
            summary = "Login With Google REST API",
            description = "Login With Google REST API is used to login a user with google."
    )
    public ResponseEntity<ApiResponse<UserWithTokenResponse>> loginWithGoogle(@RequestBody String token) {
        return ResponseEntity.ok(
                ApiResponse.<UserWithTokenResponse>builder()
                        .code(200)
                        .message("User login with google successfully")
                        .data(authenticationService.loginWithGoogle(token))
                        .build()
        );
    }
}
