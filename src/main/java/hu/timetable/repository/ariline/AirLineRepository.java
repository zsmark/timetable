package hu.timetable.repository.ariline;

import hu.timetable.api.airline.entity.AirLine;
import hu.timetable.api.flight.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by zsidm on 2017. 06. 14..
 */
@Repository
public interface AirLineRepository extends JpaRepository<AirLine,Integer> {
}