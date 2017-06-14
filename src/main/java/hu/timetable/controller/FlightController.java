package hu.timetable.controller;

import hu.timetable.api.flight.entity.Flight;
import hu.timetable.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by zsidm on 2017. 06. 14..
 */
@RestController
public class FlightController {
    private final String FLIGHT_CONTEXT = "/flight/";

    @Autowired
    private FlightService service;

    @RequestMapping(path = FLIGHT_CONTEXT + "findByDepAndDest")
    public List<Flight> findByDepAndDest(@RequestParam("dep") String dep,@RequestParam("dest") String dest){
        return service.findByCities(dep,dest);
    }
}