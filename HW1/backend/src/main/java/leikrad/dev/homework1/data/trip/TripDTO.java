package leikrad.dev.homework1.data.trip;

import java.time.LocalDateTime;

import leikrad.dev.homework1.data.city.CityDTO;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString @EqualsAndHashCode
public class TripDTO {
    private Long tripId;
    private CityDTO originCity;
    private CityDTO destinationCity;
    private LocalDateTime departureDate;
    private LocalDateTime arrivalDate;
    private Double price;
}
