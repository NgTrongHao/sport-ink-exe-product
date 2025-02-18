package rubberduck.org.sportinksystemalt.playdate.controller;


import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import rubberduck.org.sportinksystemalt.playdate.domain.dto.CreatePlaydateRequest;
import rubberduck.org.sportinksystemalt.playdate.domain.dto.PlaydateResponse;
import rubberduck.org.sportinksystemalt.playdate.service.PlaydateService;
import rubberduck.org.sportinksystemalt.shared.common.Constants;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/playdates")
public class PlayDateController {
    private final PlaydateService playdateService;


    public PlayDateController(PlaydateService playdateService) {
        this.playdateService = playdateService;
    }

    @PostMapping
    public PlaydateResponse createPlaydate(@RequestBody CreatePlaydateRequest request) {
        return playdateService.createPlaydate(request);
    }

    @GetMapping("/{id}")
    public PlaydateResponse getPlaydateById(@PathVariable UUID id) {
        return playdateService.getPlaydateById(id);
    }

    @GetMapping("/paged")
    public Page<PlaydateResponse> getPlaydatesPageable(
            @RequestParam(value = "page", defaultValue = "" + Constants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", defaultValue = "" + Constants.DEFAULT_PAGE_SIZE) int size
    ) {
        return playdateService.getPlaydatesPageable(page, size);
    }

    @DeleteMapping("/{id}")
    public void deletePlaydate(@PathVariable UUID id) {
        playdateService.deletePlaydate(id);
    }
}
