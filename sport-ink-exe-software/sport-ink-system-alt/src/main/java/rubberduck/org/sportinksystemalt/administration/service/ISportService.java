package rubberduck.org.sportinksystemalt.administration.service;

import rubberduck.org.sportinksystemalt.administration.domain.dto.CreateSportRequest;
import rubberduck.org.sportinksystemalt.administration.domain.dto.SportResponse;
import rubberduck.org.sportinksystemalt.administration.domain.entity.Sport;

import java.util.Set;
import java.util.UUID;

public interface ISportService {
    void addSport(CreateSportRequest request);

    SportResponse getSportById(UUID id);

    Sport findSportById(UUID id);

    Set<SportResponse> getAllSports();
}
