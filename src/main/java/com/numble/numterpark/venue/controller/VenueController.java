package com.numble.numterpark.venue.controller;

import com.numble.numterpark.venue.service.VenueService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class VenueController {

    private final VenueService venueService;

    @PostMapping("/venue")
    public Long createVenue(@RequestBody @Valid VenueDto.CreateRequest createRequest) {
        log.debug("createRequest: {}", createRequest);
        return venueService.createVenue(createRequest);
    }
}
