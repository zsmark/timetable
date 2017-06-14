package hu.timetable.repository.flight;

import hu.timetable.api.flight.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by zsidm on 2017. 06. 14..
 */
@Repository
public interface FlightRepository extends JpaRepository<Flight,Integer>{

    @Query("select f from Flight f where f.departure.name = :pDeparture and f.destintaion.name = :pDestination")
    List<Flight> findByCities(@Param("pDeparture") String departure,@Param("pDestination") String destination);
}
