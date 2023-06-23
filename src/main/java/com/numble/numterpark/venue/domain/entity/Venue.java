package com.numble.numterpark.venue.domain.entity;

import com.numble.numterpark.global.domain.BaseTimeEntity;
import com.numble.numterpark.venue.domain.common.VenueType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@ToString
public class Venue extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int capacity;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private VenueType type;

    private LocalTime startTime;

    private LocalTime endTime;

    private String phoneNumber;

    @OneToMany(mappedBy = "venue", orphanRemoval = true)
    private Set<Seat> seats = new HashSet<>();

    @Builder
    public Venue(Long id, String name, int capacity, VenueType type, LocalTime startTime,
        LocalTime endTime, String phoneNumber, Set<Seat> seats) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
        this.type = type;
        this.startTime = startTime;
        this.endTime = endTime;
        this.phoneNumber = phoneNumber;
        this.seats = seats;
    }
}
