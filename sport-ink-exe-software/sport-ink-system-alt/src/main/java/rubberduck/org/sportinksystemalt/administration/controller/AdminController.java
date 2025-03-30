package rubberduck.org.sportinksystemalt.administration.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rubberduck.org.sportinksystemalt.shared.domain.ApiResponse;
import rubberduck.org.sportinksystemalt.shared.domain.PageResponse;
import rubberduck.org.sportinksystemalt.user.domain.dto.UserListResponse;
import rubberduck.org.sportinksystemalt.user.service.IUserService;

@RestController
@RequestMapping("/api/admin")
@Tag(name = "Admin", description = "Admin REST API")
public class AdminController {
    
    private final IUserService userService;
    
    public AdminController(IUserService userService) {
        this.userService = userService;
    }
    
    @GetMapping("/users")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(
            summary = "Get All Users REST API",
            description = "Get All Users REST API is used to fetch all users with pagination. Only accessible by ADMIN."
    )
    public ResponseEntity<ApiResponse<Page<UserListResponse>>> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        return ResponseEntity.ok(
                ApiResponse.<Page<UserListResponse>>builder()
                        .code(200)
                        .message("Users fetched successfully")
                        .data(userService.getAllUsers(page, size))
                        .build()
        );
    }
}