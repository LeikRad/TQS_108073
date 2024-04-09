CREATE TABLE cities (
    city_id BIGINT PRIMARY KEY,
    city_name VARCHAR(255) NOT NULL
);

CREATE TABLE trips (
    trip_id BIGINT PRIMARY KEY,
    origin_city_id BIGINT NOT NULL,
    destination_city_id BIGINT NOT NULL,
    departure_date TIMESTAMP NOT NULL,
    arrival_date TIMESTAMP,
    price DOUBLE PRECISION NOT NULL,
    FOREIGN KEY (origin_city_id) REFERENCES cities(city_id),
    FOREIGN KEY (destination_city_id) REFERENCES cities(city_id)
);

CREATE TABLE reservations (
    reservation_id BIGINT PRIMARY KEY,
    trip_id BIGINT NOT NULL,
    person_name VARCHAR(255) NOT NULL,
    phone_number VARCHAR(255) NOT NULL,
    uuid VARCHAR(255) NOT NULL,
    payed DOUBLE PRECISION NOT NULL,
    currency_code VARCHAR(255) NOT NULL,
    FOREIGN KEY (trip_id) REFERENCES trips(trip_id)
);

-- INSERT CITIES 
INSERT INTO cities (city_id, city_name) VALUES (1, 'Lisboa');
INSERT INTO cities (city_id, city_name) VALUES (2, 'Porto');
INSERT INTO cities (city_id, city_name) VALUES (3, 'Faro');
INSERT INTO cities (city_id, city_name) VALUES (4, 'Albergaria-a-Velha');
INSERT INTO cities (city_id, city_name) VALUES (5, 'Aveiro');

-- INSERT TRIPS
-- Lisboa -> Porto
INSERT INTO trips (trip_id, origin_city_id, destination_city_id, departure_date, arrival_date, price) VALUES (1, 1, 2, '2024-05-03 12:00:00', '2024-05-03 16:20:00', 17.00);
INSERT INTO trips (trip_id, origin_city_id, destination_city_id, departure_date, arrival_date, price) VALUES (2, 1, 2, '2024-05-03 12:30:00', '2024-05-03 16:20:00', 18.00);
INSERT INTO trips (trip_id, origin_city_id, destination_city_id, departure_date, arrival_date, price) VALUES (3, 1, 2, '2024-05-03 18:30:00', '2024-05-04 00:35:00', 18.70);
-- Porto -> Lisboa
INSERT INTO trips (trip_id, origin_city_id, destination_city_id, departure_date, arrival_date, price) VALUES (4, 2, 1, '2024-05-03 22:05:00', '2024-05-04 00:02:45', 18.70);

-- Lisboa -> Faro
INSERT INTO trips (trip_id, origin_city_id, destination_city_id, departure_date, arrival_date, price) VALUES (5, 1, 3, '2024-05-03 10:00:00', '2024-05-04 13:25:00', 9.99);

-- Faro -> Lisboa
INSERT INTO trips (trip_id, origin_city_id, destination_city_id, departure_date, arrival_date, price) VALUES (6, 3, 1, '2024-05-03 17:00:00', '2024-05-04 20:30:00', 4.99);

-- Aveiro -> Albergaria-a-Velha
INSERT INTO trips (trip_id, origin_city_id, destination_city_id, departure_date, arrival_date, price) VALUES (7, 5, 4, '2024-05-03 14:45:00', '2024-05-03 15:05:00', 5.10);
INSERT INTO trips (trip_id, origin_city_id, destination_city_id, departure_date, arrival_date, price) VALUES (8, 5, 4, '2024-05-03 18:00:00', '2024-05-03 18:20:00', 3.95);

-- Albergaria-a-Velha -> Aveiro
INSERT INTO trips (trip_id, origin_city_id, destination_city_id, departure_date, arrival_date, price) VALUES (9, 4, 5, '2024-05-03 11:20:00', '2024-05-03 11:40:00', 5.10);

-- Aveiro -> Lisboa
INSERT INTO trips (trip_id, origin_city_id, destination_city_id, departure_date, arrival_date, price) VALUES (10, 5, 1, '2024-05-03 16:15:00', '2024-05-03 19:40:00', 9.99);

-- Lisboa -> Aveiro
INSERT INTO trips (trip_id, origin_city_id, destination_city_id, departure_date, arrival_date, price) VALUES (11, 1, 5, '2024-05-03 10:15:00', '2024-05-04 13:15:00', 14.50);
