package hu.timetable.api.route.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import hu.timetable.api.airline.entity.AirLine;
import hu.timetable.api.flight.entity.Flight;
import hu.timetable.api.settlement.entity.Settlement;
import lombok.Getter;

import java.util.List;

/**
 * Created by BEAR on 2017. 06. 15..
 */
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RouteDto extends AbstractRouteDto {
    @JsonProperty("Légitársaság")
    private AirLine airLine;
    @JsonProperty("Útvonal")
    private List<Flight> route;

    public RouteDto(Settlement smallestCity, Settlement biggestCity, List<Flight> route, String message, Long sumDistance, String sumTime) {
        super(smallestCity, biggestCity, message, sumDistance, sumTime);
//        this.airLine = airLine;
        this.route = route;
    }

    public RouteDto(AirLine airLine, String message) {
        super(message);
        this.airLine = airLine;
    }
}
