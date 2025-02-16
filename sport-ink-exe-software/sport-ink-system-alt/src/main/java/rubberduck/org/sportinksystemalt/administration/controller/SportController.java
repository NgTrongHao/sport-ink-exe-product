package rubberduck.org.sportinksystemalt.administration.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rubberduck.org.sportinksystemalt.administration.domain.dto.CreateSportRequest;
import rubberduck.org.sportinksystemalt.administration.domain.dto.SportResponse;
import rubberduck.org.sportinksystemalt.administration.service.ISportService;
import rubberduck.org.sportinksystemalt.shared.domain.ApiResponse;

import java.util.UUID;

@RestController
@RequestMapping("/api/sports")
@Tag(name = "Sport", description = "Sport REST API")
public class SportController {
    private final ISportService sportService;

    public SportController(ISportService sportService) {
        this.sportService = sportService;
    }

    @PostMapping("/add")
    @Operation(
            summary = "Add Sport REST API",
            description = "Add Sport REST API is used to add a new sport."
    )
    public ResponseEntity<ApiResponse<String>> addSport(@RequestBody CreateSportRequest request) {
        sportService.addSport(request);
        return ResponseEntity.ok(ApiResponse.<String>builder()
                .code(201)
                .message("Sport added successfully")
                .build());
    }

    @GetMapping("/get-all")
    @Operation(
            summary = "Get All Sports REST API",
            description = "Get All Sports REST API is used to get all sports."
    )
    public ResponseEntity<ApiResponse<Iterable<SportResponse>>> getAllSports() {
        return ResponseEntity.ok(ApiResponse.<Iterable<SportResponse>>builder()
                .code(200)
                .message("Sports fetched successfully")
                .data(sportService.getAllSports())
                .build());
    }

    @GetMapping("/get/{id}")
    @Operation(
            summary = "Get Sport By Id REST API",
            description = "Get Sport By Id REST API is used to get a sport by id."
    )
    public ResponseEntity<ApiResponse<SportResponse>> getSportById(@PathVariable UUID id) {
        return ResponseEntity.ok(ApiResponse.<SportResponse>builder()
                .code(200)
                .message("Sport fetched successfully")
                .data(sportService.getSportById(id))
                .build());
    }
}
