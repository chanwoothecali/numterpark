package com.numble.numterpark.venue.controller;

import com.numble.numterpark.venue.domain.common.SeatType;
import com.numble.numterpark.venue.domain.common.VenueType;
import com.numble.numterpark.venue.domain.entity.Seat;
import com.numble.numterpark.venue.domain.entity.Venue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

public class VenueDto {

    @Getter
    @ToString
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class CreateRequest {

        @NotBlank(message = "이름을 입력해주세요.")
        private String name;

        @Positive(message = "수용인원을 입력해주세요.")
        private int capacity;

        @NotBlank(message = "공연장 타입을 입력해주세요.")
        private String venuesType;

        @DateTimeFormat(iso = ISO.TIME, pattern = "HH:MM")
        private LocalTime startTime;

        @DateTimeFormat(iso = ISO.TIME, pattern = "HH:MM")
        private LocalTime endTime;

        private String phoneNumber;

        @NotEmpty(message = "공연장 좌석을 입력해주세요.")
        private Set<SeatDto> seats = new HashSet<>();

        @Builder
        public CreateRequest(String name, int capacity, String venuesType, LocalTime startTime,
            LocalTime endTime, String phoneNumber, Set<SeatDto> seats) {
            this.name = name;
            this.capacity = capacity;
            this.venuesType = venuesType;
            this.startTime = startTime;
            this.endTime = endTime;
            this.phoneNumber = phoneNumber;
            this.seats = seats;
        }

        public Venue toEntity() {
            return Venue.builder()
                .name(name)
                .capacity(capacity)
                .type(VenueType.fromValue(venuesType))
                .startTime(startTime)
                .endTime(endTime)
                .phoneNumber(phoneNumber)
                .seats(createSeatSet(seats))
                .build();
        }

        private Set<Seat> createSeatSet(Set<SeatDto> seatDtos) {
            return seatDtos.stream().map(SeatDto::toEntity).collect(Collectors.toSet());
        }
    }

    @ToString
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class SeatDto {

        @NotBlank(message = "좌석번호를 입력해주세요.")
        private String seatNumber;

        @NotBlank(message = "좌석타입을 입력해주세요.")
        private String seatType;

        @Builder
        public SeatDto(String seatNumber, String seatType) {
            this.seatNumber = seatNumber;
            this.seatType = seatType;
        }

        public Seat toEntity() {
            return Seat.builder()
                .seatNumber(seatNumber)
                .type(SeatType.fromValue(seatType))
                .build();
        }
    }
}
