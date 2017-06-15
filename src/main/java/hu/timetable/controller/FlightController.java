package hu.timetable.controller;

import hu.timetable.api.flight.entity.Flight;
import hu.timetable.api.route.dto.RouteDto;
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

    @RequestMapping(path = FLIGHT_CONTEXT + "findAll")
    public List<Flight> findAll(){
        return service.findAll();
    }

    @RequestMapping(path = FLIGHT_CONTEXT + "findByDepAndDest")
    public List<Flight> findByDepAndDest(@RequestParam(value = "dep",required = false) String dep,
                                         @RequestParam(value = "dest",required = false) String dest){
        return service.findByCities(dep,dest);
    }

    @RequestMapping(path = FLIGHT_CONTEXT + "findRouteByAirlines")
    public List<RouteDto> findRouteByAirlines(){
        return service.findRouteBetweenCitiesByAirlines();
    }
}
