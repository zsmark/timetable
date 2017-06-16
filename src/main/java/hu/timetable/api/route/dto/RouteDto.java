package hu.timetable.api.route.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import hu.timetable.api.airline.entity.AirLine;
import hu.timetable.api.flight.entity.Flight;
import hu.timetable.api.settlement.entity.Settlement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Created by BEAR on 2017. 06. 15..
 */
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RouteDto extends AbstractRouteDto {
    @JsonProperty("légitársaság")
    private AirLine airLine;
    @JsonProperty("útvonal")
    private List<Flight> route;

    public RouteDto(Settlement smallestCity, Settlement biggestCity, AirLine airLine, List<Flight> route, String message, Long sumDistance, String sumTime) {
        super(smallestCity, biggestCity, message, sumDistance, sumTime);
        this.airLine = airLine;
        this.route = route;
    }

    public RouteDto(String message) {
        super(message);
    }
}
