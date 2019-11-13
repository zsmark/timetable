package hu.timetable.api.route.dto;

import hu.timetable.api.settlement.entity.Settlement;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.StringJoiner;

@Getter
@AllArgsConstructor
public class RouteDtoWithoutAirLine extends AbstractRouteDto{
    private List<String> route;

    public  RouteDtoWithoutAirLine(Settlement smallestCity, Settlement biggestCity, List<String> route, Long sumDistance, String sumTime) {
        super(smallestCity, biggestCity, sumDistance, sumTime);
        this.route = route;
    }

    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner("\n");
        joiner.add(String.join("\n", route));
        joiner.add("-------");
        joiner.add("Összesen: " + getSumTime());
        return joiner.toString();
    }
}
