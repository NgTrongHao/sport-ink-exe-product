package rubberduck.org.sportinksystemalt.playtime.controller;


import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import rubberduck.org.sportinksystemalt.playtime.domain.dto.CreatePlaytimeRequest;
import rubberduck.org.sportinksystemalt.playtime.domain.dto.PlaytimeResponse;
import rubberduck.org.sportinksystemalt.playtime.service.IPlaytimeService;
import rubberduck.org.sportinksystemalt.shared.common.Constants;

import java.util.UUID;

@RestController
@RequestMapping("/api/playtimes")
public class PlayTimeController {
    private final IPlaytimeService playdateService;


    public PlayTimeController(IPlaytimeService playdateService) {
        this.playdateService = playdateService;
    }

    @PostMapping
    public PlaytimeResponse createPlaydate(@RequestBody CreatePlaytimeRequest request) {
        return playdateService.createPlaytime(request);
    }

    @GetMapping("/{id}")
    public PlaytimeResponse getPlaydateById(@PathVariable UUID id) {
        return playdateService.getPlaytimeById(id);
    }

    @GetMapping("/paged")
    public Page<PlaytimeResponse> getPlaytimesPageable(@RequestParam(value = "page", defaultValue = "" + Constants.DEFAULT_PAGE_NUMBER) int page, @RequestParam(value = "size", defaultValue = "" + Constants.DEFAULT_PAGE_SIZE) int size) {
        return playdateService.getPlaytimesPageable(page, size);
    }

    @DeleteMapping("/{id}")
    public void deletePlaydate(@PathVariable UUID id) {
        playdateService.deletePlaytime(id);
    }
}
