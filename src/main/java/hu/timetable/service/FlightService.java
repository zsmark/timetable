package hu.timetable.service;

import hu.timetable.api.flight.entity.Flight;
import hu.timetable.repository.flight.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zsidm on 2017. 06. 14..
 */
@Service
public class FlightService {

    @Autowired
    private FlightRepository flightRepository;

    public List<Flight> findAll(){
        return flightRepository.findAll();
    }

    public List<Flight> findByCities(String departure,String destination){
        return flightRepository.findByCities(departure,destination);
    }
}
