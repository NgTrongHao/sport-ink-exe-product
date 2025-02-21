package rubberduck.org.sportinksystemalt.playtime.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rubberduck.org.sportinksystemalt.playtime.domain.dto.CreatePlaytimeRequest;
import rubberduck.org.sportinksystemalt.playtime.domain.dto.PlaytimeResponse;
import rubberduck.org.sportinksystemalt.playtime.service.IPlaytimeService;
import rubberduck.org.sportinksystemalt.shared.common.Constants;
import rubberduck.org.sportinksystemalt.shared.common.annotation.CurrentUser;
import rubberduck.org.sportinksystemalt.shared.domain.ApiResponse;

import java.util.UUID;

@RestController
@RequestMapping("/api/playtimes")
@Tag(name = "Playtime", description = "Playtime REST API")
public class PlayTimeController {
    private final IPlaytimeService playdateService;


    public PlayTimeController(IPlaytimeService playdateService) {
        this.playdateService = playdateService;
    }

    @PostMapping("/add")
    @Operation(
            summary = "Add Playtime REST API",
            description = "Add Playtime REST API is used to add a new playtime."
    )
    public ResponseEntity<ApiResponse<PlaytimeResponse>> createPlaytime(@CurrentUser String username, @RequestBody CreatePlaytimeRequest request) {
        PlaytimeResponse response = playdateService.createPlaytime(username, request);
        return new ResponseEntity<>(ApiResponse.<PlaytimeResponse>builder()
                .code(HttpStatus.CREATED.value())
                .message("Playtime created successfully")
                .data(response)
                .build(),
                HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get Playtime by ID REST API",
            description = "Get Playtime REST API is used to Get playtime by id."
    )
    public ResponseEntity<ApiResponse<PlaytimeResponse>> getPlaytimeById(@PathVariable UUID id) {
        PlaytimeResponse response = playdateService.getPlaytimeById(id);
        return ResponseEntity.ok(ApiResponse.<PlaytimeResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Playtime fetched successfully")
                .data(response)
                .build());
    }

    @GetMapping("/paged")
    @Operation(
            summary = "Get Playtime list REST API",
            description = "Get Playtime REST API is used to get playtime list pagination."
    )
    public ResponseEntity<ApiResponse<Page<PlaytimeResponse>>> getPlaytimesPageable(@RequestParam(value = "page", defaultValue = "" + Constants.DEFAULT_PAGE_NUMBER) int page, @RequestParam(value = "size", defaultValue = "" + Constants.DEFAULT_PAGE_SIZE) int size) {
        Page<PlaytimeResponse> response = playdateService.getPlaytimesPageable(page, size);
        return ResponseEntity.ok(ApiResponse.<Page<PlaytimeResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("Playtimes fetched successfully")
                .data(response)
                .build());
    }

    @DeleteMapping("/delete/{id}")
    @Operation(
            summary = "Delete Playtime REST API",
            description = "Delete Playtime REST API is used to delete playtime by id."
    )
    public ResponseEntity<ApiResponse<String>> deletePlaytime(@PathVariable UUID id) {
        playdateService.deletePlaytime(id);
        return ResponseEntity.ok(ApiResponse.<String>builder()
                .code(HttpStatus.OK.value())
                .message("Playtime deleted successfully")
                .build());
    }

    @PostMapping("/{id}/join")
    @Operation(
            summary = "Join Playtime REST API",
            description = "User who is not bookmaker can join an OPEN playtime if it not reach max participants"
    )
    public ResponseEntity<ApiResponse<PlaytimeResponse>> joinPlaytime(@CurrentUser String username, @PathVariable UUID id) {
        PlaytimeResponse response = playdateService.joinPlaytime(username, id);
        return ResponseEntity.ok(ApiResponse.<PlaytimeResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Joined playtime successfully")
                .data(response)
                .build());
    }
}
