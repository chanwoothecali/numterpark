package com.numble.numterpark.venue.service;

import com.numble.numterpark.venue.controller.VenueDto;
import com.numble.numterpark.venue.domain.entity.Venue;
import com.numble.numterpark.venue.repository.VenueRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class VenueService {

    private final VenueRepository venueRepository;

    @Transactional
    public Long createVenue(VenueDto.CreateRequest createRequest) {
        Venue venue = createRequest.toEntity();
        log.debug("venue: {}", venue);
        venueRepository.save(venue);
        return venue.getId();
    }
}
