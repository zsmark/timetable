package hu.timetable.api.route.dto;

import hu.timetable.api.airline.entity.AirLine;
import hu.timetable.api.flight.entity.Flight;
import hu.timetable.api.settlement.entity.Settlement;
import lombok.Getter;

import java.util.List;
import java.util.StringJoiner;

@Getter
public class RouteDto extends AbstractRouteDto {
    private AirLine airLine;
    private List<Flight> route;

    public RouteDto(AirLine airLine, Settlement smallestCity, Settlement biggestCity, List<Flight> route, String message, Long sumDistance, String sumTime) {
        super(smallestCity, biggestCity, message, sumDistance, sumTime);
        this.airLine = airLine;
        this.route = route;
    }

    public RouteDto(AirLine airLine, String message) {
        super(message);
        this.airLine = airLine;
    }

    @Override
    public String toString() {
        if (airLine == null)
            return "";

        StringJoiner joiner = new StringJoiner("\n");
        joiner.add(airLine.getName() + ":");
        if (route != null && !route.isEmpty()) {
            route.forEach(flight -> {
                joiner.add(flight.getDeparture().getName()+ " -> " + flight.getDestination().getName() + ": " + flight.getPeriodInString());
            });
            joiner.add("------");
            joiner.add("Összesen (idő): " + getSumTime());
            joiner.add("Összesen (km): " + getSumDistance() + " km");
        } else {
            joiner.add(getMessage());
        }

        joiner.add(" ");

        return joiner.toString();
    }

}
