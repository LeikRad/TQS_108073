package leikrad.dev.homework1.data.trip;

import java.time.LocalDateTime;

import leikrad.dev.homework1.data.city.CityDTO;

public class TripDTO {
    private Long tripId;
    private CityDTO originCity;
    private CityDTO destinationCity;
    private LocalDateTime departureDate;
    private LocalDateTime arrivalDate;
    private Double price;

    public TripDTO() {
    }

    public TripDTO(Long tripId, CityDTO originCity, CityDTO destinationCity, LocalDateTime departureDate, LocalDateTime arrivalDate, Double price) {
        this.tripId = tripId;
        this.originCity = originCity;
        this.destinationCity = destinationCity;
        this.departureDate = departureDate;
        this.arrivalDate = arrivalDate;
        this.price = price;
    }

    public Long getTripId() {
        return tripId;
    }

    public void setTripId(Long tripId) {
        this.tripId = tripId;
    }

    public CityDTO getOriginCity() {
        return originCity;
    }

    public void setOriginCity(CityDTO originCity) {
        this.originCity = originCity;
    }

    public CityDTO getDestinationCity() {
        return destinationCity;
    }

    public void setDestinationCity(CityDTO destinationCity) {
        this.destinationCity = destinationCity;
    }

    public LocalDateTime getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(LocalDateTime departureDate) {
        this.departureDate = departureDate;
    }

    public LocalDateTime getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(LocalDateTime arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public static TripDTO fromTripEntity(Trip trip) {
        return new TripDTO(trip.getTripId(), CityDTO.fromCityEntity(trip.getOriginCity()), CityDTO.fromCityEntity(trip.getDestinationCity()), trip.getDepartureDate(), trip.getArrivalDate(), trip.getPrice());
    }

    public Trip toTripEntity() {
        return new Trip(getTripId(), getOriginCity().toCityEntity(), getDestinationCity().toCityEntity(), getDepartureDate(), getArrivalDate(), getPrice());
    }


}
