package leikrad.dev.homework1.data.trip;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import leikrad.dev.homework1.data.city.City;

@Entity
@Table(name = "trips")
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long tripId;

    @ManyToOne
    @JoinColumn(name = "origin_city_id")
    @NotNull
    private City originCity;

    @ManyToOne
    @JoinColumn(name = "destination_city_id")
    @NotNull
    private City destinationCity;

    @Column(name = "derparture_date")
    @NotNull
    private LocalDateTime departureDate;

    @Column(name = "arrival_date")
    @NotNull
    private LocalDateTime arrivalDate;

    @Column(name = "price")
    @NotNull
    private Double price;

    public Trip() {
    }

    public Trip(City originCity, City destinationCity, LocalDateTime departureDate, LocalDateTime arrivalDate, Double price) {
        this.originCity = originCity;
        this.destinationCity = destinationCity;
        this.departureDate = departureDate;
        this.arrivalDate = arrivalDate;
        this.price = price;
    }

    public Trip(Long tripId, City originCity, City destinationCity, LocalDateTime departureDate, LocalDateTime arrivalDate, Double price) {
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

    public City getOriginCity() {
        return originCity;
    }

    public void setOriginCity(City originCity) {
        this.originCity = originCity;
    }

    public City getDestinationCity() {
        return destinationCity;
    }

    public void setDestinationCity(City destinationCity) {
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
}
