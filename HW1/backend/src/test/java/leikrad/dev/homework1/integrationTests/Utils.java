package leikrad.dev.homework1.integrationTests;

import java.time.LocalDateTime;
import java.util.UUID;

import leikrad.dev.homework1.data.city.City;
import leikrad.dev.homework1.data.city.CityRepository;
import leikrad.dev.homework1.data.trip.Trip;
import leikrad.dev.homework1.data.trip.TripRepository;
import leikrad.dev.homework1.data.reservation.Reservation;
import leikrad.dev.homework1.data.reservation.ReservationRepository;

public class Utils {
    static City createTestCity(CityRepository cityRepository, String cityName) {
        City city = new City(cityName);
        return cityRepository.save(city);
    }

    static Trip createTestTrip(TripRepository tripRepository, City origin, City destination, LocalDateTime dDate, LocalDateTime aDate, double price) {
        Trip trip = new Trip(origin, destination, dDate, aDate, price);
        
        return tripRepository.save(trip);
    }

    static Reservation createTestReservation(ReservationRepository reservationRepository, Trip trip, String name, String phone) {
        Reservation reservation = new Reservation(trip, name, phone);
        reservation.setUuid(UUID.randomUUID().toString());
        return reservationRepository.save(reservation);
    }
}
