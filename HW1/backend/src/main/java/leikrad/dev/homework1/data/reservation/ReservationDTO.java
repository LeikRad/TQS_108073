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
    private Double payed;
    private String currencyCode;

    public static ReservationDTO fromReservationEntity(Reservation reservation) {
        return new ReservationDTO(reservation.getReservationId(), TripDTO.fromTripEntity(reservation.getTrip()), reservation.getPersonName(), reservation.getPhoneNumber(), reservation.getUuid(), reservation.getPayed(), reservation.getCurrencyCode());
    }

    public Reservation toReservationEntity() {
        return new Reservation(trip.toTripEntity(), personName, phoneNumber, uuid, payed, currencyCode);
    }
}
