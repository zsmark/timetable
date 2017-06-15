package hu.timetable.api.route.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
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
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RouteDto {
    private Settlement smallestCity;
    private Settlement bigestCity;
    private AirLine airLine;
    private List<Flight> route;
    private String message;
    private Long sumDistance;
    private String sumTime;

    public RouteDto(String message) {
        this.message = message;
    }
}
