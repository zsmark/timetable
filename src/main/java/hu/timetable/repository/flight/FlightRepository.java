package hu.timetable.repository.flight;

import hu.timetable.api.flight.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flight,Integer>,FlightCustomRepository{

    @Query("select a.flightList from AirLine a where lower(a.name) = lower(:pName)")
    List<Flight> findFlightsByAirLineName(@Param("pName") String name);
}
