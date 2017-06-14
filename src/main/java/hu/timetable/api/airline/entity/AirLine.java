package hu.timetable.api.airline.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import hu.timetable.api.common.entity.AbstractEntity;
import hu.timetable.api.flight.entity.Flight;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zsidm on 2017. 06. 14..
 */
@Getter
@Setter
@Entity
@Table(name = "AIR_LINE")
public class AirLine extends AbstractEntity{

    @JsonIgnore
    @javax.persistence.Id
    @GenericGenerator(name = "SEQ_AIR_LINE", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {@org.hibernate.annotations.Parameter(name = "sequence_name", value = "SEQ_AIR_LINE"),
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")})
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_AIR_LINE")
    @Column(name = "ID", unique = true, nullable = false, precision = 22)
    private Integer id;

    @Column(name = "NAME")
    private String name;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER,mappedBy = "airLineId")
    private List<Flight> flightList = new ArrayList<>();
}
