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

    public static TripDTO fromTripEntity(Trip trip) {
        return new TripDTO(trip.getTripId(), CityDTO.fromCityEntity(trip.getOriginCity()), CityDTO.fromCityEntity(trip.getDestinationCity()), trip.getDepartureDate(), trip.getArrivalDate(), trip.getPrice());
    }

    public Trip toTripEntity() {
        return new Trip(tripId, originCity.toCityEntity(), destinationCity.toCityEntity(), departureDate, arrivalDate, price);
    }
}
