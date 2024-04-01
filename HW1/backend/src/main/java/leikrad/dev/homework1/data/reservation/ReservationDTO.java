package leikrad.dev.homework1.data.reservation;

import leikrad.dev.homework1.data.trip.TripDTO;

public class ReservationDTO {
    private Long reservationId;
    private TripDTO trip;
    private String personName;
    private String phoneNumber;
    private String uuid;

    public ReservationDTO() {
    }

    public ReservationDTO(Long reservationId, TripDTO trip, String personName, String phoneNumber, String uuid) {
        this.reservationId = reservationId;
        this.trip = trip;
        this.personName = personName;
        this.phoneNumber = phoneNumber;
        this.uuid = uuid;
    }

    public Long getReservationId() {
        return reservationId;
    }

    public void setReservationId(Long reservationId) {
        this.reservationId = reservationId;
    }

    public TripDTO getTrip() {
        return trip;
    }

    public void setTrip(TripDTO trip) {
        this.trip = trip;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public static ReservationDTO fromReservationEntity(Reservation reservation) {
        return new ReservationDTO(reservation.getReservationId(), TripDTO.fromTripEntity(reservation.getTrip()), reservation.getPersonName(), reservation.getPhoneNumber(), reservation.getUuid());
    }

    public Reservation toReservationEntity() {
        return new Reservation(getReservationId(), getTrip().toTripEntity(), getPersonName(), getPhoneNumber(), getUuid());
    }
}
