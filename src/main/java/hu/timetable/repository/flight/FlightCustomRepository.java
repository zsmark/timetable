package hu.timetable.repository.flight;

import hu.timetable.api.flight.entity.Flight;

import java.util.List;

public interface FlightCustomRepository {
    List<Flight> findByCities(String departure, String destination);
}
