package rubberduck.org.sportinksystemalt.playtime.service;

import org.springframework.data.domain.Page;
import rubberduck.org.sportinksystemalt.playtime.domain.dto.CreatePlaytimeRequest;
import rubberduck.org.sportinksystemalt.playtime.domain.dto.PlaytimeResponse;

import java.time.LocalDateTime;
import java.util.UUID;

public interface IPlaytimeService {
    PlaytimeResponse createPlaytime(String username, CreatePlaytimeRequest request);
    PlaytimeResponse getPlaytimeById(UUID id);

    void deletePlaytime(UUID id);
    Page<PlaytimeResponse> getPlaytimesPageable(int page, int size);

    Page<PlaytimeResponse> searchPlaytimes(UUID sportId, String city, String district, String ward,
                                           LocalDateTime startDate, LocalDateTime endDate,
                                           int page, int size);

    PlaytimeResponse joinPlaytime(String username, UUID playtimeId);

}
