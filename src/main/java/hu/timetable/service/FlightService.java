package hu.timetable.service;

import hu.timetable.api.airline.entity.AirLine;
import hu.timetable.api.flight.entity.Flight;
import hu.timetable.api.route.dto.RouteDto;
import hu.timetable.api.route.dto.RouteDtoWithoutAirLine;
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

    public List<RouteDto> findShortestRouteBetweenCitiesByAirlines() {
        Settlement smallestCity = settlementRepository.findSmallestCity();
        Settlement biggestCity = settlementRepository.findBiggestCity();
        List<AirLine> airLineList = airLineRepository.findAll();
        List<RouteDto> result = new LinkedList<>();
        for (AirLine airLine : airLineList) {
            List<Flight> shortestRoute = calculateShortestPathFromSource(airLine.getFlightList(), smallestCity, biggestCity);
            if (shortestRoute.stream().anyMatch(flight -> flight.getDestination().equals(biggestCity))) {
                String time = calculateTime(shortestRoute);
                result.add(new RouteDto(smallestCity
                        , biggestCity
                        , airLine
                        , shortestRoute
                        , null
                        , shortestRoute.stream().mapToLong(Flight::getDistance).sum(), time));
            } else {
                result.add(new RouteDto(airLine,"Ez a légitársaság nem repül a célra!"));
            }
        }
        return result;
    }

    private String calculateTime(List<Flight> shortestRoute) {
       return DateUtils.formatDate(shortestRoute.stream().map(Flight::getPeriod).reduce((date, date2) -> {
           Calendar cal1 = Calendar.getInstance();
           cal1.setTime(date);
           int minutesToNextDeparture = (60 - cal1.get(Calendar.MINUTE));
           cal1.add(Calendar.MINUTE,minutesToNextDeparture);
           Calendar cal2 = Calendar.getInstance();
           cal2.setTime(date2);
           cal1.add(Calendar.HOUR,cal2.get(Calendar.HOUR));
           cal1.add(Calendar.MINUTE,cal2.get(Calendar.MINUTE));
           return cal1.getTime();
        }).orElse(null));
    }

    public RouteDtoWithoutAirLine findShortestRouteBetweenCities() {
        Settlement smallestCity = settlementRepository.findSmallestCity();
        Settlement biggestCity = settlementRepository.findBiggestCity();
        List<Flight> flightList = flightRepository.findAll();
        List<Flight> shortestRoute = calculateShortestPathFromSource(flightList, smallestCity, biggestCity);
        String time = DateUtils.formatDate(shortestRoute.stream().map(Flight::getPeriod).reduce((date, date2) -> {
            Long milis = date.getTime() + date2.getTime();
            return new Date(milis);
        }).orElse(null));

        List<String> routeInString = shortestRoute
                .stream()
                .map(flight -> flight.getAirLineId().getName() + ": " + flight.getDeparture().getName() + " -> " + flight.getDestination().getName())
                .collect(Collectors.toList());

        return new RouteDtoWithoutAirLine(smallestCity
                , biggestCity
                , routeInString
                , shortestRoute
                    .stream()
                    .mapToLong(Flight::getDistance).sum()
                , time);
    }

    private List<Flight> calculateShortestPathFromSource(List<Flight> flightList, Settlement smallestCity, Settlement biggestCity) {
        Set<Flight> settledFlights = new HashSet<>();
        Set<Flight> unsettledFlights = new HashSet<>();
        List<Flight> startPoints = new ArrayList<>();

        startPoints.addAll(flightList.stream().filter(flight -> flight.getDeparture().equals(smallestCity)).collect(Collectors.toList()));
//        startPoints.forEach(flight -> findRouteForFlight(biggestCity, flight, flightList));
        findRouteForFlight(biggestCity, flightList);

        startPoints.forEach(flight -> {
            unsettledFlights.clear();
            unsettledFlights.add(flight);
            findRoutes(settledFlights, unsettledFlights);
        });
        settledFlights.forEach(flight -> flight.setSumDist(flight.getShortestPath().stream().mapToLong(Flight::getDistance).sum() + flight.getDistance()));

        Flight result = settledFlights
                .stream()
                .filter(flight -> flight.getDestination().equals(biggestCity))
                .sorted(Comparator.comparing(Flight::getSumDist))
                .findFirst().orElse(null);

//        settledFlights
//                .stream()
//                .filter(flight -> flight.getDestination().equals(biggestCity))
//                .sorted(Comparator.comparing(Flight::getSumDist)).collect(Collectors.toList());

        List<Flight> resultFlightList = new LinkedList<>();
        if (result != null) {
            resultFlightList.addAll(result.getShortestPath());
            resultFlightList.add(result);
        }

        return resultFlightList;
    }

    private void findRoutes(Set<Flight> settledFlights, Set<Flight> unsettledFlights) {
        while (unsettledFlights.size() != 0) {
            Flight currentFlight = getLowestDistanceFlight(unsettledFlights);
            unsettledFlights.remove(currentFlight);
            for (Flight adjacencyFlight :
                    currentFlight.getAdjacentFlights()) {
                calculateMinimumDistance(adjacencyFlight, adjacencyFlight.getDistance(), currentFlight);
                unsettledFlights.add(adjacencyFlight);
            }
            settledFlights.add(currentFlight);
        }
    }

    private Flight getLowestDistanceFlight(Set<Flight> unsettledFlights) {
        Flight lowestDistanceFlight = null;
        Long lowestDistance = Long.MAX_VALUE;
        for (Flight flight : unsettledFlights) {
            Long flightDistance = flight.getDistance();
            if (flightDistance < lowestDistance) {
                lowestDistance = flightDistance;
                lowestDistanceFlight = flight;
            }
        }
        return lowestDistanceFlight;
    }

    private void calculateMinimumDistance(Flight evaluationFlight,
                                          Long edgeWeigh, Flight sourceFlight) {
        if (edgeWeigh <= evaluationFlight.getDistance()) {
            LinkedList<Flight> shortestPath = new LinkedList<>(sourceFlight.getShortestPath());
            shortestPath.add(sourceFlight);
            evaluationFlight.setShortestPath(shortestPath);
        }
    }

//    private List<Flight> findRouteForFlight(Settlement dest,List<Flight> flightList) {
//        Set<Flight> route = new LinkedHashSet<>();
//
//        Flight currentFlight = flightList.stream().filter(flight -> flight.getAdjacentFlights().isEmpty()).findAny().orElse(null);
//
//        List<Flight> nextFlight = flightList
//                .stream()
//                .filter(flight1 -> flight.getDestination().equals(flight1.getDeparture()))
//                .filter(flight1 -> flightList.stream().anyMatch(flight2 -> flight1.getDestination().equals(flight2.getDeparture()) || flight1.getDestination().equals(dest)))
//                .collect(Collectors.toList());
//
//        route.add(flight);
//
//        if (nextFlight.isEmpty()) {
//            return new ArrayList<>(route);
//        }
//
//        flight.setAdjacentFlights(nextFlight);
//
//        List<Flight> nexFlightList = flightList.stream().filter(flight1 -> !route.contains(flight1)).collect(Collectors.toList());
////        nexFlightList.forEach(flight1 -> route.addAll(findRouteForFlight(dest, flight1, nexFlightList)));
//        return new ArrayList<>(route);
//    }

    private List<Flight> findRouteForFlight(Settlement dest,List<Flight> flightList) {
        List<Flight> route = new LinkedList<>();

        Flight currentFlight = flightList
                .stream()
                .filter(flight -> !flight.getDestination().equals(dest))
                .filter(flight -> flightList.stream().anyMatch(flight1 -> flight.getDestination().equals(flight1.getDeparture())))
                .filter(flight -> flight.getAdjacentFlights().isEmpty())
                .findFirst()
                .orElse(null);

        if(currentFlight != null) {

            List<Flight> nextFlight = flightList
                    .stream()
                    .filter(flight1 -> currentFlight.getDestination().equals(flight1.getDeparture()))
                    .filter(flight1 -> flightList.stream().anyMatch(flight2 -> flight1.getDestination().equals(flight2.getDeparture()) || flight1.getDestination().equals(dest)))
//                    .filter(flight1 -> flight1.getDestination().equals(dest))
                    .collect(Collectors.toList());

            route.add(currentFlight);
//            if (nextFlight.isEmpty()) {
//                return new ArrayList<>(route);
//            }

            currentFlight.setAdjacentFlights(nextFlight);

//            List<Flight> nexFlightList = flightList.stream().filter(flight1 -> !route.contains(flight1)).collect(Collectors.toList());
            route.addAll(findRouteForFlight(dest,flightList));
        }
        return new ArrayList<>(route);
    }

    public List<Flight> findFlightsByName(String name) {
        return flightRepository.findFlightsByAirLineName(name);
    }

}
