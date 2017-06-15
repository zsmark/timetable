package hu.timetable.api.flight.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import hu.timetable.api.airline.entity.AirLine;
import hu.timetable.api.common.entity.AbstractEntity;
import hu.timetable.api.settlement.entity.Settlement;
import hu.timetable.service.util.DateUtils;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.*;

/**
 * Created by zsidm on 2017. 06. 14..
 */
@Getter
@Setter
@Entity
@Table(name = "FLIGHT")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Flight extends AbstractEntity implements Comparable<Flight>{

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

    @JsonIgnore
    @Column(name = "DISTANCE")
    private Long distance;

    @JsonIgnore
    @Column(name = "PERIOD")
    @Temporal(TemporalType.TIME)
    private Date period;

    @JsonProperty("Indulás")
    @ManyToOne
    @JoinColumn(name = "DEPARTURE")
    private Settlement departure;

    @JsonProperty("Cél")
    @ManyToOne
    @JoinColumn(name = "DESTINATION")
    private Settlement destination;

    @JsonProperty("Repülési idő")
    public String getPeriodInString() {
        return DateUtils.formatDate(period);
    }

    @JsonProperty("Távolság")
    public String getDistanceInString(){
        return distance + " km";
    }

    @Transient
    private List<Flight> adjacentFlights = new LinkedList<>();
    @Transient
    private List<Flight> shortestPath = new LinkedList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Flight flight = (Flight) o;

        if (airLineId != null ? !airLineId.equals(flight.airLineId) : flight.airLineId != null) return false;
        if (distance != null ? !distance.equals(flight.distance) : flight.distance != null) return false;
        if (period != null ? !period.equals(flight.period) : flight.period != null) return false;
        if (departure != null ? !departure.equals(flight.departure) : flight.departure != null) return false;
        return destination != null ? destination.equals(flight.destination) : flight.destination == null;
    }

    @Override
    public int hashCode() {
        int result = airLineId != null ? airLineId.hashCode() : 0;
        result = 31 * result + (distance != null ? distance.hashCode() : 0);
        result = 31 * result + (period != null ? period.hashCode() : 0);
        result = 31 * result + (departure != null ? departure.hashCode() : 0);
        result = 31 * result + (destination != null ? destination.hashCode() : 0);
        return result;
    }

    @Override
    public int compareTo(Flight o) {
        return this.distance.compareTo(o.getDistance());
    }
}
