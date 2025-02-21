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

import java.time.LocalDateTime;
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
    public ResponseEntity<ApiResponse<PlaytimeResponse>> getPlaydateById(@PathVariable UUID id) {
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
    public ResponseEntity<ApiResponse<String>> deletePlaydate(@PathVariable UUID id) {
        playdateService.deletePlaytime(id);
        return ResponseEntity.ok(ApiResponse.<String>builder()
                .code(HttpStatus.OK.value())
                .message("Playtime deleted successfully")
                .build());
    }

    @GetMapping("/search")
    @Operation(
            summary = "Search Playtimes by criteria",
            description = "Search Playtimes by following criterias: Sport, city, district, ward (Optional). " +
                    "start date, end date with pagination."
    )
    public ResponseEntity<ApiResponse<Page<PlaytimeResponse>>> searchPlaytimes(
            @RequestParam(required = false) UUID sportId,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String district,
            @RequestParam(required = false) String ward,
            @RequestParam("startDate") LocalDateTime startDate,
            @RequestParam("endDate") LocalDateTime endDate,
            @RequestParam(value = "page", defaultValue = "" + Constants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", defaultValue = "" + Constants.DEFAULT_PAGE_SIZE) int size) {
        Page<PlaytimeResponse> response = playdateService.searchPlaytimes(sportId, city, district, ward, startDate, endDate, page, size);
        return ResponseEntity.ok(ApiResponse.<Page<PlaytimeResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("Playtimes fetched successfully")
                .data(response)
                .build());
    }
}
