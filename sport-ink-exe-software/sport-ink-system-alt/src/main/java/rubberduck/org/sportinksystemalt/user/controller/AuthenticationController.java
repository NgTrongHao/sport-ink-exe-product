package rubberduck.org.sportinksystemalt.user.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import rubberduck.org.sportinksystemalt.shared.domain.ApiResponse;
import rubberduck.org.sportinksystemalt.user.domain.dto.RegisterUserRequest;
import rubberduck.org.sportinksystemalt.user.domain.dto.RegisterUserResponse;
import rubberduck.org.sportinksystemalt.user.service.IAuthenticationService;

@Controller
@RequestMapping("/api/auth")
public class AuthenticationController {
    private final IAuthenticationService authenticationService;

    public AuthenticationController(IAuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<RegisterUserResponse>> register(@RequestBody @Valid RegisterUserRequest registerUserRequest) {
        return ResponseEntity.ok(
                ApiResponse.<RegisterUserResponse>builder()
                        .code(200)
                        .message("User registered successfully")
                        .data(authenticationService.register(registerUserRequest))
                        .build()
        );
    }
}
