package hu.timetable.api.flight.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import hu.timetable.api.airline.entity.AirLine;
import hu.timetable.api.common.entity.AbstractEntity;
import hu.timetable.api.settlement.entity.Settlement;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalTime;

/**
 * Created by zsidm on 2017. 06. 14..
 */
@Getter
@Setter
@Entity
@Table(name = "FLIGHT")
public class Flight extends AbstractEntity {

    @JsonIgnore
    @javax.persistence.Id
    @GenericGenerator(name = "SEQ_FLIGHT", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {@org.hibernate.annotations.Parameter(name = "sequence_name", value = "SEQ_FLIGHT"),
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")})
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_FLIGHT")
    @Column(name = "ID", unique = true, nullable = false, precision = 22)
    private Integer id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "AIR_LINE_ID")
    private AirLine airLineId;

    @JsonProperty("Távolság")
    @Column(name = "DISTANCE")
    private Long distance;

    @JsonProperty("Repülési idő")
    @Column(name = "PERIOD")
    private LocalTime period;

    @JsonProperty("Indulás")
    @ManyToOne
    @JoinColumn(name = "DEPARTURE")
    private Settlement departure;

    @JsonProperty("Cél")
    @ManyToOne
    @JoinColumn(name = "DESTINATION")
    private Settlement destintaion;

}
