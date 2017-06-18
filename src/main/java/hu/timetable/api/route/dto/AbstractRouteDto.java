package hu.timetable.api.route.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import hu.timetable.api.settlement.entity.Settlement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


/**
 * Created by BEAR on 2017. 06. 15..
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
abstract class AbstractRouteDto {
    @JsonProperty("legkisebb város")
    private Settlement smallestCity;
    @JsonProperty("legnagyobb város")
    private Settlement bigestCity;
    @JsonProperty("üzenet")
    private String message;
    @JsonProperty("teljes úthossz (km)")
    private Long sumDistance;
    @JsonProperty("teljes úthossz (idő)")
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
