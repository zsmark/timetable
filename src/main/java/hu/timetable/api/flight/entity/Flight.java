package hu.timetable.api.flight.entity;

import hu.timetable.api.airline.entity.AirLine;
import hu.timetable.api.settlement.entity.Settlement;
import hu.timetable.service.util.DateUtils;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "FLIGHT")
public class Flight implements Comparable<Flight> {

    @javax.persistence.Id
    @GenericGenerator(name = "SEQ_FLIGHT", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {@org.hibernate.annotations.Parameter(name = "sequence_name", value = "SEQ_FLIGHT"),
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")})
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_FLIGHT")
    @Column(name = "ID", unique = true, nullable = false, precision = 22)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "AIR_LINE_ID")
    private AirLine airLine;

    @Column(name = "DISTANCE")
    private Long distance;

    @Column(name = "flight_time")
    private int flightTime;

    @ManyToOne
    @JoinColumn(name = "DEPARTURE")
    private Settlement departure;

    @ManyToOne
    @JoinColumn(name = "DESTINATION")
    private Settlement destination;

    public String getPeriodInString() {
        return DateUtils.formatDate(flightTime);
    }

    public String getDistanceInString() {
        return distance + " km";
    }

    @Transient
    private List<Flight> adjacentFlights = new LinkedList<>();

    @Transient
    private List<Flight> shortestPath = new LinkedList<>();

    @Transient
    private Long sumDist;

    public Long getSumDist() {
        if (sumDist == null && getShortestPath() != null) {
//            settledFlights.forEach(flight -> flight.setSumDist(flight.getShortestPath().stream().mapToLong(Flight::getDistance).sum() + flight.getDistance()));
            setSumDist(getShortestPath().stream().mapToLong(Flight::getDistance).sum() + getDistance());
        }
        return sumDist;
    }

    @Override
    public int compareTo(Flight o) {
        return this.distance.compareTo(o.getDistance());
    }

    public String getTostringWithAirlineName(){
        return "Légitársaság: " + getAirLine().getName() + " " + toString();
    }

    @Override
    public String toString() {
        return "Indulás: " + departure.getName() + " Érkezés: " + destination.getName() + " Távolság: " + getDistanceInString() + " Repülési idő: " + getPeriodInString();
    }
}
