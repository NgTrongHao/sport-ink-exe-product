package rubberduck.org.sportinksystemalt.playtime.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import rubberduck.org.sportinksystemalt.playtime.domain.dto.CreatePlaytimeRequest;
import rubberduck.org.sportinksystemalt.playtime.domain.dto.PlaytimeResponse;
import rubberduck.org.sportinksystemalt.playtime.service.IPlaytimeService;
import rubberduck.org.sportinksystemalt.shared.common.Constants;
import rubberduck.org.sportinksystemalt.shared.common.annotation.CurrentUser;

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
    public PlaytimeResponse createPlaytime(@CurrentUser String username, @RequestBody CreatePlaytimeRequest request) {
        return playdateService.createPlaytime(username, request);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get Playtime by ID REST API",
            description = "Get Playtime REST API is used to Get playtime by id."
    )
    public PlaytimeResponse getPlaytimeById(@PathVariable UUID id) {
        return playdateService.getPlaytimeById(id);
    }

    @GetMapping("/paged")
    @Operation(
            summary = "Get Playtime list REST API",
            description = "Get Playtime REST API is used to get playtime list pagination."
    )
    public Page<PlaytimeResponse> getPlaytimesPageable(@RequestParam(value = "page", defaultValue = "" + Constants.DEFAULT_PAGE_NUMBER) int page, @RequestParam(value = "size", defaultValue = "" + Constants.DEFAULT_PAGE_SIZE) int size) {
        return playdateService.getPlaytimesPageable(page, size);
    }

    @DeleteMapping("/delete/{id}")
    @Operation(
            summary = "Delete Playtime REST API",
            description = "Delete Playtime REST API is used to delete playtime by id."
    )
    public void deletePlaytime(@PathVariable UUID id) {
        playdateService.deletePlaytime(id);
    }

    @PostMapping("/{id}/join")
    @Operation(
            summary = "Join Playtime REST API",
            description = "User who is not bookmaker can join an OPEN playtime if it not reach max participants"
    )
    public PlaytimeResponse joinPlaytime(@CurrentUser String username, @PathVariable UUID id) {
        return playdateService.joinPlaytime(username, id);
    }
}
