package rubberduck.org.sportinksystemalt.playdate.service;

import org.springframework.data.domain.Page;
import rubberduck.org.sportinksystemalt.playdate.domain.dto.CreatePlaydateRequest;
import rubberduck.org.sportinksystemalt.playdate.domain.dto.PlaydateResponse;

import java.util.List;
import java.util.UUID;

public interface PlaydateService {
    PlaydateResponse createPlaydate(CreatePlaydateRequest request);
    PlaydateResponse getPlaydateById(UUID id);

    void deletePlaydate(UUID id);
    Page<PlaydateResponse> getPlaydatesPageable(int page, int size);
}
