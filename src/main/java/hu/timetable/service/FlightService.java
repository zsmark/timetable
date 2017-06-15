package hu.timetable.service;

import hu.timetable.api.airline.entity.AirLine;
import hu.timetable.api.flight.entity.Flight;
import hu.timetable.api.route.dto.RouteDto;
import hu.timetable.api.settlement.entity.Settlement;
import hu.timetable.repository.ariline.AirLineRepository;
import hu.timetable.repository.flight.FlightCustomRepository;
import hu.timetable.repository.flight.FlightRepository;
import hu.timetable.repository.settlement.SettlementRepository;
import hu.timetable.service.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by zsidm on 2017. 06. 14..
 */
@Service
public class FlightService {

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private FlightCustomRepository customFlightRepository;

    @Autowired
    private AirLineRepository airLineRepository;

    @Autowired
    private SettlementRepository settlementRepository;

    public List<Flight> findAll() {
        return flightRepository.findAll();
    }

    public List<Flight> findByCities(String departure, String destination) {
        return customFlightRepository.findByCities(departure, destination);
    }

    public List<RouteDto> findRouteBetweenCitiesByAirlines() {
        Settlement smallestCity = settlementRepository.findSmallestCity();
        Settlement biggestCity = settlementRepository.findBiggestCity();
        List<AirLine> airLineList = airLineRepository.findAll();
        List<RouteDto> result = new LinkedList<>();
        for (AirLine airLine : airLineList) {
            List<Flight> possiblePointList = new LinkedList<>();
            List<Flight> route = new LinkedList<>();
            Flight start = airLine.getFlightList().stream().filter(flight -> flight.getDeparture().equals(smallestCity)).findAny().orElse(null);
            if (start == null)
                continue;
            Flight end = airLine.getFlightList().stream().filter(flight -> flight.getDestination().equals(biggestCity)).findAny().orElse(null);

            if (end == null)
                continue;

            possiblePointList.addAll(findRouteForFlight(biggestCity, start, airLine.getFlightList(), null));
            if (possiblePointList.stream().anyMatch(flight -> flight.getDestination().equals(biggestCity))) {
                String time = DateUtils.formatDate(possiblePointList.stream().map(Flight::getPeriod).reduce((date, date2) -> {
                            Long milis = date.getTime() + date2.getTime();
                            return new Date(milis);
                        }).orElse(null));
                result.add(new RouteDto(smallestCity
                        , biggestCity
                        , airLine
                        , possiblePointList
                        , null
                        , possiblePointList.stream().mapToLong(Flight::getDistance).sum(), time));
            } else {
                result.add(new RouteDto("Ez a légitársaság nem repül a célra!"));
            }
        }
        return result;
    }

    private List<Flight> findRouteForFlight(Settlement dest, Flight flight, List<Flight> flightList, List<Flight> route) {
        if (route == null)
            route = new LinkedList<>();

        route.add(flight);

        if (flight.getDestination().equals(dest))
            return route;

        Flight nextFlight = flightList
                .stream()
                .filter(flight1 -> flight.getDestination().equals(flight1.getDeparture()))
                .filter(flight1 -> flightList.stream().anyMatch(flight2 -> flight1.getDestination().equals(flight2.getDeparture()) || flight1.getDestination().equals(dest)))
                .sorted()
                .limit(1)
                .findAny()
                .orElse(null);

        if (nextFlight == null)
            return route;

        return findRouteForFlight(dest
                , nextFlight
                , flightList
                        .stream()
                        .filter(flight1 -> flight1.getDeparture().equals(nextFlight.getDestination()))
                        .collect(Collectors.toList())
                , route);
    }

    public List<Flight> findFlightsByName(String name) {
        return flightRepository.findFlightsByAirLineName(name);
    }
}
