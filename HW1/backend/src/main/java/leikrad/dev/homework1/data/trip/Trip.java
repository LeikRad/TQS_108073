package leikrad.dev.homework1.data.trip;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import leikrad.dev.homework1.data.city.City;
import lombok.*;

@Entity
@Table(name = "trips")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @RequiredArgsConstructor
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long tripId;

    @ManyToOne
    @JoinColumn(name = "origin_city_id")
    @NonNull
    @NotNull
    private City originCity;

    @ManyToOne
    @JoinColumn(name = "destination_city_id")
    @NonNull
    @NotNull
    private City destinationCity;

    @Column(name = "departure_date")
    @NonNull
    @NotNull
    private LocalDateTime departureDate;

    @Column(name = "arrival_date")
    private LocalDateTime arrivalDate;

    @Column(name = "price")
    @NonNull
    @NotNull
    private Double price;

    public Trip(City originCity, City destinationCity, LocalDateTime departureDate, LocalDateTime arrivalDate, Double price) {
        this.originCity = originCity;
        this.destinationCity = destinationCity;
        this.departureDate = departureDate;
        this.arrivalDate = arrivalDate;
        this.price = price;
    }
}
