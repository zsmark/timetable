package hu.timetable.controller;

import hu.timetable.api.airline.entity.AirLine;
import hu.timetable.api.flight.entity.Flight;
import hu.timetable.service.AirLineService;
import hu.timetable.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by zsidm on 2017. 06. 14..
 */
@RestController(value = "airline")
public class AirLineController {

    private final String AIRLINE_CONTEXT = "/airLine/";

    @Autowired
    private FlightService flightService;

    @Autowired
    private AirLineService airLineService;

    @RequestMapping(AIRLINE_CONTEXT + "findAll")
    public List<AirLine> findAll(){
        return airLineService.findAll();
    }

    @RequestMapping(AIRLINE_CONTEXT + "findFlightsByAirLine")
    public List<Flight> findFlightsByAirLine(@RequestParam(name = "name") String name){
        return flightService.findFlightsByName(name);
    }
}
