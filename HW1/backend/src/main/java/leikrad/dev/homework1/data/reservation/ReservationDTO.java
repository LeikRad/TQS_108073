package leikrad.dev.homework1.data.reservation;

import leikrad.dev.homework1.data.trip.TripDTO;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString @EqualsAndHashCode
public class ReservationDTO {
    private Long reservationId;
    private TripDTO trip;
    private String personName;
    private String phoneNumber;
    private String uuid;
}
