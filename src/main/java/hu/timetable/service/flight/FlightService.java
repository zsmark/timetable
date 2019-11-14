package hu.timetable.service.flight;

import hu.timetable.api.airline.entity.AirLine;
import hu.timetable.api.flight.entity.Flight;
import hu.timetable.api.route.dto.RouteDto;
import hu.timetable.api.route.dto.RouteDtoWithoutAirLine;
import hu.timetable.api.settlement.entity.Settlement;
import hu.timetable.repository.ariline.AirLineRepository;
import hu.timetable.repository.flight.FlightRepository;
import hu.timetable.repository.settlement.SettlementRepository;
import hu.timetable.service.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class FlightService {

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private AirLineRepository airLineRepository;

    @Autowired
    private SettlementRepository settlementRepository;

    public List<Flight> findByCities(String departure, String destination) {
        return flightRepository.findByCities(departure, destination);
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
                result.add(new RouteDto(airLine, smallestCity
                        , biggestCity
                        , shortestRoute
                        , null
                        , shortestRoute.stream().mapToLong(Flight::getDistance).sum(), time));
            } else {
                result.add(new RouteDto(airLine, "Ez a légitársaság nem repül a célra!"));
            }
        }
        return result;
    }

    private String calculateTime(List<Flight> shortestRoute) {
        return DateUtils.formatDate(shortestRoute.stream().map(Flight::getFlightTime).reduce((flightTime1, flightTime2) -> {
            LocalDateTime time1 = DateUtils.getLocalDateTimeFromTimeInMinutes(flightTime1);
            LocalDateTime time2 = DateUtils.getLocalDateTimeFromTimeInMinutes(flightTime2);

            int minutesToNextDeparture = (DateUtils.MINUTE - time1.getMinute());

            return time1.getHour() * DateUtils.MINUTE + time1.getMinute() + minutesToNextDeparture + time2.getHour() * DateUtils.MINUTE + time2.getMinute();

        }).orElseThrow(() -> new IllegalArgumentException("Nem lehet üres a lista")));
    }

    public RouteDtoWithoutAirLine findShortestRouteBetweenCities() {
        Settlement smallestCity = settlementRepository.findSmallestCity();
        Settlement biggestCity = settlementRepository.findBiggestCity();
        List<Flight> flightList = flightRepository.findAll();
        List<Flight> shortestRoute = calculateShortestPathFromSource(flightList, smallestCity, biggestCity);
        String time = DateUtils.formatDate(shortestRoute.stream().mapToInt(Flight::getFlightTime).sum());

        List<String> routeInString = shortestRoute
                .stream()
                .map(flight -> flight.getAirLine().getName() + ": " + flight.getDeparture().getName() + " -> " + flight.getDestination().getName() + ": " + flight.getPeriodInString())
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

        List<Flight> startPoints = flightList.
                stream()
                .filter(flight -> flight.getDeparture().equals(smallestCity))
                .collect(Collectors.toList());

        findRouteForFlight(biggestCity, flightList);

        startPoints.forEach(flight -> {
            unsettledFlights.clear();
            unsettledFlights.add(flight);
            findRoutes(settledFlights, unsettledFlights);
        });

        Flight result = settledFlights
                .stream()
                .filter(flight -> flight.getDestination().equals(biggestCity))
                .min(Comparator.comparing(Flight::getSumDist))
                .orElse(null);

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
            for (Flight adjacencyFlight : currentFlight.getAdjacentFlights()) {
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

    private List<Flight> findRouteForFlight(Settlement dest, List<Flight> flightList) {
        List<Flight> route = new LinkedList<>();

        Flight currentFlight = flightList
                .stream()
                .filter(flight -> !flight.getDestination().equals(dest))
                .filter(flight -> flightList.stream().anyMatch(flight1 -> flight.getDestination().equals(flight1.getDeparture())))
                .filter(flight -> flight.getAdjacentFlights().isEmpty())
                .findFirst()
                .orElse(null);

        if (currentFlight != null) {

            List<Flight> nextFlight = flightList
                    .stream()
                    .filter(flight1 -> currentFlight.getDestination().equals(flight1.getDeparture()))
                    .filter(flight1 -> flightList.stream().anyMatch(flight2 -> flight1.getDestination().equals(flight2.getDeparture()) || flight1.getDestination().equals(dest)))
                    .collect(Collectors.toList());

            route.add(currentFlight);

            currentFlight.setAdjacentFlights(nextFlight);

            route.addAll(findRouteForFlight(dest, flightList));
        }
        return new ArrayList<>(route);
    }

    public List<Flight> findFlightsByName(String name) {
        return flightRepository.findFlightsByAirLineName(name);
    }

}
