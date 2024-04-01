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
    @NotNull
    @NonNull
    private City originCity;

    @ManyToOne
    @JoinColumn(name = "destination_city_id")
    @NotNull
    @NonNull
    private City destinationCity;

    @Column(name = "derparture_date")
    @NotNull
    @NonNull
    private LocalDateTime departureDate;

    @Column(name = "arrival_date")
    @NotNull
    @NonNull
    private LocalDateTime arrivalDate;

    @Column(name = "price")
    @NotNull
    @NonNull
    private Double price;
}
