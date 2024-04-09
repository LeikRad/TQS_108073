export interface CityInterface {
    cityId: number;
    cityName: string;
}

export interface TripInterface {
    tripId: number;
    originCity: CityInterface;
    destinationCity: CityInterface;
    departureDate: string;
    arrivalDate: string;
    price: number;
}

export interface ReservationInterface {
    reservationId: number;
    trip: TripInterface;
    personName: string;
    phoneNumber: string;
    uuid: string;
    payed: number;
    currencyCode: string;
}
