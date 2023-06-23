package com.numble.numterpark.venue.repository;

import com.numble.numterpark.venue.domain.entity.Venue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VenueRepository extends JpaRepository<Venue, Long> {

}
