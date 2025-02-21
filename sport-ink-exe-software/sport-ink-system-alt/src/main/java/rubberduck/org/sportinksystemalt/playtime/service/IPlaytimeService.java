package rubberduck.org.sportinksystemalt.playtime.service;

import org.springframework.data.domain.Page;
import rubberduck.org.sportinksystemalt.playtime.domain.dto.CreatePlaytimeRequest;
import rubberduck.org.sportinksystemalt.playtime.domain.dto.PlaytimeResponse;
import rubberduck.org.sportinksystemalt.playtime.domain.entity.Playtime;

import java.util.List;
import java.util.UUID;

public interface IPlaytimeService {
    PlaytimeResponse createPlaytime(String username, CreatePlaytimeRequest request);
    PlaytimeResponse getPlaytimeById(UUID id);

    void deletePlaytime(UUID id);
    Page<PlaytimeResponse> getPlaytimesPageable(int page, int size);

    Playtime findPlaytimeById(UUID id);

    List<Playtime> getAllPlaytimesOfUser(String username);
}
