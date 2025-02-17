package rubberduck.org.sportinksystemalt.administration.service.impl;

import org.springframework.stereotype.Service;
import rubberduck.org.sportinksystemalt.administration.domain.dto.CreateSportRequest;
import rubberduck.org.sportinksystemalt.administration.domain.dto.SportResponse;
import rubberduck.org.sportinksystemalt.administration.domain.entity.Sport;
import rubberduck.org.sportinksystemalt.administration.repository.SportRepository;
import rubberduck.org.sportinksystemalt.administration.service.ISportService;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SportService implements ISportService {
    private final SportRepository sportRepository;

    public SportService(SportRepository sportRepository) {
        this.sportRepository = sportRepository;
    }

    @Override
    public void addSport(CreateSportRequest request) {
        Sport sport = Sport.builder()
                .name(request.name())
                .description(request.description())
                .iconUrl(request.iconUrl())
                .build();

        sportRepository.save(sport);
    }

    @Override
    public SportResponse getSportById(UUID id) {
        Sport sport = findSportById(id);
        return new SportResponse(sport.getId(), sport.getName(), sport.getDescription(), sport.getIconUrl());
    }

    @Override
    public Sport findSportById(UUID id) {
        return sportRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Sport not found")
        );
    }

    @Override
    public Set<SportResponse> getAllSports() {
        return sportRepository.findAll().stream()
                .map(sport -> new SportResponse(sport.getId(), sport.getName(), sport.getDescription(), sport.getIconUrl()))
                .collect(Collectors.toSet());
    }
}
