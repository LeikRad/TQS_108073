package leikrad.dev.homework1.data.reservation;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import leikrad.dev.homework1.data.trip.Trip;

@Entity
@Table(name = "reservations")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long reservationId;

    @ManyToOne
    @JoinColumn(name = "trip_id")
    @NotNull
    private Trip trip;

    @Column(name = "person_name")
    @NotNull
    private String personName;

    @Column(name = "phone_number")
    @NotNull
    private String phoneNumber;

    @Column(name = "uuid")
    @NotNull
    private String uuid;

    public Reservation() {
    }

    public Reservation(Trip trip, String personName, String phoneNumber, String uuid) {
        this.trip = trip;
        this.personName = personName;
        this.phoneNumber = phoneNumber;
        this.uuid = uuid;
    }

    public Reservation(Long reservationId, Trip trip, String personName, String phoneNumber, String uuid) {
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

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
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
}
