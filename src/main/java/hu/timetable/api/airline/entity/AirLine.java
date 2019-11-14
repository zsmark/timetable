package hu.timetable.api.airline.entity;

import hu.timetable.api.flight.entity.Flight;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(name = "AIR_LINE")
public class AirLine{

    @javax.persistence.Id
    @Column(name = "ID", unique = true, nullable = false, precision = 22)
    private Integer id;

    @Column(name = "NAME")
    private String name;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER,mappedBy = "airLine")
    private List<Flight> flightList = new ArrayList<>();

    @Override
    public String toString() {
        return  name;
    }
}
