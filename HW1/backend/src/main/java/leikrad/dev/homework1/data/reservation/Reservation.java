package leikrad.dev.homework1.data.reservation;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import leikrad.dev.homework1.data.trip.Trip;
import lombok.*;

@Entity
@Table(name = "reservations")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @RequiredArgsConstructor
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long reservationId;

    @ManyToOne
    @JoinColumn(name = "trip_id")
    @NotNull
    @NonNull
    private Trip trip;

    @Column(name = "person_name")
    @NotNull
    @NonNull
    private String personName;

    @Column(name = "phone_number")
    @NotNull
    @NonNull
    private String phoneNumber;

    @Column(name = "uuid")
    @NotNull
    @NonNull
    private String uuid;
}
