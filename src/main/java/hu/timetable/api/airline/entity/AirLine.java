package hu.timetable.api.airline.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AirLine extends AbstractEntity{

    @JsonIgnore
    @javax.persistence.Id
    @Column(name = "ID", unique = true, nullable = false, precision = 22)
    private Integer id;

    @JsonProperty("Név")
    @Column(name = "NAME")
    private String name;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER,mappedBy = "airLineId")
    private List<Flight> flightList = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AirLine airLine = (AirLine) o;

        return name != null ? name.equals(airLine.name) : airLine.name == null;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
