package hu.timetable.api.route.dto;

import hu.timetable.api.settlement.entity.Settlement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@AllArgsConstructor
abstract class AbstractRouteDto {
    private Settlement smallestCity;
    private Settlement bigestCity;
    private String message;
    private Long sumDistance;
    private String sumTime;

    AbstractRouteDto(String message) {
        this.message = message;
    }

    AbstractRouteDto(Settlement smallestCity, Settlement bigestCity, Long sumDistance, String sumTime) {
        this.smallestCity = smallestCity;
        this.bigestCity = bigestCity;
        this.sumDistance = sumDistance;
        this.sumTime = sumTime;
    }
}
