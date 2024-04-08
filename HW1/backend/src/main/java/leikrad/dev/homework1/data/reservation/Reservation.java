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
    @NonNull
    private String phoneNumber;

    @Column(name = "uuid")
    @NotNull
    private String uuid;

    @Column(name = "payed")
    @NotNull
    @NonNull
    private Double payed;

    @Column(name = "currency_code")
    @NonNull
    @NotNull
    private String currencyCode;

    public Reservation(Trip trip, String personName, String phoneNumber, String uuid, Double payed, String currencyCode) {
        this.trip = trip;
        this.personName = personName;
        this.phoneNumber = phoneNumber;
        this.uuid = uuid;
        this.currencyCode = currencyCode;
        this.payed = payed;
    }
}
