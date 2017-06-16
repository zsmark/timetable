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
import java.util.Map;

/**
 * Created by BEAR on 2017. 06. 15..
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RouteDtoWithoutAirLine extends AbstractRouteDto{
    private List<String> route;

    public  RouteDtoWithoutAirLine(Settlement smallestCity, Settlement biggestCity, List<String> route, Long sumDistance, String sumTime) {
        super(smallestCity, biggestCity, sumDistance, sumTime);
        this.route = route;
    }

}
